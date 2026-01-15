package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.PlacementDrive;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriveRepository extends JpaRepository<PlacementDrive, Long> {
}
