package pawg.hexagonal.cdc.out.dto;

import java.time.LocalDateTime;
import java.util.Set;

public record ChangesInRangeQueryParam(
        LocalDateTime startTimestamp,
        LocalDateTime endTimestamp,
        Set<String> operations,
        int pageNumber,
        int pageSize
) {
}
