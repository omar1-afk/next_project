package com.noteam.next.controllers;

import com.noteam.next.entities.Sender;
import com.noteam.next.services.SenderService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/sender")
public class SenderController {

    private final SenderService senderService;

    public SenderController (SenderService senderService) {
        this.senderService = senderService;
    }

    //create
    @PostMapping
    public ResponseEntity<Sender> createSender(@RequestBody Sender sender) {
        Sender createdSender = senderService.save(sender);
        return new ResponseEntity<>(createdSender, HttpStatus.CREATED); // 201 Created
    }

    // retrieve all
    @GetMapping
    public ResponseEntity<List<Sender>> getAllSenders() {
        List<Sender> senders = senderService.findAll();
        return new ResponseEntity<>(senders, HttpStatus.OK); // 200 OK
    }

    // retrieve by
    @GetMapping("/{id}")
    public ResponseEntity<Sender> getSenderById(@PathVariable int id) {
        // Optional handles the case where the ID is not found
        Optional<Sender> sender = senderService.findById(id);

        return sender.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND)); // 404
    }

    // update
    @PutMapping("/{id}")
    public ResponseEntity<Sender> updateSender(@PathVariable int id, @RequestBody Sender senderDetails) {
        Optional<Sender> existingSender = senderService.findById(id);

        if (existingSender.isPresent()) {
            Sender senderToUpdate = existingSender.get();
            senderToUpdate.setName(senderDetails.getName());
            senderToUpdate.setSocialSecurityNumber(senderDetails.getSocialSecurityNumber());
            senderToUpdate.setPhone(senderDetails.getPhone());
            senderToUpdate.setEmail(senderDetails.getEmail());
            Sender updatedSender = senderService.save(senderToUpdate);
            return new ResponseEntity<>(updatedSender, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404
        }
    }

    // delete
    @DeleteMapping("/{id}")
    public ResponseEntity<HttpStatus> deleteSender(@PathVariable int id) {
        try {
            senderService.deleteById(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 Success
        } catch (Exception e) {
            // In a real app, you might check if the entity existed before deleting
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}