package pawg.hexagonal.outbound.port.db;

import pawg.hexagonal.domain.DataDomain;

import java.util.List;
import java.util.Optional;

public interface DBPort {
    Optional<DataDomain> createData(DataDomain dataDomain);
    List<DataDomain> createDataList(List<DataDomain> dataDomainList);
}
