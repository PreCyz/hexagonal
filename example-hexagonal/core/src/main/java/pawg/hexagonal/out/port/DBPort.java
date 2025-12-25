package pawg.hexagonal.out.port;

import pawg.hexagonal.domain.DataDomain;

import java.util.List;
import java.util.Optional;

public interface DBPort {
    Optional<DataDomain> createData(DataDomain dataDomain);
    List<DataDomain> createDataList(List<DataDomain> dataDomainList);
}
