package pawg.hexagonal.cdc.in.ports.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pawg.hexagonal.cdc.in.ports.rest.req.FetchChangesWithinDatesDto;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;

import java.util.List;

@RequestMapping(path = "/change", produces = "application/json", consumes = "application/json")
public interface CdcChangeApi {

    @GetMapping(path = "/{changeId}")
    ResponseEntity<ChangeResponse> getChangeById(@PathVariable String changeId);

    @PostMapping
    ResponseEntity<List<ChangeResponse>> getHistoryByDate(@Validated @RequestBody FetchChangesWithinDatesDto fetchChangesWithinDatesDto,
                                                          @RequestParam(name = "page", required = false) Integer pageNumber,
                                                          @RequestParam(name = "size", required = false) Integer pageSize
    );

}
