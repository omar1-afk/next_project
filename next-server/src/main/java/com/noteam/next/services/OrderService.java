package com.noteam.next.services;

import com.noteam.next.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.logging.Logger;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    private static final Logger log = Logger.getLogger(OrderService.class.getName());
}
