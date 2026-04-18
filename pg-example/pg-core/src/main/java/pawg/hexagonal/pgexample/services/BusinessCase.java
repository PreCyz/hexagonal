package pawg.hexagonal.pgexample.services;

import pawg.hexagonal.pgexample.domain.UserDomain;

import java.util.Optional;

public interface BusinessCase {
    void validate(UserDomain userDomain);
    Optional<UserDomain> saveUserDomain(UserDomain userDomain);
    Optional<UserDomain> fetchById(Long userId);
}
