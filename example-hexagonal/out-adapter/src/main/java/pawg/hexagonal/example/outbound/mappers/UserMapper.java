package pawg.hexagonal.example.outbound.mappers;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pawg.hexagonal.example.domain.UserDomain;
import pawg.hexagonal.example.outbound.entities.UserEntity;

@Mapper
public interface UserMapper {
    @Mapping(target = "updatedAt", source = "createDateTime")
    @Mapping(target = "createdAt", source = "updateDateTime")
    @Mapping(target = "username", source = "name")
    @Mapping(target = "age", ignore = true)
    UserEntity userDomainToEntity(UserDomain userDomain);


    @Mapping(target = "updateDateTime", ignore = true)
    @Mapping(target = "createDateTime", ignore = true)
    UserDomain entityToUserDomain(UserEntity saved);
}
