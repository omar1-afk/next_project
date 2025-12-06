package com.example.shippment.shipment.service;

import com.example.shippment.shipment.model.Receiver;
import com.example.shippment.shipment.repository.ReceiverRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional; // Used for findById

@Service
public class ReceiverService {
    private final ReceiverRepository receiverRepository;

    // Dependency Injection
    public ReceiverService(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }

    // create & update
    // JpaRepository's save() method handles both insert and update
    public Receiver save(Receiver receiver) {
        receiver.setUpdatedAt(LocalDateTime.now()); // Update timestamp
        return receiverRepository.save(receiver);
    }

    // retrieve all
    public List<Receiver> findAll() {
        return receiverRepository.findAll();
    }

    // retrieve by
    // JpaRepository returns an Optional, which prevents NullPointerExceptions
    public Optional<Receiver> findById(int id) {
        return receiverRepository.findById(id);
    }

    // delete
    public void deleteById(int id) {
        receiverRepository.deleteById(id);
    }
}