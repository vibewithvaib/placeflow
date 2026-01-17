package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OfferRepository
        extends JpaRepository<Offer, Long> {
    Optional<Offer> findByApplicationId(Long appId);
}

