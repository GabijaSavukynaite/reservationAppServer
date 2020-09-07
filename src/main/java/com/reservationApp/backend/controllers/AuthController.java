package com.reservationApp.backend.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.nimbusds.openid.connect.sdk.claims.UserInfo;
import com.reservationApp.backend.exceptions.AppException;
import com.reservationApp.backend.models.Role;
import com.reservationApp.backend.models.RoleName;
import com.reservationApp.backend.models.User;
import com.reservationApp.backend.payloads.ApiResponse;
import com.reservationApp.backend.payloads.JwtAuthenticationResponse;
import com.reservationApp.backend.payloads.LoginRequest;
import com.reservationApp.backend.payloads.SignUpRequest;
import com.reservationApp.backend.repositories.RoleRepository;
import com.reservationApp.backend.repositories.UserRepository;
import com.reservationApp.backend.security.JwtTokenProvider;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.io.IOException;
import java.net.URI;
import java.security.GeneralSecurityException;
import java.util.*;

// https://www.youtube.com/watch?v=GNKxGHVD1Eg
// https://www.devglan.com/spring-security/spring-boot-security-google-oauth
// https://www.youtube.com/watch?v=nnsWiXBVeMI

// https://de.switch-case.com/65760997
// http://blog.davidvassallo.me/2018/03/03/google-yolo-and-spring-boot-2-0-authentication/


// https://www.javatips.net/api/com.google.api.client.googleapis.auth.oauth2.googleidtoken.payload
@RestController
@RequestMapping("/api/auth")
public class AuthController {


    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest) {

        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                    loginRequest.getUsernameOrEmail(),
                    loginRequest.getPassword()
            )
    );

        SecurityContextHolder.getContext().setAuthentication(authentication);


    // String jwt = tokenProvider.generateToken(authentication)
    User user = userRepository.findByEmail(loginRequest.getUsernameOrEmail());
    String jwt = tokenProvider.generateToken(user);



    Map<String, String> userData = new HashMap<>();
        userData.put("token", jwt);
        userData.put("username", user.getUsername());

        return ResponseEntity.ok(userData);
}




    @PostMapping("/tokensignin")
    public ResponseEntity<?> authenticateWithToken(@RequestParam("token") String token) {
        JsonFactory jsonFactory = JacksonFactory.getDefaultInstance();
        HttpTransport transport = new NetHttpTransport();
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(transport, jsonFactory)
                .setAudience(Collections.singletonList(""))
                .build();

        GoogleIdToken idToken = null;
        try {
            idToken = verifier.verify(token);
        } catch (GeneralSecurityException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        System.out.println("token " + idToken);
        if (idToken != null) {
            GoogleIdToken.Payload payload = idToken.getPayload();

            // Print user identifier


            HashMap<String, String> UserInfo = new HashMap<String, String>();
            // Get profile information from payload
            String userId = payload.getSubject();
            UserInfo.put("id", userId);

            // Get profile information from payload
            String email = payload.getEmail();
            UserInfo.put("email", email);

            boolean emailVerified = Boolean.valueOf(payload.getEmailVerified());
            UserInfo.put("emailVerified", String.valueOf(emailVerified));

            String name = (String) payload.get("name");
            UserInfo.put("name", name);

            String family_name = (String) payload.get("family_name");
            UserInfo.put("family_name", name);

            String pictureUrl = (String) payload.get("picture");
            UserInfo.put("pictureUrl", pictureUrl);

            User user;


            if (!userRepository.existsByEmail(email)) {

                Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                        .orElseThrow(() -> new AppException("User Role not set."));

                User _user = new User();
                _user.setName(name);
                _user.setUsername(name);
                _user.setEmail(email);
                _user.setAuthenticationMethod("google");

                _user.setRoles(Collections.singleton(userRole));

                user = userRepository.save(_user);
            }
            else {
                user = userRepository.findByEmail(email);
            }



            String jwt = tokenProvider.generateToken(user);

            Map<String, String> userData = new HashMap<>();
            userData.put("token", jwt);
            userData.put("username", user.getUsername());

            return ResponseEntity.ok(userData);


        } else {
            return ResponseEntity
                    .status(HttpStatus.FORBIDDEN)
                    .body(new ApiResponse(false, "invalid token"));
        }

    }

    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@Valid @RequestBody SignUpRequest signUpRequest) {
        if(userRepository.existsByUsername(signUpRequest.getUsername())) {
            return new ResponseEntity(new ApiResponse(false, "Username is already taken!"),
                    HttpStatus.BAD_REQUEST);
        }

        if(userRepository.existsByEmail(signUpRequest.getEmail())) {
//            return new ResponseEntity(new ApiResponse(false, "Email Address already in use!"),
//                    HttpStatus.BAD_REQUEST);
//            return new ResponseEntity<>(
//                    "Email Address already in use",
//                    HttpStatus.BAD_REQUEST);
            return ResponseEntity.badRequest()
                    .body("Email Address already in use");
        }

        User user = new User();
        user.setName(signUpRequest.getName());
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(passwordEncoder.encode(signUpRequest.getPassword()));
        user.setAuthenticationMethod("emailAndPassword");

        Role userRole = roleRepository.findByName(RoleName.ROLE_USER)
                .orElseThrow(() -> new AppException("User Role not set."));

        user.setRoles(Collections.singleton(userRole));

        User result = userRepository.save(user);

        URI location = ServletUriComponentsBuilder
                .fromCurrentContextPath().path("/api/users/{username}")
                .buildAndExpand(result.getUsername()).toUri();

        return ResponseEntity.ok().body("successfull registration");
    }


}