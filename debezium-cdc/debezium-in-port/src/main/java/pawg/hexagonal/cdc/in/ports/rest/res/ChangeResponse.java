package pawg.hexagonal.cdc.in.ports.rest.res;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ChangeResponse {
    private String changeId;
    private Object valueBeforeChange;
    private Object valueAfterChange;
    private String operation;
    private String databaseName;
    private String tableName;
    private LocalDateTime timestamp;
}
