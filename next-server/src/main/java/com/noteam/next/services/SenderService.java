package com.noteam.next.services;
import com.noteam.next.entities.Sender;
import com.noteam.next.repositories.SenderRepository;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class SenderService {
    private final SenderRepository senderRepository;

    // Dependency Injection
    public SenderService(SenderRepository senderRepository) {
        this.senderRepository = senderRepository;
    }

    // create & update
    public Sender save(Sender sender) {
        sender.setUpdatedAt(LocalDateTime.now()); // Update timestamp
        return senderRepository.save(sender);
    }
    // retrieve all
    public List<Sender> findAll() {
        return senderRepository.findAll();
    }
    // retrieve by
    public Optional<Sender> findById(int id) {
        return senderRepository.findById(id);
    }
    // delete
    public void deleteById(int id) {
        senderRepository.deleteById(id);
    }
}
