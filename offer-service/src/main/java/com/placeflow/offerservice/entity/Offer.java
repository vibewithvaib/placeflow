package com.placeflow.offerservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "offers")
public class Offer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long studentId;
    private Long driveId;

    @Enumerated(EnumType.STRING)
    private OfferStatus status;
}

