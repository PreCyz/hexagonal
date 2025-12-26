package pawg.hexagonal.cdc.domain;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CdcEventDomain {
    private String dbId;
    private String changeId;
    private String databaseName;
    private String tableName;
    private String operation;
    private LocalDateTime timestamp;
    private Object valueBeforeChange;
    private Object valueAfterChange;
}
