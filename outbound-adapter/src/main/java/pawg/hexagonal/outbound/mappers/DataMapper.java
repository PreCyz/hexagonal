package pawg.hexagonal.outbound.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.outbound.dto.CreateDataRequest;
import pawg.hexagonal.outbound.dto.CreateDataResponse;
import pawg.hexagonal.outbound.entities.Data;

@Mapper
public interface DataMapper {
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "createdAt", ignore = true)
    @Mapping(target = "id", ignore = true)
    Data createDataRequestToEntity(CreateDataRequest request);

    CreateDataResponse entityToCreateDataResponse(Data entity);
}
