package org.noteam.nextclient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Order(int id, String country, String city, String region
        , String address, boolean flameable, boolean breakable
        , int price, State state, int weight, LocalDate shippingDate
        , int shipment, int receiver, int sender
        , int boxesCount, LocalDateTime createdAt, LocalDate updatedAt) {
}