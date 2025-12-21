package com.noteam.next.repositories;

import com.noteam.next.entities.City;
import org.springframework.data.jpa.repository.JpaRepository;


public interface CityRepository extends JpaRepository<City, Integer> {
}
