package com.noteam.next.controllers;

import com.noteam.next.dto.LoginRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.noteam.next.dto.TokenResponse;

@RestController

class AuthController {

  @PostMapping("/login")
  public ResponseEntity<TokenResponse> login(
          @RequestBody LoginRequest loginRequest
          ) {
    return ResponseEntity.notFound().build();
  }

  @PostMapping("/register")
  public ResponseEntity<TokenResponse> register() {
    return ResponseEntity.notFound().build();
  }
}
