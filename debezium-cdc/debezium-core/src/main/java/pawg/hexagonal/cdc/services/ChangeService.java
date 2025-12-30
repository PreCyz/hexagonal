package pawg.hexagonal.cdc.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import io.debezium.engine.ChangeEvent;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import pawg.hexagonal.cdc.debezium.DebeziumEvent;
import pawg.hexagonal.cdc.debezium.DebeziumPayload;
import pawg.hexagonal.cdc.debezium.DebeziumSchemaField;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.dto.ChangeIdQueryParam;
import pawg.hexagonal.cdc.out.dto.ChangesInRangeQueryParam;
import pawg.hexagonal.cdc.out.mappers.CdcEventMapper;
import pawg.hexagonal.cdc.out.ports.CdcPort;

@RequiredArgsConstructor
@Slf4j
public class ChangeService {
    private final ObjectMapper objectMapper;
    private final CdcPort cdcPort;
    private final CdcEventMapper cdcEventMapper;

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

            Optional<DebeziumEvent> key = Optional.empty();
            if (changeEvent.key() != null) {
                key = Optional.ofNullable(objectMapper.readValue(
                        changeEvent.key(),
                        DebeziumEvent.class
                ));
            }

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

