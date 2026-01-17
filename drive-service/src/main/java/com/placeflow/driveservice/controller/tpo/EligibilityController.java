package com.placeflow.driveservice.controller.tpo;

import com.placeflow.driveservice.entity.EligibilityCriteria;
import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.repository.DriveRepository;
import com.placeflow.driveservice.repository.EligibilityRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tpo/eligibility")
@PreAuthorize("hasRole('TPO')")
public class EligibilityController {

    private final EligibilityRepository repo;
    private final DriveRepository driveRepo;

    public EligibilityController(
            EligibilityRepository repo,
            DriveRepository driveRepo
    ) {
        this.repo = repo;
        this.driveRepo = driveRepo;
    }

    @PostMapping("/{driveId}")
    public EligibilityCriteria create(
            @PathVariable Long driveId,
            @RequestBody EligibilityCriteria criteria
    ) {
        PlacementDrive drive = driveRepo.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        criteria.setDrive(drive);
        return repo.save(criteria);
    }
}

