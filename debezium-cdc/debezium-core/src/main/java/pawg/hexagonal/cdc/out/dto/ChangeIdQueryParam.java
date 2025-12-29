package pawg.hexagonal.cdc.out.dto;

public record ChangeIdQueryParam(String dbName, String tableName, String idFieldName, Long id) {
}