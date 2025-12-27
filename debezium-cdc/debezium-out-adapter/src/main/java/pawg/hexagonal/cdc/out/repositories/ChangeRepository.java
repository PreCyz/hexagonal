package pawg.hexagonal.cdc.out.repositories;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;

import java.time.LocalDateTime;
import java.util.*;


public interface ChangeRepository extends JpaRepository<ChangeEntity, String> {
    Optional<ChangeEntity> findChangeByChangeId(String changeId);
    List<ChangeEntity> findAllByTimestampBetween(LocalDateTime timestampAfter, LocalDateTime timestampBefore, Pageable pageable);
    @Query(nativeQuery = true,
            value = "SELECT change_id FROM cdc.changelog c " +
                    "WHERE c.table_name = :tableName " +
                        "AND c.db = :dbName " +
                        "AND (JSON_EXTRACT(c.value_before, CONCAT('$.', :idFieldName)) = CAST(:idValue AS JSON) " +
                            "OR JSON_EXTRACT(c.value_after, CONCAT('$.', :idFieldName)) = CAST(:idValue AS JSON))"
    )
    Set<String> findChangeId(@Param("dbName") String dbName,
                             @Param("tableName") String tableName,
                             @Param("idFieldName") String idFieldName,
                             @Param("idValue") String idValue);
}
