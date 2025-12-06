package com.noteam.next.services;

import com.noteam.next.dto.OrderRequest;
import com.noteam.next.entities.Order;
import com.noteam.next.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
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

    //Get orders
    public List<Order> getAllOrders(String sortBy, String sortDir){
        log.info("Order service: getting all orders sorted by "+ sortBy+",("+ sortDir+")");
        return orderRepository.findAll(Sort.by(Sort.Direction.fromString(sortDir),sortBy));
    }
    public Page<Order> getOrdersByPage(int page, int size, String sortBy, String sortDir){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(sortDir),sortBy));
        log.info("Order service: getting orders by page: "+page+" with size "+size+" sorted by "+ sortBy+",("+ sortDir+")");
        return orderRepository.findAll(pageable);
    }
    public Optional<Order> getOrderById(int id){
        log.info("Order service: getting order by id: "+ id);
        return orderRepository.findById(id);
    }
    //-------------------------------------------------------------------------------------------------------
    //update order
    public int updateOrderById(int id,OrderRequest request) {
        int maxWeight = 250;
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return -1;
        }
        else if (request.weight() > maxWeight) {
            log.info("Order service: Error: Invalid weight (more than " + maxWeight + ")!");
            return 0;
        } else {
            log.info("Order service: Updating the order with id: "+id);
            Order order = orderOptional.get();
            order.setCountry(request.country());
            order.setCity(request.city());
            order.setRegion(request.region());
            order.setAddress(request.address());
            order.setFlameable(request.flameable());
            order.setBreakable(request.breakable());
            order.setPrice(request.price());
            order.setState(request.state());
            order.setWeight(request.weight());
            order.setShippingDate(request.shippingDate());
            order.setBoxesCount(request.boxesCount());
            order.setReceiver(request.receiver());
            order.setSender(request.sender());
            order.setShipment(request.shipment());

            orderRepository.save(order);
            return 1;
        }
    }
    //----------------------------------------------------------------------------------------------------------
}
