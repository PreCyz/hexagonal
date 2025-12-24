package pawg.hexagonal.services;

import pawg.hexagonal.domain.DataDomain;

public interface BusinessCase {
    boolean validate(DataDomain dataDomain);
    DataDomain saveDataDomain(DataDomain dataDomain);
}
