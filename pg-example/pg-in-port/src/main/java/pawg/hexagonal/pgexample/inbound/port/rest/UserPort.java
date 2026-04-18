package pawg.hexagonal.pgexample.inbound.port.rest;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import pawg.hexagonal.pgexample.inbound.dto.*;

@RequestMapping(path = "/users", produces = "application/json")
public interface UserPort {

    @PostMapping(consumes = "application/json")
    ResponseEntity<CreateUserResponse> createUser(@RequestBody @Validated CreateUserRequest createUserRequest);

    @GetMapping("/{id}")
    ResponseEntity<GetUserResponse> findUser(@PathVariable Long id,
            @RequestParam(name = "page", required = false, defaultValue = "0") int page,
            @RequestParam(name = "size", required = false, defaultValue = "20") int size);

    @GetMapping(path = "/all")
    ResponseEntity<GetUserResponse> getAll();

}
