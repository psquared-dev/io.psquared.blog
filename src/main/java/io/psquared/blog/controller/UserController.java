package io.psquared.blog.controller;

import io.psquared.blog.dto.UserCreateRequest;
import io.psquared.blog.services.UserService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public void createUser(@Valid @RequestBody UserCreateRequest userCreateRequest){
        userService.createUser(userCreateRequest);
    }

    @PostMapping("/login")
    public String showUser(Authentication principal){
        return "user " + LocalDateTime.now() + " " + principal.getName() + " " + principal.getAuthorities();
    }
}
