package pawg.hexagonal.outbound.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.domain.DataDomain;
import pawg.hexagonal.outbound.entities.Data;

@Mapper
public interface DataMapper {
    @Mapping(target = "updatedAt", source = "createDateTime")
    @Mapping(target = "createdAt", source = "updateDateTime")
    @Mapping(target = "age", ignore = true)
    Data dataDomainToEntity(DataDomain dataDomain);


    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    DataDomain entityToDataDomain(Data saved);
}
