package pawg.hexagonal.cdc.out.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;

import java.time.LocalDateTime;
import java.util.*;

@Mapper(imports = { UUID.class, LocalDateTime.class, Map.class, TypeReference.class })
public interface ChangeMapper {

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "changeId", defaultExpression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    @Mapping(target = "valueAfter", source = "valueAfterChange")
    @Mapping(target = "valueBefore", source = "valueBeforeChange")
    @Mapping(target = "db", source = "databaseName")
    ChangeEntity cdcEventToChange(CdcEventDomain cdcEventDomain);

    @Mapping(target = "valueBeforeChange", source = "valueBefore")
    @Mapping(target = "valueAfterChange", source = "valueAfter")
    @Mapping(target = "databaseName", source = "db")
    @Mapping(target = "dbId", ignore = true)
    CdcEventDomain changeToCdcEvent(ChangeEntity changeEntity);

    List<CdcEventDomain> changesToCdcEvents(List<ChangeEntity> changeEntities);
}
