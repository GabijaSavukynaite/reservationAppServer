package com.reservationApp.backend.repositories;

import org.springframework.data.mongodb.repository.MongoRepository;
import com.reservationApp.backend.models.CustomerReservation;

import java.util.List;

public interface CustomerReservationRepository extends MongoRepository<CustomerReservation, String> {

    List<CustomerReservation> findByCustomerId(String customerId);
}
