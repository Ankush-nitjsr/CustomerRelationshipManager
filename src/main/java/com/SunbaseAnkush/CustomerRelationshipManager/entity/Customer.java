package com.SunbaseAnkush.CustomerRelationshipManager.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Data
//@NoArgsConstructor
@AllArgsConstructor
@Table(name = "customer")
public class Customer {
    @Id
    @Column(name = "uuid", updatable = false, nullable = false)
    private String uuid;//Initially uuid was set as actual UUID value, later converted to String as per output
    @Column(nullable = false, length = 100)
    private String first_name;
    @Column(nullable = false, length = 100)
    private String last_name;
    @Column(nullable = false, length = 100)
    private String street;
    @Column(nullable = false, length = 100)
    private String address;
    @Column(nullable = false, length = 20)
    private String city;
    @Column(nullable = false, length = 20)
    private String state;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String phone;

    public Customer() {
        // Generate a random ID using UUID
        this.uuid = generateRandomId();
        this.first_name = null;
        this.last_name = null;
        this.street = null;
        this.address = null;
        this.city = null;
        this.state = null;
        this.email = null;
        this.phone = null;
    }

    private String generateRandomId() {
        // Logic to generate a random ID
        return "ID_" + System.currentTimeMillis();
    }
}
