package pawg.hexagonal.example.outbound.adapters;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pawg.hexagonal.example.domain.UserDomain;
import pawg.hexagonal.example.out.port.DBPort;
import pawg.hexagonal.example.outbound.entities.UserEntity;
import pawg.hexagonal.example.outbound.mappers.UserMapper;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class MySqlAdapter implements DBPort {
    private final UserRepository userRepository;
    private final UserMapper mapper;

    @Override
    public Optional<UserDomain> createData(UserDomain userDomain) {
        UserEntity entity = mapper.userDomainToEntity(userDomain);
        LocalDateTime now = LocalDateTime.now();
        entity.setCreatedAt(now);
        entity.setUpdatedAt(now);
        UserEntity saved = userRepository.save(entity);
        return Optional.ofNullable(mapper.entityToUserDomain(saved));
    }

    @Override
    public List<UserDomain> createDataList(List<UserDomain> userDomainList) {
        return List.of();
    }

    @Override
    public Optional<UserDomain> findById(Long id) {
        return userRepository.findById(id).map(mapper::entityToUserDomain);
    }
}
