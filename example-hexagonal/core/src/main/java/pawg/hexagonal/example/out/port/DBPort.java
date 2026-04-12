package pawg.hexagonal.example.out.port;

import pawg.hexagonal.example.domain.UserDomain;

import java.util.List;
import java.util.Optional;

public interface DBPort {
    Optional<UserDomain> createData(UserDomain userDomain);
    List<UserDomain> createDataList(List<UserDomain> userDomainList);
}
