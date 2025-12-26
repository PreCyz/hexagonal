package pawg.hexagonal.cdc.out.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.cdc.out.entities.Change;

import java.util.Optional;


public interface ChangeRepository extends JpaRepository<Change, String> {
    Optional<Change> findChangeByChangeId(String changeId);
}
