package pawg.hexagonal.domain;

import java.time.LocalDateTime;

public record DataDomain (
        long id,
        String name,
        String email,
        LocalDateTime createDateTime,
        LocalDateTime updateDateTime
) {
}
