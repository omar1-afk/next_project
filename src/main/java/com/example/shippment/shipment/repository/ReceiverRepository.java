package com.example.shippment.shipment.repository;

import com.example.shippment.shipment.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver, Integer> {
}