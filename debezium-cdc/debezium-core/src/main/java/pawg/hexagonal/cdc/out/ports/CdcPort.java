package pawg.hexagonal.cdc.out.ports;

import pawg.hexagonal.cdc.domain.CdcEvent;

public interface CdcPort {
    void processChange(CdcEvent cdcEvent);
    CdcEvent fetchCdcEvent(String changeId);
}
