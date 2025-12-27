package pawg.hexagonal.cdc.out.params;

import java.time.LocalDateTime;

public record ChangesInRangeQueryParam(LocalDateTime startTimestamp, LocalDateTime endTimestamp, int pageNumber, int pageSize) {
}
