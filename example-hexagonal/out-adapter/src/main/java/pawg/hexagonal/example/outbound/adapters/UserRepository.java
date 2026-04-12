package pawg.hexagonal.example.outbound.adapters;

import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.example.outbound.entities.UserEntity;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
