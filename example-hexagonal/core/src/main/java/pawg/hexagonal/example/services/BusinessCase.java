package pawg.hexagonal.example.services;

import pawg.hexagonal.example.domain.UserDomain;

import java.util.Optional;

public interface BusinessCase {
    void validate(UserDomain userDomain);
    Optional<UserDomain> saveUserDomain(UserDomain userDomain);
}
