package pawg.hexagonal.cdc.debezium;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DebeziumSchema {
    private String type;
    private List<DebeziumSchemaField> fields;
    private boolean optional;
    private String name;
    private String version;
}
