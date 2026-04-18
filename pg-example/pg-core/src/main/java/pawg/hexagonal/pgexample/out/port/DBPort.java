package pawg.hexagonal.pgexample.out.port;

import pawg.hexagonal.pgexample.domain.UserDomain;

import java.util.List;
import java.util.Optional;

public interface DBPort {
    Optional<UserDomain> createData(UserDomain userDomain);
    List<UserDomain> createDataList(List<UserDomain> userDomainList);
    Optional<UserDomain> findById(Long id);
}
