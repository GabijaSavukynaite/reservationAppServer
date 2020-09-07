package com.reservationApp.backend.controllers;

import com.reservationApp.backend.models.Restaurant;
import com.reservationApp.backend.repositories.RestaurantRepository;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileInputStream;
import java.util.Base64;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/restaurant")
public class RestaurantController {

    private RestaurantRepository repository;

    public RestaurantController(RestaurantRepository repository) {
        this.repository = repository;
    }

    @GetMapping
    public List<Restaurant> getRestaurantList() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Optional<Restaurant> getRestaurantByID(@PathVariable("id") String id) {return repository.findById(id); }

    @PostMapping
    public Restaurant addNewRestaurant() {
        byte[] imageBytes = null;
        try {
            imageBytes = loadImage("C:\\Users\\Gabija\\Pictures\\g3.jpg");

        } catch (Exception e) {
            e.printStackTrace();
        }

        String encodedString = Base64.getEncoder().encodeToString(imageBytes);
        Restaurant restaurant = new Restaurant("testas", "adresas",encodedString );
        repository.save(restaurant);
        return restaurant;
    }

    public static byte[] loadImage(String filePath) throws Exception {
        File file = new File(filePath);
        int size = (int)file.length();
        byte[] buffer = new byte[size];
        FileInputStream in = new FileInputStream(file);
        in.read(buffer);
        in.close();
        return buffer;
    }

}
