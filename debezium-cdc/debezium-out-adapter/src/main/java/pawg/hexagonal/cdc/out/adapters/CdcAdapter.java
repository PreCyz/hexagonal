package pawg.hexagonal.cdc.out.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import pawg.hexagonal.cdc.domain.CdcEvent;
import pawg.hexagonal.cdc.out.entities.Change;
import pawg.hexagonal.cdc.out.mappers.ChangeMapper;
import pawg.hexagonal.cdc.out.ports.CdcPort;
import pawg.hexagonal.cdc.out.repositories.ChangeRepository;

@RequiredArgsConstructor
@Service
@Slf4j
public class CdcAdapter implements CdcPort {

    private final ChangeRepository changeRepository;
    private final ChangeMapper changeMapper;

    @Override
    public void processChange(CdcEvent cdcEvent) {
        try {
            Change change = changeMapper.cdcEventToChange(cdcEvent);
            changeRepository.save(change);
            log.info("Change {} has been saved", change);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public CdcEvent fetchCdcEvent(final String changeId) {
        return changeRepository.findChangeByChangeId(changeId)
                .map(changeMapper::changeToCdcEvent)
                .orElseThrow(() -> new IllegalArgumentException("No change with Id " + changeId));
    }
}
