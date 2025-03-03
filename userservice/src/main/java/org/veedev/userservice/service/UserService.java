package org.veedev.userservice.service;

import org.springframework.http.ResponseEntity;
import org.veedev.userservice.dto.RegisterRequest;

public interface UserService {
    ResponseEntity<String> register(RegisterRequest registerRequest);
}
