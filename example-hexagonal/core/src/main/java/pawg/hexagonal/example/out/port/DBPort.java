package pawg.hexagonal.example.out.port;

import java.util.List;
import java.util.Optional;
import pawg.hexagonal.example.domain.UserDomain;

public interface DBPort {
    Optional<UserDomain> createData(UserDomain userDomain);
    List<UserDomain> createDataList(List<UserDomain> userDomainList);
    Optional<UserDomain> findById(Long id);
}
