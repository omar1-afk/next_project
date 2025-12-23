package org.noteam.nextclient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Order(int id, String city,int city_id ,String region
        , String address, boolean flameable, boolean breakable
        , Integer price, State state, Integer weight
        , Integer shipment, String receiverEmail, String senderEmail
        , int boxesCount,LocalDate shippingDate) {
}