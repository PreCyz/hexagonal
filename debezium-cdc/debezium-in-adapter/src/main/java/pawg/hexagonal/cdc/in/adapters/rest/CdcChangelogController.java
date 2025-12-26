package pawg.hexagonal.cdc.in.adapters.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pawg.hexagonal.cdc.in.mappers.ChangeResponseMapper;
import pawg.hexagonal.cdc.in.ports.rest.CdcChangelogApi;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;
import pawg.hexagonal.cdc.services.DebeziumService;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RestController
public class CdcChangelogController implements CdcChangelogApi {

    private final DebeziumService debeziumService;
    private final ChangeResponseMapper changeResponseMapper;

    @Override
    public ResponseEntity<ChangeResponse> getChangeById(String changeId) {
        ChangeResponse changeResponse = changeResponseMapper.cdcEventToChangeResponse(debeziumService.fetchChange(changeId));
        return ResponseEntity.ok(changeResponse);
    }
}
