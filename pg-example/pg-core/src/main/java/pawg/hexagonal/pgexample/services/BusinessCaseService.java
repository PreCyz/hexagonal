package pawg.hexagonal.pgexample.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pawg.hexagonal.pgexample.domain.UserDomain;
import pawg.hexagonal.pgexample.out.port.DBPort;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessCaseService implements BusinessCase {

    private final DBPort dbPort;

    @Override
    public void validate(UserDomain userDomain) {
        if (userDomain == null) {
            throw new IllegalArgumentException("userDomain is null");
        }
    }

    @Override
    public Optional<UserDomain> saveUserDomain(UserDomain userDomain) {
        validate(userDomain);
        return dbPort.createData(userDomain);
    }

    @Override
    public Optional<UserDomain> fetchById(Long id) {
        return dbPort.findById(id);
    }
}
