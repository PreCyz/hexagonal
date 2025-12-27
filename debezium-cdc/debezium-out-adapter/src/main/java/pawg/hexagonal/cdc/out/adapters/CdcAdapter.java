package pawg.hexagonal.cdc.out.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.domain.ChangesInRangeDomain;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;
import pawg.hexagonal.cdc.out.mappers.ChangeMapper;
import pawg.hexagonal.cdc.out.ports.CdcPort;
import pawg.hexagonal.cdc.out.repositories.ChangeRepository;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CdcAdapter implements CdcPort {

    private final ChangeRepository changeRepository;
    private final ChangeMapper changeMapper;

    @Override
    public void processChange(CdcEventDomain cdcEventDomain) {
        try {
            ChangeEntity changeEntity = changeMapper.cdcEventToChange(cdcEventDomain);
            changeRepository.save(changeEntity);
            log.info("Change {} has been saved", changeEntity);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public CdcEventDomain fetchCdcEvent(final String changeId) {
        return changeRepository.findChangeByChangeId(changeId)
                .map(changeMapper::changeToCdcEvent)
                .orElseThrow(() -> new IllegalArgumentException("No change with Id " + changeId));
    }

    @Override
    public List<CdcEventDomain> fetchCdcEvents(ChangesInRangeDomain changesInRangeDomain) {
        return changeMapper.changesToCdcEvents(changeRepository.findAllByTimestampBetween(
                changesInRangeDomain.startTimestamp(),
                changesInRangeDomain.endTimestamp(),
                PageRequest.of(changesInRangeDomain.pageNumber(), changesInRangeDomain.pageSize(), Sort.by(Sort.Direction.DESC, "timestamp"))
        ));
    }
}
