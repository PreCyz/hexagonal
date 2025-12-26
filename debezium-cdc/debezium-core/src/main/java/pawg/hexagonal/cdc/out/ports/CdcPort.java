package pawg.hexagonal.cdc.out.ports;

import pawg.hexagonal.cdc.domain.CdcEventDomain;

public interface CdcPort {
    void processChange(CdcEventDomain cdcEventDomain);
    CdcEventDomain fetchCdcEvent(String changeId);
}
