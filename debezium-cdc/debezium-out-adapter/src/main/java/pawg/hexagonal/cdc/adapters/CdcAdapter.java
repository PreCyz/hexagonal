package pawg.hexagonal.cdc.adapters;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.debezium.data.Envelope;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pawg.hexagonal.cdc.domain.Payload;
import pawg.hexagonal.cdc.entities.Change;
import pawg.hexagonal.cdc.out.port.CdcPort;
import pawg.hexagonal.cdc.repositories.ChangeRepository;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Service
@Slf4j
public class CdcAdapter implements CdcPort {

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final ChangeRepository changeRepository;

    @Override
    public void processChange(Payload payload) {
        try {
            Change change = new Change();
            change.setChangeId(UUID.randomUUID().toString());
            change.setValueBefore(objectMapper.convertValue(payload.getBefore(), new TypeReference<>() {}));
            change.setValueAfter(objectMapper.convertValue(payload.getAfter(), new TypeReference<>() {}));
            change.setOperation(Envelope.Operation.forCode(payload.getOp()).name());
            change.setDb(payload.getSource().getDb());
            change.setTableName(payload.getSource().getTable());
            change.setTimestamp(LocalDateTime.now());
            changeRepository.save(change);
            log.info("Change {} has been saved", change);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }
}
