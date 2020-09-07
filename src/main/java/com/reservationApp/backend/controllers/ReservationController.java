package com.reservationApp.backend.controllers;

import com.reservationApp.backend.models.Reservation;
import com.reservationApp.backend.repositories.ReservationRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private ReservationRepository repository;

    public ReservationController(ReservationRepository repository) {
        this.repository = repository;
    }

    @GetMapping("/{restaurantId}/{date}/{numOfPeople}")
    public List<Reservation> getReservations(@PathVariable("restaurantId") String restaurantId,
                                             @PathVariable("date") String date,
                                             @PathVariable("numOfPeople") String numOfPeople) {
        List<Reservation> reservationList = repository.findByRestaurantIdAndDateAndNumOfPeople(restaurantId,
                date, Integer.parseInt(numOfPeople));
//        for (Reservation r:
//                reservationList) {
//            if(r.getCustomerId() == "")
//            {
//                reservationList.remove(r);
//            }
//        }
        return reservationList;
    }

    @PutMapping("/updateCustomer")
    public ResponseEntity<String> updateCustomer(@Valid  @RequestBody Reservation reservation) {
        repository.save(reservation);
        return ResponseEntity.ok().body("customer successfully added to reservation");
    }

//   @PostMapping
//    public Reservation addNewRerservation(@RequestBody Reservation reservation) {
//       reservationRepository.save(reservation);
//       return reservation;
//   }
//
//   @DeleteMapping("/{id}")
//    public void deleteReservation(@PathVariable("id") String id) {
//        reservationRepository.deleteById(id);
//   }
//
//   @PutMapping("/{id}")
//    public void updateRservation(@PathVariable("id") String id, @RequestBody Reservation reservation) {
//        reservation.setId(id);
//        reservationRepository.save(reservation);
//   }
}
