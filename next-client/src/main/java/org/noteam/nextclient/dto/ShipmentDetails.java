package org.noteam.nextclient.dto;

import java.util.ArrayList;

public record ShipmentDetails(
    int id,
    ArrayList<Order> orders) {
}
