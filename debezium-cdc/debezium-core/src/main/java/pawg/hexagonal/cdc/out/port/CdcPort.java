package pawg.hexagonal.cdc.out.port;

import pawg.hexagonal.cdc.domain.Payload;

public interface CdcPort {
    void processChange(Payload payload);
}
