package pawg.hexagonal.cdc.out.adapters;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import pawg.hexagonal.cdc.out.dto.EventOutDto;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;
import pawg.hexagonal.cdc.out.mappers.ChangeMapper;
import pawg.hexagonal.cdc.out.dto.ChangeIdQueryParam;
import pawg.hexagonal.cdc.out.dto.ChangesInRangeQueryParam;
import pawg.hexagonal.cdc.out.ports.CdcPort;
import pawg.hexagonal.cdc.out.repositories.ChangeRepository;

import java.util.*;

@RequiredArgsConstructor
@Service
@Slf4j
public class CdcAdapter implements CdcPort {

    private final ChangeRepository changeRepository;
    private final ChangeMapper changeMapper;

    @Override
    public void processChange(EventOutDto eventOutDto) {
        try {
            ChangeEntity changeEntity = changeMapper.eventOutDtoToChange(eventOutDto);
            changeRepository.save(changeEntity);
            log.info("Change {} has been saved", changeEntity);
        } catch (IllegalArgumentException e) {
            log.error(e.getMessage(), e);
        }
    }

    @Override
    public List<EventOutDto> fetchEventByChangeId(final String changeId) {
        return changeMapper.changeListToEventOutDtoList(changeRepository.findChangeByChangeId(changeId));
    }

    @Override
    public List<EventOutDto> fetchCdcEvents(ChangesInRangeQueryParam changesInRangeQueryParam) {
        Set<String> operations = changesInRangeQueryParam.operations();
        if (operations == null || operations.isEmpty()) {
            operations = Set.of("CREATE", "UPDATE", "DELETE");
        }
        return changeMapper.changeListToEventOutDtoList(changeRepository.findAllByTimestampBetweenAndOperationIn(
                changesInRangeQueryParam.startTimestamp(),
                changesInRangeQueryParam.endTimestamp(),
                operations,
                PageRequest.of(
                        changesInRangeQueryParam.pageNumber(),
                        changesInRangeQueryParam.pageSize(),
                        Sort.by(Sort.Direction.DESC, "timestamp")
                )
        ).getContent());
    }

    @Override
    public Optional<String> fetchChangeId(ChangeIdQueryParam changeIdQueryParam) {
        log.info("Fetching changeId for databaseName: [{}], tableName: [{}] and record id: [{}]",
                changeIdQueryParam.dbName(), changeIdQueryParam.tableName(), changeIdQueryParam.id());

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
