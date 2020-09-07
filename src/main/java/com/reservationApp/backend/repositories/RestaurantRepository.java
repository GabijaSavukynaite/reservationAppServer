package com.reservationApp.backend.repositories;

import com.reservationApp.backend.models.Restaurant;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantRepository  extends MongoRepository<Restaurant, String> {
}
