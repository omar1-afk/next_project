package com.noteam.next.dto;

import com.noteam.next.entities.Vehicle.VehicleType;

public record VehicleRequest(
        VehicleType type,
        String licensePlate,
        Integer weightLimit
) {}
