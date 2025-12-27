package pawg.hexagonal.cdc.in.ports.rest.req;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record FetchChangesWithinDatesDto(
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime startTimestamp,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss") LocalDateTime endTimestamp
) { }
