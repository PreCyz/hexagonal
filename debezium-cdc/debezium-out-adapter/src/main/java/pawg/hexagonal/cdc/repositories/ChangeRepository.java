package pawg.hexagonal.cdc.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.cdc.entities.Change;


public interface ChangeRepository extends JpaRepository<Change, String> {
}
