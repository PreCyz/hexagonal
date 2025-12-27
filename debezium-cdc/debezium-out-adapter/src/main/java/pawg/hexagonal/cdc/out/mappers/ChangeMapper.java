package pawg.hexagonal.cdc.out.mappers;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mapstruct.*;
import pawg.hexagonal.cdc.domain.CdcEventDomain;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;

import java.time.LocalDateTime;
import java.util.*;

@Mapper(imports = { UUID.class, LocalDateTime.class, Map.class, TypeReference.class })
public abstract class ChangeMapper {

    protected ObjectMapper objectMapper = new ObjectMapper();

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "changeId", expression = "java(UUID.randomUUID().toString())")
    @Mapping(target = "timestamp", expression = "java(LocalDateTime.now())")
    @Mapping(target = "valueAfter", source = "valueAfterChange", qualifiedByName = "convertToMap")
    @Mapping(target = "valueBefore", source = "valueBeforeChange", qualifiedByName = "convertToMap")
    @Mapping(target = "db", source = "databaseName")
    public abstract ChangeEntity cdcEventToChange(CdcEventDomain cdcEventDomain);

    @Mapping(target = "valueBeforeChange", source = "valueBefore")
    @Mapping(target = "valueAfterChange", source = "valueAfter")
    @Mapping(target = "databaseName", source = "db")
    @Mapping(target = "dbId", ignore = true)
    public abstract CdcEventDomain changeToCdcEvent(ChangeEntity changeEntity);

    @Named("convertToMap")
    protected Map<String, Object> convertToMap(Object source) {
        if (source == null) return null;
        return objectMapper.convertValue(source, new TypeReference<>() {});
    }

    public abstract List<CdcEventDomain> changesToCdcEvents(List<ChangeEntity> changeEntities);
}
