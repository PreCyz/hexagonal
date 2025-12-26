package pawg.hexagonal.cdc.in.mappers;

import org.mapstruct.Mapper;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;

@Mapper
public interface ChangeResponseMapper {

    ChangeResponse cdcEventToChangeResponse(CdcEventDomain cdcEventDomain);
}
