package com.reservationApp.backend.models;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

@Document
public class Reservation {
    @Id
    private String id;
    private String date;
    private int numOfPeople;
    private String restaurantId;
    private String customerId;
    private String time;

    public Reservation() {

    }

    public Reservation(String date, int numOfPeople, String restaurantId, String customerId, String time){
        this.date = date;
        this.numOfPeople = numOfPeople;
        this.restaurantId = restaurantId;
        this.customerId = customerId;
        this.time = time;
    }

    public String getId() {
        return id;
    }

    public String getDate() {
        return date;
    }

    public int getNumOfPeople() {
        return numOfPeople;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setNumOfPeople(int numOfPeople) {
        this.numOfPeople = numOfPeople;
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

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
