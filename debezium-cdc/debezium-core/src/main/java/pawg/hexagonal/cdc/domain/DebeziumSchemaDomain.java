package pawg.hexagonal.cdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DebeziumSchemaDomain {
    private String type;
    private List<DebeziumSchemaFieldDomain> fields;
    private boolean optional;
    private String name;
}
