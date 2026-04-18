package pawg.hexagonal.pgexample.inbound.dto;

import java.util.List;

public record GetUserResponse(List<DataResponse> data) {
}
