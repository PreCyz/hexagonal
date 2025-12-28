package pawg.hexagonal.cdc.domain;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.Map;

@Data
public class CdcEventDomain {
    private String dbId;
    private String changeId;
    private String databaseName;
    private String tableName;
    private String operation;
    private LocalDateTime timestamp;
    private Map<String, Object> valueBeforeChange;
    private Map<String, Object> valueAfterChange;
}
