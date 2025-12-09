package com.noteam.next.repositories;

import com.noteam.next.entities.Admin;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AdminRepository extends JpaRepository<Admin,Integer > {
}
