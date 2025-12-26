package pawg.hexagonal.cdc.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@JsonIgnoreProperties(ignoreUnknown = true)
@Data
public class DebeziumEventDomain {
    private DebeziumPayloadDomain payload;
}
