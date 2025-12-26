package pawg.hexagonal.cdc.out.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Entity
@Table(name = "changelog", schema = "cdc")
@Getter
@Setter
@ToString
public class ChangeEntity implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;
    @Column
    private String changeId;
    @Column
    private String operation;
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> valueBefore = new HashMap<>();
    @JdbcTypeCode(SqlTypes.JSON)
    private Map<String, Object> valueAfter = new HashMap<>();
    @JdbcTypeCode(SqlTypes.TIMESTAMP)
    private LocalDateTime timestamp;
    @Column
    private String tableName;
    @Column
    private String db;

}
