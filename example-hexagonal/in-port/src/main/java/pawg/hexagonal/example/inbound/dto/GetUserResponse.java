package pawg.hexagonal.example.inbound.dto;

import java.util.List;

public record GetUserResponse(List<DataResponse> data) {
}
