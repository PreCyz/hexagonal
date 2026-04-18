package pawg.hexagonal.pgexample.outbound.adapters;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import pawg.hexagonal.pgexample.domain.UserDomain;
import pawg.hexagonal.pgexample.out.port.DBPort;
import pawg.hexagonal.pgexample.outbound.entities.UserEntity;
import pawg.hexagonal.pgexample.outbound.mappers.UserMapper;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class PostgreSqlAdapter implements DBPort {
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
