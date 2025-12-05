package com.noteam.next.services;

import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OrdersService {

    public void AssignOrDeleteShipment(List<Integer> orders, int shipment_id){
        System.out.println("assigning orders to shipment "+shipment_id);
    }
}
