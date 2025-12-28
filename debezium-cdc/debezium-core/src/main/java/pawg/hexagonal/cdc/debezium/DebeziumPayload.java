package pawg.hexagonal.cdc.debezium;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DebeziumPayload {
    private Long id;
    private Map<String, Object> before;
    private Map<String, Object> after;
    private DebeziumSource source;
    @JsonProperty("op")
    private String operation; // c = create, u = update, d = delete
    @JsonProperty("ts_ms")
    private Long timestampMillis;
    @JsonProperty("ts_us")
    private Long timestampMicros;
    @JsonProperty("ts_ns")
    private Long timestampNanos;
}
