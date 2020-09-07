package com.reservationApp.backend.controllers;

import com.reservationApp.backend.models.CustomerReservation;
import com.reservationApp.backend.repositories.CustomerReservationRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/customerReservation")
public class CustomerReservationController {
    private CustomerReservationRepository repository;

    public CustomerReservationController(CustomerReservationRepository repository) {
        this.repository = repository;
    }

    @PostMapping
    public CustomerReservation addCustomerReservation(String restaurantId, String customerId) {
        CustomerReservation customerReservation = new CustomerReservation(restaurantId, customerId);
        repository.save(customerReservation);
        return  customerReservation;
    }

    @GetMapping
    public List<CustomerReservation> getAllCustomerReservations(String customerId) {
        return repository.findByCustomerId(customerId);
    }
}
