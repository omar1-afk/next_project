package com.noteam.next.repositories;

import com.noteam.next.entities.Sender;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SenderRepository extends JpaRepository<Sender, Integer> {
  List<Sender> findAllByEmail(String email);
}
