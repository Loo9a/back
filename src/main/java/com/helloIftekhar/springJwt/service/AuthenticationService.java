package com.helloIftekhar.springJwt.service;


import com.helloIftekhar.springJwt.model.*;
import com.helloIftekhar.springJwt.repository.TokenRepository;
import com.helloIftekhar.springJwt.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AuthenticationService {

    private final UserRepository repository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private final TokenRepository tokenRepository;

    private final AuthenticationManager authenticationManager;

    public AuthenticationService(UserRepository repository,
                                 PasswordEncoder passwordEncoder,
                                 JwtService jwtService,
                                 TokenRepository tokenRepository,
                                 AuthenticationManager authenticationManager) {
        this.repository = repository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
        this.tokenRepository = tokenRepository;
        this.authenticationManager = authenticationManager;
    }

    public AuthenticationResponse register(User request) {

        // check if user already exist. if exist than authenticate the user
        if(repository.findByUsername(request.getUsername()).isPresent()) {
            return new AuthenticationResponse(null,  "User already exist",null,0);
        }

        User user = new User();
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setUsername(request.getUsername());
        user.setPassword(passwordEncoder.encode(request.getPassword()));


        user.setRole(request.getRole());

        user = repository.save(user);

        String jwt = jwtService.generateToken(user);

        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User registration was successful",user.getRole(), user.getId());

    }

    public AuthenticationResponse authenticate(User request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getUsername(),
                        request.getPassword()
                )
        );

        User user = repository.findByUsername(request.getUsername()).orElseThrow();
        String jwt = jwtService.generateToken(user);

        revokeAllTokenByUser(user);
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt ,"User login was successful" ,user.getRole(), user.getId());

    }
    private void revokeAllTokenByUser(User user) {
        List<Token> validTokens = tokenRepository.findAllTokensByUser(user.getId());
        if(validTokens.isEmpty()) {
            return;
        }

        validTokens.forEach(t-> {
            t.setLoggedOut(true);
        });

        tokenRepository.saveAll(validTokens);
    }
    private void saveUserToken(String jwt, User user) {
        Token token = new Token();
        token.setToken(jwt);
        token.setLoggedOut(false);
        token.setUser(user);
        tokenRepository.save(token);
    }

    private Set<String> blacklistedTokens = new HashSet<>();

    // Method to invalidate the token
    public void invalidateToken(String token) {
        // Add the token to the blacklist
        blacklistedTokens.add(token);
    }

    // Method to check if a token is blacklisted (invalid)
    public boolean isTokenBlacklisted(String token) {
        return blacklistedTokens.contains(token);
    }




    @Transactional
    public AuthenticationResponse updateUser(User request, Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Update user details
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        // Save updated user
        user = repository.save(user);

        // Generate new JWT token
        String jwt = jwtService.generateToken(user);

        // Revoke previous tokens for this user
        revokeAllTokenByUser(user);

        // Save new token
        saveUserToken(jwt, user);

        return new AuthenticationResponse(jwt, "User details updated successfully", user.getRole(), user.getId());
    }

    @Transactional
    public AuthenticationResponse deleteUser(Long userId) {
        User user = repository.findById(userId)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        // Revoke all tokens for the user
        revokeAllTokenByUser(user);

        // Delete user from repository
        repository.delete(user);

        return new AuthenticationResponse(null, "User deleted successfully", null, 0);
    }



    public List<UserDto> getAllUsers() {
        List<User> users = repository.findAll();
        return users.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private UserDto convertToDto(User user) {
        UserDto userDto = new UserDto();
        userDto.setId(user.getId());
        userDto.setFirstName(user.getFirstName());
        userDto.setLastName(user.getLastName());
        userDto.setUsername(user.getUsername());
        userDto.setRole(user.getRole());
        return userDto;
    }


    public Optional<UserDto> getUserById(Long userId) {
        Optional<User> userOptional = repository.findById(userId);
        return userOptional.map(this::convertToDto);
    }
}
