package com.placeflow.driveservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "drives")
public class Drive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long companyId;   // ID reference only

    private String role;
    private Double packageLpa;

    @Enumerated(EnumType.STRING)
    private DriveStatus status;
}
