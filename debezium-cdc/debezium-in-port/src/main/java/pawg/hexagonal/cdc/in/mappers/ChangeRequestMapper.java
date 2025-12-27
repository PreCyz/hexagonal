package pawg.hexagonal.cdc.in.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.cdc.domain.ChangesInRangeDomain;
import pawg.hexagonal.cdc.in.ports.rest.req.FetchChangesWithinDatesDto;

@Mapper
public interface ChangeRequestMapper {

    @Mapping(target = "pageSize", source = "pageSize")
    @Mapping(target = "pageNumber", source = "pageNumber")
    ChangesInRangeDomain changeRequestToChangeRequestDomain(FetchChangesWithinDatesDto dto, Integer pageNumber, Integer pageSize);
}
