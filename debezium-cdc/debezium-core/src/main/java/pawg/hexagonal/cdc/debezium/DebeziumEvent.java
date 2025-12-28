package pawg.hexagonal.cdc.debezium;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Getter;
import lombok.Setter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class DebeziumEvent {
    private DebeziumSchema schema;
    private DebeziumPayload payload;
}
