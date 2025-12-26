package pawg.hexagonal.cdc.in.ports.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;

@RequestMapping(path = "/change")
public interface CdcChangelogApi {

    @GetMapping(path = "/{changeId}", produces = "application/json")
    ResponseEntity<ChangeResponse> getChangeById(@PathVariable("changeId") String changeId);

}
