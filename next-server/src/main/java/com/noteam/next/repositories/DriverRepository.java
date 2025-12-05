package com.noteam.next.repositories;

import com.noteam.next.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver,Integer > {
}
