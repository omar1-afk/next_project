package com.noteam.next.controllers;

import com.noteam.next.dto.OrderRequest;
import com.noteam.next.entities.Order;
import com.noteam.next.entities.Shipment;
import com.noteam.next.services.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/order")
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

    @GetMapping("/all")
    ResponseEntity<List<Order>> getAllOrders(
            @RequestParam(defaultValue ="createdAt" )String sortBy,
            @RequestParam(defaultValue = "DESC")String sortDir){
        List<Order> orderList = orderService.getAllOrders(sortBy,sortDir);
        if(orderList.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            log.info("Order controller: getting all orders sorted by "+ sortBy+",("+ sortDir+")");
            return ResponseEntity.ok(orderList);
        }
    }
    @GetMapping
    ResponseEntity<Page<Order>> getOrdersByPage(
            @RequestParam(defaultValue = "0") int page
            ,@RequestParam(defaultValue = "10") int size
            , @RequestParam(defaultValue ="createdAt" )String sortBy
            ,@RequestParam(defaultValue = "DESC")String sortDir
            ,@RequestParam(defaultValue = "ALL")String state){
        Page<Order> orderPage=orderService.getOrdersByPage(page, size, sortBy, sortDir,state);
        if (orderPage.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            log.info("Order controller: getting orders by page: "+page+" with size "+size+" sorted by "+ sortBy+",("+ sortDir+")");
            return ResponseEntity.ok(orderPage);
        }
    }
    @GetMapping("/{id}")
    ResponseEntity<Order> getOrderById(@PathVariable("id") int id){
        Optional<Order> orderOptional = orderService.getOrderById(id);
        if(orderOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            log.info("Order controller: getting order by id: "+ id);
            return ResponseEntity.ok(orderOptional.get());
        }
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateOrderById(@PathVariable("id") int id,
                                           @RequestBody OrderRequest request){
        log.info("Order controller: Updating order by id: "+ id);
        int result = orderService.updateOrderById(id, request);
        if(result==-1){
            return ResponseEntity.notFound().build();
        }
        else if(result==0){
            return ResponseEntity.badRequest().body("The weight is more than 250 KG!");
        }
        else {
            return ResponseEntity.ok("The order with id: " + id + " is updated successfully!");
        }
    }
    @PatchMapping("/{id}")
    ResponseEntity<String> attachOrDeattachShipmentById(@PathVariable("id") int id, @RequestBody Shipment shipment){
        Optional<Order> orderOptional= orderService.getOrderById(id);
        if(orderOptional.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        else {
            if(shipment.getShipment_id()!=null){
                if(orderService.attachShipmentById(id,shipment)) {
                    return ResponseEntity.ok("Shipment is attached to order successfully!");
                }
                else {
                    return ResponseEntity.notFound().build();
                }
            }
            else {
                if(orderService.deattachShipmentById(id)) {
                    return ResponseEntity.ok("Shipment is deattached to order successfully!");
                }
                else {
                    return ResponseEntity.notFound().build();
                }
            }
        }
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteOrderById(@PathVariable("id") int id){
        log.info("Order controller: Deleting order by id: "+ id);
        if(orderService.deleteOrderById(id)){
            return ResponseEntity.ok("The order with id: " + id + " is deleted successfully!");
        }
        else{
            return ResponseEntity.notFound().build();
        }
    }
}
