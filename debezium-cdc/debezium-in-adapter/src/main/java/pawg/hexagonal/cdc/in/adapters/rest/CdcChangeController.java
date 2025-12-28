package pawg.hexagonal.cdc.in.adapters.rest;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pawg.hexagonal.cdc.in.mappers.ChangeRequestMapper;
import pawg.hexagonal.cdc.in.mappers.ChangeResponseMapper;
import pawg.hexagonal.cdc.in.ports.rest.CdcChangeApi;
import pawg.hexagonal.cdc.in.ports.rest.req.FetchChangesWithinDatesDto;
import pawg.hexagonal.cdc.in.ports.rest.res.ChangeResponse;
import pawg.hexagonal.cdc.services.DebeziumService;

import java.util.List;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RestController
public class CdcChangeController implements CdcChangeApi {

    private final DebeziumService debeziumService;
    private final ChangeResponseMapper responseMapper;
    private final ChangeRequestMapper requestMapper;

    @Override
    public ResponseEntity<List<ChangeResponse>> getChangeById(String changeId) {
        return ResponseEntity.ok(responseMapper.cdcEventsToChangeResponses(debeziumService.fetchChanges(changeId)));
    }

    @Override
    public ResponseEntity<List<ChangeResponse>> getHistoryByDate(
            FetchChangesWithinDatesDto fetchChangesWithinDatesDto, Integer pageNumber, Integer pageSize
    ) {
        return ResponseEntity.ok(
                responseMapper.cdcEventsToChangeResponses(debeziumService.fetchChanges(
                        requestMapper.changeRequestToChangeRequestDomain(fetchChangesWithinDatesDto, pageNumber, pageSize)
                ))
        );
    }
}
