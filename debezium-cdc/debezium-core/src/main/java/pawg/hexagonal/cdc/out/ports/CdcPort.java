package pawg.hexagonal.cdc.out.ports;

import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.domain.ChangesInRangeDomain;

import java.util.List;
import java.util.Optional;

public interface CdcPort {
    void processChange(CdcEventDomain cdcEventDomain);
    CdcEventDomain fetchCdcEvent(String changeId);
    List<CdcEventDomain> fetchCdcEvents(ChangesInRangeDomain changesInRangeDomain);
    Optional<String> fetchChangeId(String dbName, String tableName, String idFieldName, Long id);
}
