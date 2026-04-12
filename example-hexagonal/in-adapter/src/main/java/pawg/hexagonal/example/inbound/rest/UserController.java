package pawg.hexagonal.example.inbound.rest;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;
import pawg.hexagonal.example.domain.UserDomain;
import pawg.hexagonal.example.inbound.dto.*;
import pawg.hexagonal.example.inbound.mappers.UserRequestMapper;
import pawg.hexagonal.example.inbound.port.rest.UserPort;
import pawg.hexagonal.example.services.BusinessCase;

import java.util.*;

@RequiredArgsConstructor(onConstructor = @__(@Autowired))
@Slf4j
@RestController
public class UserController implements UserPort {
    private final BusinessCase businessCase;
    private final UserRequestMapper userRequestMapper;

    @PostConstruct
    public void init() {
        log.info("Init data controller");
    }

    @Override
    public ResponseEntity<CreateUserResponse> createUser(CreateUserRequest createUserRequest) {
        Optional<UserDomain> userDomain = businessCase.saveUserDomain(userRequestMapper.userRequestToDomain(createUserRequest));
        return userDomain.map(domain -> ResponseEntity.ok(new CreateUserResponse(domain.id())))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Override
    public ResponseEntity<GetUserResponse> getData(int page, int size) {
        return ResponseEntity.ok(new GetUserResponse(new ArrayList<>()));
    }

    @Override
    public ResponseEntity<GetUserResponse> getAll() {
        return ResponseEntity.ok(new GetUserResponse(List.of()));
    }

}
