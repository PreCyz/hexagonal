package pawg.hexagonal.cdc.out.ports;

import pawg.hexagonal.cdc.out.dto.ChangeIdQueryParam;
import pawg.hexagonal.cdc.out.dto.ChangesInRangeQueryParam;

import java.util.List;
import java.util.Optional;
import pawg.hexagonal.cdc.out.dto.EventOutDto;

public interface CdcPort {
    void processChange(EventOutDto eventOutDto);
    List<EventOutDto> fetchEventByChangeId(String changeId);
    List<EventOutDto> fetchCdcEvents(ChangesInRangeQueryParam changesInRangeQueryParam);
    Optional<String> fetchChangeId(ChangeIdQueryParam changeIdQueryParam);
}
