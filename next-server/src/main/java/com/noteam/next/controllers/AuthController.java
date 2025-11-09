package com.noteam.next.controllers;

import com.noteam.next.dto.LoginRequest;
import com.noteam.next.dto.MeResponse;
import com.noteam.next.models.User;
import com.noteam.next.services.AuthService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.noteam.next.dto.TokenResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;

@RestController
class AuthController {

    private final AuthService auth;

    public AuthController(AuthService auth) {
        this.auth = auth;
    }

    @GetMapping("/me") public ResponseEntity<MeResponse> me(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String header
    ) {
        String token = header.substring("Bearer ".length());
        Optional<User> employee = auth.getEmployee(token);
        if (employee.isPresent()) {
            User user = employee.get();
            MeResponse response = new MeResponse(
                    user.getId(),
                    user.getEmail()
            );
            return ResponseEntity
                    .ok(response);
        }
        return ResponseEntity
                .status(HttpStatus.UNAUTHORIZED)
                .build();
    }

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(
          @RequestBody LoginRequest loginRequest
          ) {
        try {
            System.out.println(loginRequest);
            String token = auth.login(loginRequest.getEmail(), loginRequest.getPassword());
            TokenResponse response = new TokenResponse(token);
            return ResponseEntity.ok(response);
        } catch(HttpClientErrorException e) {
            System.out.println(e.getMessage());
            return new ResponseEntity(e.getStatusText(), e.getStatusCode());
        }
  }

  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register(
          @RequestBody User user
  ) {
        String token = auth.register(user);
        TokenResponse response = new TokenResponse(token);
        return ResponseEntity.ok(response);
  }
}
