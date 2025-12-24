package pawg.hexagonal.inbound.adapters.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pawg.hexagonal.inbound.dto.*;
import pawg.hexagonal.inbound.port.rest.Port;
import pawg.hexagonal.services.BusinessCase;

@RestController("/hexagonal")
public class DataController implements Port {
    private final BusinessCase businessCase;

    @Autowired
    public DataController(BusinessCase businessCase) {
        this.businessCase = businessCase;
    }

    @Override
    public ResponseEntity<CreateDataResponse> createData(CreateDataRequest createDataRequest) {
        return null;
    }

    @Override
    public ResponseEntity<GetDataResponse> getData(int page, int size) {
        return null;
    }
}
