package com.noteam.next.dto;

import com.noteam.next.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderRequest(int id, City city, String region
        , String address, boolean flameable, boolean breakable
        , int price, State state, int weight, Shipment shipment
        , Receiver receiver, Sender sender, int boxesCount
        , LocalDateTime createdAt, LocalDateTime updatedAt) {
}
