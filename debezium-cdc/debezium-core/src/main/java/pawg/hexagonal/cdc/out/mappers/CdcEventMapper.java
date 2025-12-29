package pawg.hexagonal.cdc.out.mappers;

import java.util.List;
import org.mapstruct.Mapper;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.dto.EventOutDto;

@Mapper
public interface CdcEventMapper {
    EventOutDto cdcEventDomainToEventOutDto(CdcEventDomain cdcEventDomain);
    List<EventOutDto> cdcEventDomainListToEventOutDtoList(List<CdcEventDomain> cdcEventDomain);
    CdcEventDomain eventOutDtoToCdcEventDomain(EventOutDto eventOutDto);
    List<CdcEventDomain> eventOutDtoListToCdcEventDomainList(List<EventOutDto> eventOutDto);
}
