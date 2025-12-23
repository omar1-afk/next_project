package com.noteam.next.dto;

public record AdminResponse(
        String name,
        String email,
        String socialSecurityNumber,
        String image,
        int age) {
}
