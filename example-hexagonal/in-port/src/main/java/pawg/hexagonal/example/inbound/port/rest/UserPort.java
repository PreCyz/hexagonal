package pawg.hexagonal.example.inbound.port.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pawg.hexagonal.example.inbound.dto.CreateUserRequest;
import pawg.hexagonal.example.inbound.dto.CreateUserResponse;
import pawg.hexagonal.example.inbound.dto.GetUserResponse;

@RequestMapping(path = "/users", produces = "application/json")
public interface UserPort {

    @PostMapping(consumes = "application/json")
    ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest createUserRequest);

    @GetMapping
    ResponseEntity<GetUserResponse> getData(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(name = "size", required = false, defaultValue = "20") int size);

    @GetMapping(path = "/all")
    ResponseEntity<GetUserResponse> getAll();

}
