package com.noteam.next.services;

import com.noteam.next.dto.OrderRequest;
import com.noteam.next.entities.Order;
import com.noteam.next.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    private static final Logger log = Logger.getLogger(OrderService.class.getName());

    //Create new order
    public boolean createOrder(OrderRequest request) {
        int maxWeight = 250;
        if (request.weight() > maxWeight) {
            log.info("Order service: invalid weight (more than " + maxWeight + ")");
            return false;
        } else {
            log.info("Order service: Creating new order...");
            Order newOrder = new Order(request.country(), request.city(), request.region()
                    , request.address(), request.flameable(), request.breakable()
                    , request.price(), request.state(), request.weight()
                    , request.shippingDate(), null, request.receiver()
                    , request.sender(), request.boxesCount());
            orderRepository.save(newOrder);
            return true;
        }
    }
    //--------------------------------------------------------------------------------------------------------

}
