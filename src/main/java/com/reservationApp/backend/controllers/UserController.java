package com.reservationApp.backend.controllers;

import com.reservationApp.backend.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/user")
public class UserController {
    @Autowired
    JwtTokenProvider tokenProvider;

        @GetMapping
        public ResponseEntity<String> getUserId(@RequestHeader("Authorization") String token) {
            return  ResponseEntity.ok().body(tokenProvider.getUserIdFromJWT( token.substring(6)));
        }

}
