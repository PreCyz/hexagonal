package pawg.hexagonal.outbound.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pawg.hexagonal.domain.DataDomain;
import pawg.hexagonal.outbound.dto.CreateDataRequest;
import pawg.hexagonal.outbound.dto.CreateDataResponse;
import pawg.hexagonal.outbound.entities.Data;
import pawg.hexagonal.outbound.mappers.DataMapper;
import pawg.hexagonal.outbound.port.DBPort;
import pawg.hexagonal.outbound.repositories.DataRepository;

import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MySqlAdapter implements DBPort {
    private final DataRepository dataRepository;
    private final DataMapper mapper;

    @Override
    public Optional<CreateDataResponse> createData(DataDomain dataDomain) {
        Data saved = dataRepository.save(mapper.createDataRequestToEntity(request));
        return Optional.ofNullable(mapper.entityToCreateDataResponse(saved));
    }

    @Override
    public List<CreateDataResponse> createDataList(List<CreateDataRequest> request) {
        return List.of();
    }
}
