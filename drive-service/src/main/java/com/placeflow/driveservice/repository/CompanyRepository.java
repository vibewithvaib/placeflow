package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CompanyRepository
        extends JpaRepository<Company, Long> {
    Optional<Company> findByAuthEmail(String authEmail);
}

