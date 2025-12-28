package pawg.hexagonal.cdc.debezium;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DebeziumSchemaField {
    private String type;
    private boolean optional;
    private List<DebeziumSchemaField> fields;
    private String field;
    private String name;
    private String version;
}
