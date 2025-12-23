package com.noteam.next.dto;

public record DriverResponse(
        String name,
        String email,
        String socialSecurityNumber,
        boolean isBusy,
        String image,
        int age) {
}
