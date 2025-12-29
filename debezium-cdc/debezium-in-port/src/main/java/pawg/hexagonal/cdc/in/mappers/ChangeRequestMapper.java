package pawg.hexagonal.cdc.in.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.cdc.in.ports.rest.req.FetchChangesWithinDatesDto;
import pawg.hexagonal.cdc.out.dto.ChangesInRangeQueryParam;

@Mapper
public interface ChangeRequestMapper {

    @Mapping(target = "pageSize", source = "pageSize")
    @Mapping(target = "pageNumber", source = "pageNumber")
    ChangesInRangeQueryParam changeRequestToChangeRequestDomain(FetchChangesWithinDatesDto dto, Integer pageNumber, Integer pageSize);
}
