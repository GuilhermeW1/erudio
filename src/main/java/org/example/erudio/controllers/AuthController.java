package org.example.erudio.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.example.erudio.data.dto.v1.security.AccountCredentialsDto;
import org.example.erudio.model.User;
import org.example.erudio.repositories.UserRepository;
import org.example.erudio.services.AuthServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@Tag(name = "Authentication endpoint")
public class AuthController {

    @Autowired
    AuthServices authServices;

    @Autowired
    UserRepository userRepository;

    @Operation(summary = "Authenticates a user token")
    @PostMapping("/signin")
    public ResponseEntity<?> signIn(@RequestBody AccountCredentialsDto dto) {
        if (    dto == null ||
                dto.getUsername() == null ||
                dto.getUsername().isBlank() ||
                dto.getPassword() == null ||
                dto.getPassword().isBlank()
        ) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid  client request");

        var token = authServices.signin(dto);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid  client request");

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

    @Operation(summary = "Refresh token for authenticated user and return a token")
    @PutMapping("/refresh/{username}")
    public ResponseEntity<?> refresh(@PathVariable String username, @RequestHeader("Authorization") String refreshToken) {
        if (username.isBlank() || refreshToken.isBlank())
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid  client request");

        var token = authServices.refreshToken(username, refreshToken);
        if (token == null) return ResponseEntity.status(HttpStatus.FORBIDDEN).body("Invalid  client request");

        return ResponseEntity.status(HttpStatus.OK).body(token);
    }

}
