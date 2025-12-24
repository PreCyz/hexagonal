package pawg.hexagonal.outbound.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.outbound.entities.Data;

public interface DataRepository extends JpaRepository<Data, Long> {
}
