package pawg.hexagonal.cdc.out.params;

public record ChangeIdQueryParam(String dbName, String tableName, String idFieldName, Long id) {
}