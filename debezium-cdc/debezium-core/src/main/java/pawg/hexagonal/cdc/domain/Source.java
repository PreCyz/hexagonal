package pawg.hexagonal.cdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class Source {
    private String version;
    private String connector;
    private String name;
    private long ts_ms;
    private String snapshot;
    private String db;
    private String sequence;
    private long ts_us;
    private long ts_ns;
    private String table;
    private long server_id;
    private String gtid;
    private String file;
    private long pos;
    private long row;
    private long thread;
    private String query;
}
