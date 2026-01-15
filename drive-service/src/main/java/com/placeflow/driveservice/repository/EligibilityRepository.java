package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.EligibilityCriteria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EligibilityRepository extends JpaRepository<EligibilityCriteria, Long> {
    EligibilityCriteria findByDriveId(Long driveId);
}
