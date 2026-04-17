package pawg.hexagonal.example.outbound.mappers;

import org.mapstruct.*;
import pawg.hexagonal.example.domain.UserDomain;
import pawg.hexagonal.example.outbound.entities.UserEntity;

import java.util.UUID;

@Mapper
public interface UserMapper {
    @Mapping(target = "updatedAt", source = "createDateTime")
    @Mapping(target = "createdAt", source = "updateDateTime")
    @Mapping(target = "username", source = "name")
    @Mapping(target = "age", ignore = true)
    @Mapping(target = "metadata", qualifiedByName = "buildMetadataJson")
    UserEntity userDomainToEntity(UserDomain userDomain);

    @Mapping(target = "updateDateTime", source = "updatedAt")
    @Mapping(target = "createDateTime", source = "createdAt")
    @Mapping(target = "name", source = "username")
    UserDomain entityToUserDomain(UserEntity userEntity);

    @Named("buildMetadataJson")
    default String buildMetadataJson(String metadata) {
        return """
                {
                "guid" : "%s",
                "systemUser" : "%s"
                }
                """.formatted(UUID.randomUUID().toString(), System.getenv("USERNAME"));


    }
}
