package pawg.hexagonal.cdc.in.mappers;

import org.mapstruct.Mapper;
import pawg.hexagonal.cdc.domain.CdcEvent;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;

@Mapper
public interface ChangeResponseMapper {

    ChangeResponse cdcEventToChangeResponse(CdcEvent cdcEvent);
}
