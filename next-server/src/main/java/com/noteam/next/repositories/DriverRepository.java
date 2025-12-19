package com.noteam.next.repositories;

import com.noteam.next.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface DriverRepository extends JpaRepository<Driver, Integer> {
  Optional<Driver> findByEmail(String email);

  Optional<Driver> findById(Integer id);
}
