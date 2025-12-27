package pawg.hexagonal.cdc.out.ports;

import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.domain.ChangesInRangeDomain;

import java.util.List;

public interface CdcPort {
    void processChange(CdcEventDomain cdcEventDomain);
    CdcEventDomain fetchCdcEvent(String changeId);
    List<CdcEventDomain> fetchCdcEvents(ChangesInRangeDomain changesInRangeDomain);
}
