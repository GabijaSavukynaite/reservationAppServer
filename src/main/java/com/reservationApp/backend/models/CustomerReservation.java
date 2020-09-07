package com.reservationApp.backend.models;

import org.springframework.data.annotation.Id;

public class CustomerReservation {
    @Id
    private String id;
    private String restaurantId;
    private String customerId;

    public CustomerReservation() {
    }

    public CustomerReservation(String restaurantId, String customerId) {
        this.restaurantId = restaurantId;
        this.customerId = customerId;
    }

    public String getRestaurantId() {
        return restaurantId;
    }

    public void setRestaurantId(String restaurantId) {
        this.restaurantId = restaurantId;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }
}
