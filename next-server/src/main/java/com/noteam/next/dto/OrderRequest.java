package com.noteam.next.dto;

import com.noteam.next.entities.Receiver;
import com.noteam.next.entities.Sender;
import com.noteam.next.entities.Shipment;
import com.noteam.next.entities.State;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderRequest(int id, String country, String city, String region
        , String address, boolean flameable, boolean breakable
        , int price, State state, int weight, LocalDate shippingDate
        , Shipment shipment, Receiver receiver, Sender sender
        , int boxesCount, LocalDateTime createdAt, LocalDate updatedAt) {
}
