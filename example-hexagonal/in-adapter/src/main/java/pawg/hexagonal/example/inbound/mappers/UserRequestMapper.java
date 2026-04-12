package pawg.hexagonal.example.inbound.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.example.domain.UserDomain;
import pawg.hexagonal.example.inbound.dto.CreateUserRequest;
import pawg.hexagonal.example.inbound.dto.DataResponse;

import java.util.List;

@Mapper
public interface UserRequestMapper {
    @Mapping(target = "name", source = "username")
    @Mapping(target = "createDateTime", ignore = true)
    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "id", ignore = true)
    UserDomain userRequestToDomain(CreateUserRequest createUserRequest);

    @Mapping(target = "value", ignore = true)
    DataResponse userDomainToResponse(UserDomain userDomain);

    List<DataResponse> userDomainToResponseList(List<UserDomain> userDomains);
}
