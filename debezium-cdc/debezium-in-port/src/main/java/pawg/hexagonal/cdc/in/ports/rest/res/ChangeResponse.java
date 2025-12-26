package pawg.hexagonal.cdc.in.ports.rest.res;

import java.time.LocalDateTime;

public record ChangeResponse (String changeId, Object valueBeforeChange, Object valueAfterChange, String operation,
    String databaseName, String tableName, LocalDateTime timestamp) {
}
