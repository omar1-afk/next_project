package com.noteam.next.dto;

public record DriverResponse(
		String name,
		String email,
		String social_security_number,
		boolean is_busy,
		String image,
		int age) {
}
