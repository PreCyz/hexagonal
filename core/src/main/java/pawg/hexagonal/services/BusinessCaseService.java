package pawg.hexagonal.services;

import org.springframework.stereotype.Service;
import pawg.hexagonal.domain.DataDomain;
import pawg.hexagonal.outbound.port.DBPort;

@Service
public class BusinessCaseService implements BusinessCase {

    private DBPort dbPort;

    @Override
    public boolean validate(DataDomain dataDomain) {
        if (dataDomain == null) {
            throw new IllegalArgumentException("dataDomain is null");
        }
        return dbPort.createData(dataDomain);
    }

    @Override
    public DataDomain saveDataDomain(DataDomain dataDomain) {
        return null;
    }
}
