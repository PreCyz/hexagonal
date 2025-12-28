package pawg.hexagonal.cdc.debezium;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DebeziumSource {
    private String version;
    private String connector;
    private String name;
    private String snapshot;
    private String db;
    private String sequence;
    private String table;
    private String gtid;
    private String file;
    private String query;
    @JsonProperty("ts_ms")
    private Long timestampMillis;
    @JsonProperty("ts_us")
    private Long timestampMicros;
    @JsonProperty("ts_ns")
    private Long timestampNanos;
    @JsonProperty("server_id")
    private Long serverId;
    private Long pos;
    private Long row;
    private Long thread;
}
