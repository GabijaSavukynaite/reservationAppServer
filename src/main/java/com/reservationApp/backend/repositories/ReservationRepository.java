package com.reservationApp.backend.repositories;

import com.reservationApp.backend.models.Reservation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends MongoRepository<Reservation, String> {

    List<Reservation> findByRestaurantIdAndDateAndNumOfPeople(String restaurantId,
                                                              String date, int numOfPeople);

}
