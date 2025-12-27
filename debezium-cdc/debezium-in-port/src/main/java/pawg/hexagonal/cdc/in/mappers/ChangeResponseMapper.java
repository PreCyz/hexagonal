package pawg.hexagonal.cdc.in.mappers;

import org.mapstruct.Mapper;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;

import java.util.List;

@Mapper
public interface ChangeResponseMapper {

    ChangeResponse cdcEventToChangeResponse(CdcEventDomain cdcEventDomain);
    List<ChangeResponse> cdcEventsToChangeResponses(List<CdcEventDomain> cdcEventDomains);
}
