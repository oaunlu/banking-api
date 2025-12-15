package com.banking.api.service;

import com.banking.api.dto.UserRegistrationRequest;
import com.banking.api.dto.UserResponse;
import com.banking.api.dto.AuthRequest;
import com.banking.api.dto.LoginResponse;
import com.banking.api.entity.User;
import com.banking.api.security.JwtService;
import com.banking.api.security.UserInfoDetails;
import com.banking.api.repository.UserRepository;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class UserService implements UserDetailsService {

    private final UserRepository repository;
    private final PasswordEncoder encoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public UserService(UserRepository repository, PasswordEncoder passwordEncoder,
            JwtService jwtService, @Lazy AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.encoder = passwordEncoder;
        this.jwtService = jwtService;
        this.authenticationManager = authenticationManager;
    }

    public UserResponse register(@NonNull UserRegistrationRequest request) {
        User user = new User();
        user.setUsername(request.username());
        user.setEmail(request.email());
        user.setPassword(encoder.encode(request.password()));
        user.setRoles(request.roles() != null ? request.roles() : "ROLE_USER");

        User savedUser = repository.save(user);

        return new UserResponse(
                savedUser.getId(),
                savedUser.getUsername(),
                savedUser.getEmail(),
                savedUser.getCreatedAt(),
                savedUser.getRoles());
    }

    public LoginResponse login(@NonNull AuthRequest authRequest) {

        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                authRequest.username(), authRequest.password());
        Authentication authentication = authenticationManager.authenticate(usernamePasswordAuthenticationToken);

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(authRequest.username());
            LocalDateTime now = LocalDateTime.now();
            LocalDateTime expiresAt = jwtService.getExpirationAsLocalDateTime(token);

            return new LoginResponse(token, "Bearer", now, expiresAt);
        }
        throw new UsernameNotFoundException("Invalid user request!");
    }

    // Method to load user details by username (email)
    @Override
    public UserDetails loadUserByUsername(@NonNull String username) throws UsernameNotFoundException {
        // Fetch user from the database by email (username)
        Optional<User> user = repository.findByEmail(username);

        if (user.isEmpty()) {
            throw new UsernameNotFoundException("User not found with email: " + username);
        }

        return new UserInfoDetails(user.get());
    }
}
