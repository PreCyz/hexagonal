package pawg.hexagonal.outbound.port;

import pawg.hexagonal.outbound.dto.CreateDataRequest;
import pawg.hexagonal.outbound.dto.CreateDataResponse;

import java.util.List;
import java.util.Optional;

public interface DBPort {
    Optional<CreateDataResponse> createData(DataDomain dataDomain);
    List<CreateDataResponse> createDataList(List<CreateDataRequest> request);
}
