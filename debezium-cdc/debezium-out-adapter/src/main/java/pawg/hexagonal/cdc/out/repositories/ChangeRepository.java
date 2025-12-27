package pawg.hexagonal.cdc.out.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


public interface ChangeRepository extends JpaRepository<ChangeEntity, String> {
    Optional<ChangeEntity> findChangeByChangeId(String changeId);
    List<ChangeEntity> findAllByTimestampBetween(LocalDateTime timestampAfter, LocalDateTime timestampBefore, Pageable pageable);
}
