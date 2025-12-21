package com.noteam.next.services;

import com.noteam.next.entities.Admin;
import com.noteam.next.entities.Driver;
import com.noteam.next.models.User;
import com.noteam.next.utils.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class AuthService {
	private final JwtUtil util;
	private final UserService userService;
	private final Logger logger = Logger.getLogger(AuthService.class.getName());

	AuthService(JwtUtil util, UserService userService) {
		this.util = util;
		this.userService = userService;
	}

	public String hashPassword(String password) {
		return BCrypt.hashpw(password, BCrypt.gensalt());
	}

	public boolean checkPassword(String password, String hashedPassword) {
		return BCrypt.checkpw(password, hashedPassword);
	}

	public String login(String email, String password) throws HttpClientErrorException {
		Optional<User> optionalUser = userService.findByEmail(email);
		if (optionalUser.isEmpty()) {
			throw new HttpClientErrorException(HttpStatus.NOT_FOUND);
		}
		User user = optionalUser.get();
		if (!checkPassword(password, user.getPassword())) {
			throw new HttpClientErrorException(HttpStatus.UNAUTHORIZED);
		}
		return util.generateToken(user.getId());
	}

	public boolean validateEmployee(String token) {
		return util.validateToken(token);
	}

	public Optional<User> getEmployee(String token) {
		Optional<User> employee = Optional.empty();
		if (!validateEmployee(token)) {
			return employee;
		}
		int id = util.getIdFromToken(token);
		try {
			employee = userService.findById(id);
		} catch (NumberFormatException e) {
			System.out.println("Long parsing for id from string to long");
		}
		return employee;
	}

	public Admin createAdmin(Admin admin) {
		logger.severe("SDASDASD " + admin.getPassword());
		admin.setPassword(
				hashPassword(admin.getPassword()));
		logger.severe("SDASDASD " + admin.getPassword());
		return userService.createAdmin(admin);
	}

	public Driver createDriver(Driver driver) {
		driver.setPassword(
				hashPassword(driver.getPassword()));
		return userService.createDriver(driver);
	}
}
