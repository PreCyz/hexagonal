package pawg.hexagonal.cdc.out.repositories;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.NativeQuery;
import org.springframework.data.repository.query.Param;
import pawg.hexagonal.cdc.out.entities.ChangeEntity;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;


public interface ChangeRepository extends JpaRepository<ChangeEntity, String> {
    List<ChangeEntity> findChangeByChangeId(String changeId);

    Page<ChangeEntity> findAllByTimestampBetweenAndOperationIn(
            LocalDateTime timestampAfter,
            LocalDateTime timestampBefore,
            Set<String> operations,
            Pageable pageable
    );

    @NativeQuery(value = "SELECT change_id FROM cdc.changelog c " +
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
