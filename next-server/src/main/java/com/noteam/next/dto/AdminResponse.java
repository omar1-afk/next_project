package com.noteam.next.dto;

public record AdminResponse(
        String name,
        String email,
        String social_security_number,
        String image,
        int age) {
}
