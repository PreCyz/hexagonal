package pawg.hexagonal.cdc.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.config.Configuration;
import io.debezium.data.Envelope;
import io.debezium.engine.ChangeEvent;
import io.debezium.engine.DebeziumEngine;
import io.debezium.engine.format.Json;
import io.debezium.engine.format.KeyValueChangeEventFormat;
import jakarta.annotation.PostConstruct;
import jakarta.annotation.PreDestroy;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pawg.hexagonal.cdc.domain.*;
import pawg.hexagonal.cdc.out.ports.CdcPort;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
public class DebeziumService {

    private final ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();
    private final ObjectMapper objectMapper = new ObjectMapper();
    private final Configuration connectorConfiguration;
    private final CdcPort cdcPort;
    private DebeziumEngine<ChangeEvent<String, String>> debeziumEngine;

    @PostConstruct
    private void start() {
        debeziumEngine = DebeziumEngine.create(KeyValueChangeEventFormat.of(Json.class, Json.class))
                .using(connectorConfiguration.asProperties())
                .notifying(this::handleChangeEvent)
                .build();

        executor.execute(debeziumEngine);
    }

    @PreDestroy
    private void stop() throws IOException {
        if (debeziumEngine != null) {
            debeziumEngine.close();
        }
    }

    public void handleChangeEvent(ChangeEvent<String, String> changeEvent) {
        if (changeEvent.value() == null) {
            log.info("Received event with null value. The event's key is {}", changeEvent.key());
            return;
        }
        try {
            DebeziumEventDomain event = objectMapper.readValue(
                    changeEvent.value(),
                    DebeziumEventDomain.class
            );

            DebeziumPayloadDomain payload = event.getPayload();
            if (payload != null && payload.getOp() != null && !payload.getOp().isEmpty()) {

                Envelope.Operation operation = Envelope.Operation.forCode(payload.getOp());
                log.debug("Operation: [{}] changes [{}]", operation, payload);

                CdcEventDomain cdcEventDomain = new CdcEventDomain();
                cdcEventDomain.setValueBeforeChange(payload.getBefore());
                cdcEventDomain.setValueAfterChange(payload.getAfter());
                cdcEventDomain.setOperation(Envelope.Operation.forCode(payload.getOp()).name());
                cdcEventDomain.setDatabaseName(payload.getSource().getDb());
                cdcEventDomain.setTableName(payload.getSource().getTable());
                cdcEventDomain.setTimestamp(LocalDateTime.now());

                cdcPort.processChange(cdcEventDomain);
                log.debug("Operation: [{}] on [{}] saved", operation, payload);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    public CdcEventDomain fetchChange(String changeId) {
        return cdcPort.fetchCdcEvent(changeId);
    }

    public List<CdcEventDomain> fetchChanges(ChangesInRangeDomain changesInRangeDomain) {
        return cdcPort.fetchCdcEvents(changesInRangeDomain);
    }
}

