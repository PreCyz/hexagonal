package pawg.hexagonal.cdc.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;

import java.util.Optional;


public interface ChangeRepository extends JpaRepository<ChangeEntity, String> {
    Optional<ChangeEntity> findChangeByChangeId(String changeId);
}
