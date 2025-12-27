package pawg.hexagonal.cdc.out.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;
import pawg.hexagonal.cdc.out.mappers.ChangeMapper;
import pawg.hexagonal.cdc.out.params.ChangeIdQueryParam;
import pawg.hexagonal.cdc.out.params.ChangesInRangeQueryParam;
import pawg.hexagonal.cdc.out.ports.CdcPort;
import pawg.hexagonal.cdc.out.repositories.ChangeRepository;

import java.util.List;
import java.util.Optional;

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
    public List<CdcEventDomain> fetchCdcEvents(ChangesInRangeQueryParam changesInRangeQueryParam) {
        return changeMapper.changesToCdcEvents(changeRepository.findAllByTimestampBetween(
                changesInRangeQueryParam.startTimestamp(),
                changesInRangeQueryParam.endTimestamp(),
                PageRequest.of(changesInRangeQueryParam.pageNumber(), changesInRangeQueryParam.pageSize(), Sort.by(Sort.Direction.DESC, "timestamp"))
        ));
    }

    @Override
    public Optional<String> fetchChangeId(ChangeIdQueryParam changeIdQueryParam) {
        log.info("Fetching changeId for databaseName: [{}], tableName: [{}] and record id: [{}]", changeIdQueryParam.dbName(), changeIdQueryParam.tableName(), changeIdQueryParam.id());

        Optional<String> changeId = changeRepository.findChangeId(
                changeIdQueryParam.dbName(),
                changeIdQueryParam.tableName(),
                changeIdQueryParam.idFieldName(),
                changeIdQueryParam.id().toString()
        ).stream().findFirst();

        if (changeId.isPresent()) {
            log.info("ChangeId for databaseName: [{}], tableName: [{}] and record id: [{}] is: [{}]",
                    changeIdQueryParam.dbName(), changeIdQueryParam.tableName(), changeIdQueryParam.id(), changeId.get());
        } else {
            log.info("There is no changeId for databaseName: [{}], tableName: [{}] and record id: [{}]",
                    changeIdQueryParam.dbName(), changeIdQueryParam.tableName(), changeIdQueryParam.id());
        }
        return changeId;
    }
}
