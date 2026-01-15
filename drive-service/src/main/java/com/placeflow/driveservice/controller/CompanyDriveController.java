package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.entity.StudentApplication;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.DriveRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyDriveController {

    private final DriveRepository driveRepository;
    private final ApplicationRepository applicationRepository;

    public CompanyDriveController(
            DriveRepository driveRepository,
            ApplicationRepository applicationRepository
    ) {
        this.driveRepository = driveRepository;
        this.applicationRepository = applicationRepository;
    }

    /**
     * COMPANY views applicants for its own drive
     */
    @GetMapping("/drives/{driveId}/applications")
    public List<StudentApplication> viewApplicants(
            @PathVariable Long driveId,
            Authentication authentication
    ) {
        String companyEmail = authentication.getName();

        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        // 🔐 STRICT OWNERSHIP CHECK
        if (!drive.getCompany().getCompanyAuthEmail().equals(companyEmail)) {
            throw new RuntimeException("Unauthorized access to this drive");
        }

        return applicationRepository.findByDriveId(driveId);
    }
}

