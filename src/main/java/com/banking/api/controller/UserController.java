package com.banking.api.controller;

import com.banking.api.dto.UserRegistrationRequest;
import com.banking.api.dto.UserResponse;
import com.banking.api.dto.AuthRequest;
import com.banking.api.dto.LoginResponse;
import com.banking.api.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/users")
@Tag(name = "Users", description = "User authentication and registration endpoints")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register User", description = "Creates a new user account with the provided credentials")
    public UserResponse registerUser(@Valid @RequestBody UserRegistrationRequest request) {
        return userService.register(request);
    }

    @PostMapping("/login")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Login User", description = "Authenticates a user and returns a JWT token with expiration details")
    public LoginResponse loginUser(@RequestBody AuthRequest authRequest) {
        return userService.login(authRequest);
    }
}
