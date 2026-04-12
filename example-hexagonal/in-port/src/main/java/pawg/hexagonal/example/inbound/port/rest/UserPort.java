package pawg.hexagonal.example.inbound.port.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pawg.hexagonal.example.inbound.dto.*;

@RequestMapping(produces = "application/json")
public interface UserPort {

    @PostMapping(consumes = "application/json")
    ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest createUserRequest);

    @GetMapping
    ResponseEntity<GetUserResponse> getData(@RequestParam(name = "page", required = false, defaultValue = "0") int page,
                                            @RequestParam(name = "size", required = false, defaultValue = "20") int size);

    @GetMapping(path = "/all")
    ResponseEntity<GetUserResponse> getAll();

}
