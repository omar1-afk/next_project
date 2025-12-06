package com.example.shippment.shipment.repository;

import com.example.shippment.shipment.model.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
// ACTION: Extend JpaRepository. Arguments are: <Model Class, Primary Key Type>
public interface ReceiverRepository extends JpaRepository<Receiver, Integer> {
}