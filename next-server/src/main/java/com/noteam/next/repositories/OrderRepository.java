package com.noteam.next.repositories;

import com.noteam.next.entities.Order;
import com.noteam.next.entities.State;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order,Integer> {
    Page<Order> findAllByState(State state, Pageable pageable);
}
