package pawg.hexagonal.cdc.domain;

import java.time.LocalDateTime;

public record ChangesInRangeDomain(LocalDateTime startTimestamp, LocalDateTime endTimestamp, int pageNumber, int pageSize) {
}
