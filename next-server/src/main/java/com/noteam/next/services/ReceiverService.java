package com.noteam.next.services;
import com.noteam.next.entities.Receiver;
import com.noteam.next.repositories.ReceiverRepository;
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
    public Receiver save (Receiver receiver) {
        receiver.setUpdatedAt(LocalDateTime.now()); // Update timestamp
        return receiverRepository.save(receiver);
    }
    // retrieve all
    public List<Receiver> findAll() {
        return receiverRepository.findAll();
    }
    // retrieve by
    public Optional<Receiver> findById(int id) {
        return receiverRepository.findById(id);
    }
    // delete
    public void deleteById(int id) {
        receiverRepository.deleteById(id);
    }
}
