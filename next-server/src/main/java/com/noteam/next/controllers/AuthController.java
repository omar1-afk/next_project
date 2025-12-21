package com.noteam.next.controllers;

import com.noteam.next.dto.LoginRequest;
import com.noteam.next.dto.MeResponse;
import com.noteam.next.models.User;
import com.noteam.next.services.AuthService;
import com.noteam.next.services.UserService;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.noteam.next.dto.TokenResponse;
import org.springframework.web.client.HttpClientErrorException;

import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Logger;

@RestController
@RequestMapping("/api/v1/")
class AuthController {
	private static final Logger logger = Logger.getLogger(AuthController.class.getName());

	private final AuthService auth;
	private final UserService service;

	public AuthController(AuthService auth, UserService service) {
		this.auth = auth;
		this.service = service;
	}

	@GetMapping("/all")
	public ResponseEntity<Object> all() {
		ArrayList<User> users = service.findAll();
		return ResponseEntity.ok(users);
	}

	@GetMapping("/me")
	public ResponseEntity<MeResponse> me(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String header) {
		String token = header.substring("Bearer ".length());
		Optional<User> employee = auth.getEmployee(token);
		if (employee.isPresent()) {
			User user = employee.get();
			MeResponse response = new MeResponse(
					user.getId(),
					user.getEmail());
			return ResponseEntity
					.ok(response);
		}
		return ResponseEntity
				.status(HttpStatus.UNAUTHORIZED)
				.build();
	}

	@PostMapping("/login")
	public ResponseEntity<TokenResponse> login(
			@RequestBody LoginRequest loginRequest) {
		try {
			System.out.println(loginRequest);
			String token = auth.login(loginRequest.getEmail(), loginRequest.getPassword());
			TokenResponse response = new TokenResponse(token);
			return ResponseEntity.ok(response);
		} catch (HttpClientErrorException e) {
			System.out.println(e.getMessage());
			return ResponseEntity.status(e.getStatusCode()).build();
		}
	}
}
