package com.noteam.next.dto;

import com.noteam.next.entities.*;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record OrderRequest(int id,  String region
        , String address, boolean flameable, boolean breakable
        , int price, State state, int weight
        , int boxesCount, LocalDateTime createdAt, LocalDate updatedAt
        ,int senderId, int receiverId, int shipmentId,int cityId) {
}
