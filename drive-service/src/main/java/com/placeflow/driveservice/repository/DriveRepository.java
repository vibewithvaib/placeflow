package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DriveRepository
        extends JpaRepository<PlacementDrive, Long> {
    List<PlacementDrive> findByCompanyAuthEmail(String companyAuthEmail);
}

