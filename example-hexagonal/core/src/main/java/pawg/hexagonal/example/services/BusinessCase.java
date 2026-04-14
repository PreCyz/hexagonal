package pawg.hexagonal.example.services;

import java.util.Optional;
import pawg.hexagonal.example.domain.UserDomain;

public interface BusinessCase {
    void validate(UserDomain userDomain);
    Optional<UserDomain> saveUserDomain(UserDomain userDomain);
    Optional<UserDomain> fetchById(Long userId);
}
