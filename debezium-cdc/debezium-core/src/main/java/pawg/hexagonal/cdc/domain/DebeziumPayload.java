package pawg.hexagonal.cdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DebeziumPayload {
    private Object before;
    private Object after;
    private DebeziumSource source;
    private String op; // c = create, u = update, d = delete
    private String ts_ns;
}
