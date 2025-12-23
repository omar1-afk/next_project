package com.noteam.next.repositories;

import com.noteam.next.entities.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ReceiverRepository extends JpaRepository<Receiver, Integer> {

  List<Receiver> findAllByEmail(String email);

}
