package pawg.hexagonal.cdc.out.dto;

import java.time.LocalDateTime;
import java.util.Map;

public record EventOutDto(
        String dbId,
        String changeId,
        String databaseName,
        String tableName,
        String operation,
        LocalDateTime timestamp,
        Map<String, Object>valueBeforeChange,
        Map<String, Object> valueAfterChange
) { }
