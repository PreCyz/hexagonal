package pawg.hexagonal.outbound.dto;

import java.time.LocalDateTime;

public record CreateDataResponse(long id, LocalDateTime createdAt, String email, String name, int age) {
}
