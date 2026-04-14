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

    @Mapping(target = "updateDateTime", source = "updatedAt")
    @Mapping(target = "createDateTime", source = "createdAt")
    @Mapping(target = "name", source = "username")
    UserDomain entityToUserDomain(UserEntity userEntity);
}
