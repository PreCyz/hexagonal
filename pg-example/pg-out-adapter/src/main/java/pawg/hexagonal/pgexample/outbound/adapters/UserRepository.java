package pawg.hexagonal.pgexample.outbound.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.pgexample.outbound.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
