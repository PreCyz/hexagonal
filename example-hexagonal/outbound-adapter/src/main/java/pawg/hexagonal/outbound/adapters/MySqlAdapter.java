package pawg.hexagonal.outbound.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pawg.hexagonal.domain.DataDomain;
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
    public Optional<DataDomain> createData(DataDomain dataDomain) {
        Data saved = dataRepository.save(mapper.dataDomainToEntity(dataDomain));
        return Optional.ofNullable(mapper.entityToDataDomain(saved));
    }

    @Override
    public List<DataDomain> createDataList(List<DataDomain> dataDomainList) {
        return List.of();
    }
}
