package org.noteam.nextclient.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record Order(int id, String city,int city_id ,String region
        , String address, boolean flameable, boolean breakable
        , int price, State state, int weight
        , int shipment, int receiver, int sender
        , int boxesCount,LocalDate shippingDate) {
}