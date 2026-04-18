package pawg.hexagonal.pgexample.domain;

import java.time.LocalDateTime;

public record UserDomain(
        Long id,
        String name,
        String email,
        LocalDateTime createDateTime,
        LocalDateTime updateDateTime,
        String metadata
) {
}
