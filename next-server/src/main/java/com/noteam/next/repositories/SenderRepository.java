package com.noteam.next.repositories;

import com.noteam.next.entities.Sender;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SenderRepository extends JpaRepository<Sender, Integer> {
}
