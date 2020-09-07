package com.reservationApp.backend.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Document
public class User {
    @Id
    private String id;
    private String name;
    private String username;
    private String email;
    private String password;
    private String authenticationMethod;
    private Set<Role> roles = new HashSet<>();


    public User() {

    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String userName) {
        this.username = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Role> getRoles() {
        return roles;
    }

    public void setRoles(Set<Role> roles) {
        this.roles = roles;
    }

    public String getAuthenticationMethod() {
        return authenticationMethod;
    }

    public void setAuthenticationMethod(String authenticationMethod) {
        this.authenticationMethod = authenticationMethod;
    }
}
