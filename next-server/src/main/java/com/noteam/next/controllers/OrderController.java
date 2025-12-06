package com.noteam.next.controllers;

import com.noteam.next.dto.OrderRequest;
import com.noteam.next.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    OrderService orderService;
    private static final Logger log = LoggerFactory.getLogger(OrderController.class);

    @PostMapping
    ResponseEntity<String> createOrder(@RequestBody OrderRequest request){
        log.info("Order controller: Creating new order");
        if(orderService.createOrder(request)){
            return ResponseEntity.ok("The order is created successfully!");
        }
        else {
            return ResponseEntity.badRequest().body("The weight is more than 250 KG!");
        }
    }
}
