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
import pawg.hexagonal.cdc.debezium.*;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.dto.ChangeIdQueryParam;
import pawg.hexagonal.cdc.out.dto.ChangesInRangeQueryParam;
import pawg.hexagonal.cdc.out.mappers.CdcEventMapper;
import pawg.hexagonal.cdc.out.ports.CdcPort;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
    private final CdcEventMapper cdcEventMapper;
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
            DebeziumEvent value = objectMapper.readValue(
                    changeEvent.value(),
                    DebeziumEvent.class
            );

            Optional<DebeziumEvent> key = Optional.ofNullable(objectMapper.readValue(
                    changeEvent.key(),
                    DebeziumEvent.class
            ));

            DebeziumPayload payload = value.getPayload();
            if (payload != null && payload.getOperation() != null && !payload.getOperation().isEmpty()) {

                String operation = Envelope.Operation.forCode(payload.getOperation()).name();
                log.info("Operation: [{}] changes [{}]", operation, payload);

                final var databaseName = payload.getSource().getDb();
                final var tableName = payload.getSource().getTable();
                var cdcEventDomain = new CdcEventDomain();
                cdcEventDomain.setValueBeforeChange(payload.getBefore());
                cdcEventDomain.setValueAfterChange(payload.getAfter());
                cdcEventDomain.setOperation(operation);
                cdcEventDomain.setDatabaseName(databaseName);
                cdcEventDomain.setTableName(tableName);
                cdcEventDomain.setTimestamp(LocalDateTime.now());
                key.flatMap(it -> getChangeId(databaseName, tableName, it))
                        .ifPresent(cdcEventDomain::setChangeId);

                cdcPort.processChange(cdcEventMapper.cdcEventDomainToEventOutDto(cdcEventDomain));
                log.info("Operation: [{}] on [{}] saved", operation, payload);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }

    }

    private Optional<String> getChangeId(String dbName, String tableName, DebeziumEvent key) {
        Optional<String> result = Optional.empty();
        if (key.getSchema() != null && key.getPayload().getId() != null) {
            Optional<String> idFieldName = key.getSchema()
                    .getFields()
                    .stream()
                    .map(DebeziumSchemaField::getField)
                    .findFirst();
            if (idFieldName.isPresent()) {
                result = cdcPort.fetchChangeId(
                        new ChangeIdQueryParam(dbName, tableName, idFieldName.get(), key.getPayload().getId())
                );
            }
        }
        return result;
    }

    public List<CdcEventDomain> fetchChanges(String changeId) {
        return cdcEventMapper.eventOutDtoListToCdcEventDomainList(cdcPort.fetchEventByChangeId(changeId));
    }

    public List<CdcEventDomain> fetchChanges(ChangesInRangeQueryParam changesInRangeQueryParam) {
        return cdcEventMapper.eventOutDtoListToCdcEventDomainList(cdcPort.fetchCdcEvents(changesInRangeQueryParam));
    }
}

