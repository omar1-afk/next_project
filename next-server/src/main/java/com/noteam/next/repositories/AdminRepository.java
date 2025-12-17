package com.noteam.next.repositories;

import com.noteam.next.entities.Admin;

import com.noteam.next.entities.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AdminRepository extends JpaRepository<Admin,Integer > {
    Optional<Admin> findByEmail(String email);
}
