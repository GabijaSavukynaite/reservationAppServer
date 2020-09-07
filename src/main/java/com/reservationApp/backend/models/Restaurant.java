package com.reservationApp.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
// https://stackoverflow.com/questions/4245787/how-to-insert-images-in-mongodb-using-java
@Document
public class Restaurant {
    @Id
    private String id;
    private String name;
    private String address;
    private String image;


    public Restaurant() {
    }

    public Restaurant(String name, String address, String image) {
        this.name = name;
        this.address = address;
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
