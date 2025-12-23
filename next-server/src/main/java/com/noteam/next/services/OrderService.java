package com.noteam.next.services;

import com.noteam.next.dto.OrderRequest;
import com.noteam.next.entities.*;
import com.noteam.next.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private ShipmentRepository shipmentRepository;
    @Autowired
    private CityRepository cityRepository;
    @Autowired
    private SenderRepository senderRepository;
    @Autowired
    private ReceiverRepository receiverRepository;
    private static final Logger log = Logger.getLogger(OrderService.class.getName());

    //Create new order
    public boolean createOrder(OrderRequest request) {
        Optional<City> cityOptional= cityRepository.findById(request.city_id());
        Optional<Sender> senderOptional= senderRepository.findById(request.sender_id());
        Optional<Receiver> receiverOptional= receiverRepository.findById(request.receiver_id());
        if (cityOptional.isEmpty() ) {
            log.severe("City id " + request.city_id() + " does not exist");
        }
        if (senderOptional.isEmpty() ) {
            log.severe("Sender id " + request.sender_id() + " does not exist");
        }
        if (receiverOptional.isEmpty() ) {
            log.severe("Receiver id " + request.receiver_id() + " does not exist");
        }
        int maxWeight = 250;
        if (request.weight() > maxWeight) {
            log.info("Order service: invalid weight (more than " + maxWeight + ")");
            return false;
        } else {
            log.info("Order service: Creating new order...");
            City city = cityOptional.get();
            Sender sender = senderOptional.get();
            Receiver receiver = receiverOptional.get();
            Order newOrder = new Order(city, request.region()
                    , request.address(), request.flameable(), request.breakable()
                    , request.price(), request.state(), request.weight()
                    , null, receiver
                    , sender, request.boxesCount());
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
    public Page<Order> getOrdersByPage(int page, int size, String sortBy, String sortDir,String state){
        Pageable pageable = PageRequest.of(page,size,Sort.by(Sort.Direction.fromString(sortDir),sortBy));
        log.info("Order service: getting orders by page: "+page+" with size "+size+" sorted by "+ sortBy+",("+ sortDir+")");
        if(state.equals("ALL")){
                return orderRepository.findAll(pageable);
        }else if (state.equals(State.PICKED.name()) || state.equals(State.PACKAGING.name())
                || state.equals(State.DELEVERED.name()) || state.equals(State.RETURNED.name()) || state.equals(State.SHIPPING.name())){
                return orderRepository.findAllByState(State.valueOf(state),pageable);
        }else{
            return null;
        }
    }
  public List<Order> getOrdersByState(String sortBy, String sortDir,String state){
    log.info("Order service: getting orders by state sorted by "+ sortBy+",("+ sortDir+")");
    if(state.equals("ALL")){
      log.info("ORDER SERVICE: ALL");
      return orderRepository.findAll();
    }else if (state.equals(State.PICKED.name()) || state.equals(State.PACKAGING.name())
      || state.equals(State.DELEVERED.name()) || state.equals(State.RETURNED.name()) || state.equals(State.SHIPPING.name())){
      log.info("ORDER SERVICE: " + state.toString());
      return orderRepository.findAllByState(State.valueOf(state),Sort.by(Sort.Direction.fromString(sortDir)));
    }else{
      return Collections.emptyList();
    }
  }
    public Optional<Order> getOrderById(int id){
        log.info("Order service: getting order by id: "+ id);
        return orderRepository.findById(id);
    }
    //-------------------------------------------------------------------------------------------------------
    //update order
    public int updateOrderById(int id,OrderRequest request) {
        Optional<City> cityOptional= cityRepository.findById(request.city_id());
        Optional<Sender> senderOptional= senderRepository.findById(request.sender_id());
        Optional<Receiver> receiverOptional= receiverRepository.findById(request.receiver_id());
        Optional <Shipment> shipmentOptional = shipmentRepository.findById(id);
        int maxWeight = 250;
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return -1;
        }
        if(senderOptional.isEmpty()){
            log.info("Order service: The Sender with id: "+id+" is not found!");
        }
        if(receiverOptional.isEmpty()){
            log.info("Order service: The Receiver with id: "+id+" is not found!");
        }
        if(cityOptional.isEmpty()){
            log.info("Order service: The City with id: "+id+" is not found!");
        }
        if (shipmentOptional.isEmpty()){
            log.info("Order service: The Shipment with id: "+id+" is not found!");
        }
        else if (request.weight() > maxWeight) {
            log.info("Order service: Error: Invalid weight (more than " + maxWeight + ")!");
            return 0;
        } else {
            log.info("Order service: Updating the order with id: "+id);
            City city = cityOptional.get();
            Sender sender = senderOptional.get();
            Receiver receiver = receiverOptional.get();
            Order order = orderOptional.get();
            Shipment shipment = shipmentOptional.get();
            order.setCity(city);
            order.setRegion(request.region());
            order.setAddress(request.address());
            order.setFlameable(request.flameable());
            order.setBreakable(request.breakable());
            order.setPrice(request.price());
            order.setState(request.state());
            order.setWeight(request.weight());
            order.setBoxesCount(request.boxesCount());
            order.setReceiver(receiver);
            order.setSender(sender);
            order.setShipment(shipment);

            orderRepository.save(order);
            return 1;
        }
        return 0;
    }
    //----------------------------------------------------------------------------------------------------------
    //delete order
    public boolean deleteOrderById(int id){
        Optional<Order> orderOptional = getOrderById(id);
        if (orderOptional.isEmpty()){
            return false;
        }
        else {
            orderRepository.deleteById(id);
            return true;
        }
    }
    //-------------------------------------------------------------------------------------------------------------
    // attach or deattach shipments
    public boolean attachShipmentById(int id,int shipmentId){
        Optional<Order> orderOptional= getOrderById(id);
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipmentId);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return false;
        }
        if(shipmentOptional.isEmpty()){
            log.info("Order service: The Shipment with id: "+id+" is not found!");
            return false;
        }
        else {
            log.info("Order service: attach shipment to the order with id: "+id);
            Order order =orderOptional.get();
            Shipment shipment = shipmentOptional.get();
            shipment.setTotal_weight(shipment.getTotal_weight()+order.getWeight());
            shipmentRepository.save(shipment);
            order.setShipment(shipment);

            orderRepository.save(order);
            return true;
        }
    }
    public boolean attachShipmentByIds(List<Integer> id, int shipmentId){
        List<Order> orderList= orderRepository.findAllById(id);
        Optional<Shipment> shipmentOptional = shipmentRepository.findById(shipmentId);
        if(orderList.isEmpty()){
            log.info("Order service: The Orders with ids: "+id+" are not found!");
            return false;
        }
        if(shipmentOptional.isEmpty()){
            log.info("Order service: The shipment with id: "+shipmentId+" are not found!");
            return false;}
        else {
            log.info("Order service: attach shipment to the orders with ids: "+id);

            Shipment shipment=shipmentOptional.get();
            for (Order order : orderList){
                order.setShipment(shipment);
            }
            orderRepository.saveAll(orderList);
            return true;
        }
    }
    public boolean deattachShipmentById(int id) {
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return false;
        }
        else {
            log.info("Order service: deattach shipment to the orders with id: "+id);
            Order order =orderOptional.get();
            Shipment shipment = order.getShipment();
            shipment.setTotal_weight((shipment.getTotal_weight()-order.getWeight()));
            shipmentRepository.save(shipment);
            order.setShipment(null);
            orderRepository.save(order);
            return true;
        }
    }
    public boolean deattachShipmentByIds(List<Integer> id){
        List<Order> orderList= orderRepository.findAllById(id);
        if(orderList.isEmpty()){
            log.info("Order service: The Orders with ids: "+id+" are not found!");
            return false;
        }
        else {
            log.info("Order service: deattach shipment to the orders with ids: "+id);
            for (Order order : orderList){
                order.setShipment(null);
            }
            orderRepository.saveAll(orderList);
            return true;
        }
    }
    //----------------------------------------------------------------------------------------------
    // attach or deattach senders
    public boolean attachSenderById(int id, Sender sender){
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return false;
        }
        else {
            log.info("Order service: attach sender to the order with id: "+id);
            Order order =orderOptional.get();
            order.setSender(sender);
            orderRepository.save(order);
            return true;
        }
    }
    public boolean attachSenderByIds(List<Integer> id, Sender sender){
        List<Order> orderList= orderRepository.findAllById(id);
        if(orderList.isEmpty()){
            log.info("Order service: The Orders with ids: "+id+" are not found!");
            return false;
        }
        else {
            log.info("Order service: attach sender to the orders with ids: "+id);
            for (Order order : orderList){
                order.setSender(sender);
            }
            orderRepository.saveAll(orderList);
            return true;
        }
    }
    public boolean deattachSenderById(int id) {
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return false;
        }
        else {
            log.info("Order service: deattach sender to the orders with id "+id);
            Order order =orderOptional.get();
            order.setSender(null);
            orderRepository.save(order);
            return true;
        }
    }
    public boolean deattachSenderByIds(List<Integer> id){
        List<Order> orderList= orderRepository.findAllById(id);
        if(orderList.isEmpty()){
            log.info("Order service: The Orders with ids: "+id+" are not found!");
            return false;
        }
        else {
            log.info("Order service: deattach sender to the orders with ids: "+id);
            for (Order order : orderList){
                order.setSender(null);
            }
            orderRepository.saveAll(orderList);
            return true;
        }
    }
    //----------------------------------------------------------------------------------------------
    // attach or deattach receivers
    public boolean attachReceiverById(int id, Receiver receiver){
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return false;
        }
        else {
            log.info("Order service: attach receiver to the order with id: "+id);
            Order order =orderOptional.get();
            order.setReceiver(receiver);
            orderRepository.save(order);
            return true;
        }
    }
    public boolean attachReceiverByIds(List<Integer> id, Receiver receiver){
        List<Order> orderList= orderRepository.findAllById(id);
        if(orderList.isEmpty()){
            log.info("Order service: The Orders with ids: "+id+" are not found!");
            return false;
        }
        else {
            log.info("Order service: attach receiver to the orders with ids: "+id);
            for (Order order : orderList){
                order.setReceiver(receiver);
            }
            orderRepository.saveAll(orderList);
            return true;
        }
    }
    public boolean deattachReceiverById(int id) {
        Optional<Order> orderOptional= getOrderById(id);
        if(orderOptional.isEmpty()){
            log.info("Order service: The Order with id: "+id+" is not found!");
            return false;
        }
        else {
            log.info("Order service: Updating the order with id: "+id);
            Order order =orderOptional.get();
            order.setReceiver(null);
            orderRepository.save(order);
            return true;
        }
    }
    public boolean deattachReceiverByIds(List<Integer> id){
        List<Order> orderList= orderRepository.findAllById(id);
        if(orderList.isEmpty()){
            log.info("Order service: The Orders with ids: "+id+" are not found!");
            return false;
        }
        else {
            log.info("Order service: deattach receiver to the orders with ids: "+id);
            for (Order order : orderList){
                order.setReceiver(null);
            }
            orderRepository.saveAll(orderList);
            return true;
        }
    }
    //----------------------------------------------------------------------------------------------

//  Nesma test file
   // public void assignOrDeleteShipment(List<Integer> orders, Shipment shipment){
     //   System.out.println("assigning orders to shipment ");
   // }
}
