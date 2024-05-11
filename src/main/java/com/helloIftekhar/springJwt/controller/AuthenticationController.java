package com.helloIftekhar.springJwt.controller;

import com.helloIftekhar.springJwt.model.AuthenticationResponse;
import com.helloIftekhar.springJwt.model.Role;
import com.helloIftekhar.springJwt.model.User;
import com.helloIftekhar.springJwt.model.UserDto;
import com.helloIftekhar.springJwt.service.AuthenticationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@RestController
@CrossOrigin(origins = "http://localhost:4200")
public class AuthenticationController {

    private final AuthenticationService authService;

    public AuthenticationController(AuthenticationService authService) {
        this.authService = authService;
    }


    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponse> register(
            @RequestBody User request
            ) {
        return ResponseEntity.ok(authService.register(request));
    }

    @PostMapping("/login")
    public ResponseEntity<AuthenticationResponse> login(
            @RequestBody User request
    ) {
        return ResponseEntity.ok(authService.authenticate(request));
        }

    @PreAuthorize("hasRole('ADMIN')")

    @PutMapping("/users/put/{userId}")
    public ResponseEntity<AuthenticationResponse> updateUser(
            @PathVariable Long userId,
            @RequestBody User request
    ) {
        try {
            return ResponseEntity.ok(authService.updateUser(request, userId));
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("users/delete/{userId}")
    public ResponseEntity<AuthenticationResponse> deleteUser(
            @PathVariable Long userId
    ) {
        try {
            return ResponseEntity.ok(authService.deleteUser(userId));
        } catch (UsernameNotFoundException ex) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found", ex);
        }
    }
    @GetMapping("/users/get/parent")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> users = authService.getAllUsers()
                .stream()
                .filter(user -> user.getRole() == Role.USER )
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/users/get/prof")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<UserDto>> getAllUserss() {
        List<UserDto> users = authService.getAllUsers()
                .stream()
                .filter(user -> user.getRole() == null )
                .collect(Collectors.toList());
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
    @GetMapping("/users/{userId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<UserDto> getUserById(@PathVariable Long userId) {
        Optional<UserDto> user = authService.getUserById(userId);
        return user.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}








