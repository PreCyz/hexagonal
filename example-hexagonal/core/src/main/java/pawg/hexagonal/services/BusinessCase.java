package pawg.hexagonal.services;

import pawg.hexagonal.domain.DataDomain;

import java.util.Optional;

public interface BusinessCase {
    void validate(DataDomain dataDomain);
    Optional<DataDomain> saveDataDomain(DataDomain dataDomain);
}
