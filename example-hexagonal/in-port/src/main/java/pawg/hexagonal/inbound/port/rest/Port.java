package pawg.hexagonal.inbound.port.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pawg.hexagonal.inbound.dto.*;

public interface Port {

    @PostMapping(path = "/data", consumes = {"application/json"}, produces = "application/json")
    ResponseEntity<CreateDataResponse> createData(@RequestBody @Validated CreateDataRequest createDataRequest);

    @GetMapping(path = "/data", produces = "application/json")
    ResponseEntity<GetDataResponse> getData(int page, int size);


}
