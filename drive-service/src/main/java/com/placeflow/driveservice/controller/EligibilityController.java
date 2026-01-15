package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.entity.EligibilityCriteria;
import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.repository.DriveRepository;
import com.placeflow.driveservice.repository.EligibilityRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/eligibility")
@PreAuthorize("hasRole('TPO')")
public class EligibilityController {

    private final EligibilityRepository eligibilityRepository;
    private final DriveRepository driveRepository;

    public EligibilityController(
            EligibilityRepository eligibilityRepository,
            DriveRepository driveRepository
    ) {
        this.eligibilityRepository = eligibilityRepository;
        this.driveRepository = driveRepository;
    }

    @PostMapping("/{driveId}")
    public EligibilityCriteria createEligibility(
            @PathVariable Long driveId,
            @RequestBody EligibilityCriteria criteria
    ) {
        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        criteria.setDrive(drive);
        return eligibilityRepository.save(criteria);
    }
}
