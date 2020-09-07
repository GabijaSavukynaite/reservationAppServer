package com.reservationApp.backend;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

// https://geowarin.com/social-login-with-spring/
// https://blog.jdriven.com/2014/10/stateless-spring-security-part-2-stateless-authentication/
// https://o7planning.org/en/11823/social-login-with-oauth2-in-spring-boot#a17243997


// http://sivatechlab.com/secure-rest-api-using-spring-security-oauth2/

@SpringBootApplication
public class BackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

}
