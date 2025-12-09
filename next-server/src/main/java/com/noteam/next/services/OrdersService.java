package com.noteam.next.services;

import com.noteam.next.entities.Shipment;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrdersService {

    public void assignOrDeleteShipment(List<Integer> orders, Shipment shipment){
        System.out.println("assigning orders to shipment ");
    }
}
