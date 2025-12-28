package pawg.hexagonal.cdc.out.ports;

import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.params.ChangeIdQueryParam;
import pawg.hexagonal.cdc.out.params.ChangesInRangeQueryParam;

import java.util.List;
import java.util.Optional;

public interface CdcPort {
    void processChange(CdcEventDomain cdcEventDomain);
    List<CdcEventDomain> fetchCdcEvent(String changeId);
    List<CdcEventDomain> fetchCdcEvents(ChangesInRangeQueryParam changesInRangeQueryParam);
    Optional<String> fetchChangeId(ChangeIdQueryParam changeIdQueryParam);
}
