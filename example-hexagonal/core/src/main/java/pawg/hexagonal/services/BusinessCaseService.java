package pawg.hexagonal.services;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pawg.hexagonal.domain.DataDomain;
import pawg.hexagonal.out.port.DBPort;

import java.util.Optional;

@Service
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class BusinessCaseService implements BusinessCase {

    private final DBPort dbPort;

    @Override
    public void validate(DataDomain dataDomain) {
        if (dataDomain == null) {
            throw new IllegalArgumentException("dataDomain is null");
        }
    }

    @Override
    public Optional<DataDomain> saveDataDomain(DataDomain dataDomain) {
        return dbPort.createData(dataDomain);
    }
}
