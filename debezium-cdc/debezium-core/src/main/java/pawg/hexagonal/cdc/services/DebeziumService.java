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
import pawg.hexagonal.cdc.domain.DebeziumEvent;
import pawg.hexagonal.cdc.out.port.CdcPort;

import java.io.IOException;
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
        try {
            DebeziumEvent event = objectMapper.readValue(
                    changeEvent.value(),
                    DebeziumEvent.class
            );

            if (event.getPayload() != null && event.getPayload().getOp() != null && !event.getPayload().getOp().isEmpty()) {

                Envelope.Operation operation = Envelope.Operation.forCode(event.getPayload().getOp());
                log.debug("Operation: [{}] changes [{}]", operation, event.getPayload());

                cdcPort.processChange(event.getPayload());
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

}

