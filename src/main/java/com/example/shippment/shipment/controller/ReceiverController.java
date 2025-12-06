package com.example.shippment.shipment.controller;

import com.example.shippment.shipment.model.Receiver;
import com.example.shippment.shipment.service.ReceiverService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/receivers")
public class ReceiverController {

    private final ReceiverService receiverService;

    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    //create
    @PostMapping
    public ResponseEntity<Receiver> createReceiver(@RequestBody Receiver receiver) {
        Receiver createdReceiver = receiverService.save(receiver);
        return new ResponseEntity<>(createdReceiver, HttpStatus.CREATED); // 201 Created
    }

    // retrieve all
    @GetMapping
    public ResponseEntity<List<Receiver>> getAllReceivers() {
        List<Receiver> receivers = receiverService.findAll();
        return new ResponseEntity<>(receivers, HttpStatus.OK); // 200 OK
    }

    // retrieve by
    @GetMapping("/{id}")
    public ResponseEntity<Receiver> getReceiverById(@PathVariable int id) {
        // Optional handles the case where the ID is not found
        Optional<Receiver> receiver = receiverService.findById(id);

        return receiver.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Receiver> updateReceiver(@PathVariable int id, @RequestBody Receiver receiverDetails) {
        Optional<Receiver> existingReceiver = receiverService.findById(id);

        if (existingReceiver.isPresent()) {
            Receiver receiverToUpdate = existingReceiver.get();
            receiverToUpdate.setName(receiverDetails.getName());
            receiverToUpdate.setSocialSecurityNumber(receiverDetails.getSocialSecurityNumber());
            receiverToUpdate.setPhone(receiverDetails.getPhone());
            receiverToUpdate.setEmail(receiverDetails.getEmail());
            Receiver updatedReceiver = receiverService.save(receiverToUpdate);
            return new ResponseEntity<>(updatedReceiver, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteReceiver(@PathVariable int id) {
        try {
            receiverService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Success
        } catch (Exception e) {
            // In a real app, you might check if the entity existed before deleting
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}