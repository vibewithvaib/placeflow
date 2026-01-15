package com.placeflow.driveservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @Column(length = 1000)
    private String description;

    private String industry;
    private String location;
    private String website;

    /**
     * 🔐 AUTH OWNERSHIP FIELD
     * Email of COMPANY user from auth-service
     */
    @Column(nullable = false, unique = true)
    private String companyAuthEmail;
}
