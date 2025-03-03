package org.veedev.userservice.impl;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.veedev.userservice.dto.RegisterRequest;
import org.veedev.userservice.model.User;
import org.veedev.userservice.repository.UserRepository;
import org.veedev.userservice.service.UserService;

@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public ResponseEntity<String> register(RegisterRequest registerRequest) {
        if (registerRequest.getFirstName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("First name are required");
        } else if (registerRequest.getLastName() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Last name are required");
        } else if (userRepository.findByUsername(registerRequest.getUsername()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Username already exists");
        } else if (userRepository.findByEmail(registerRequest.getEmail()) != null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email already exists");
        } else if (registerRequest.getPassword() == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password is required");
        }
        User user = new User();
        user.setFirstName(registerRequest.getFirstName());
        user.setLastName(registerRequest.getLastName());
        user.setUsername(registerRequest.getUsername());
        user.setEmail(registerRequest.getEmail());
        user.setPassword(passwordEncoder.encode(registerRequest.getPassword()));
        userRepository.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body("User registered successfully");
    }
}
