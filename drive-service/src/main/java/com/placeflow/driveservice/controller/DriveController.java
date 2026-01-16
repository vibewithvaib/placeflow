package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.DTO.CreateDriveRequest;
import com.placeflow.driveservice.entity.Company;
import com.placeflow.driveservice.entity.DriveStatus;
import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.repository.CompanyRepository;
import com.placeflow.driveservice.repository.DriveRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/drives")
@PreAuthorize("hasRole('TPO')")
public class DriveController {

    private final DriveRepository driveRepository;
    private final CompanyRepository companyRepository;

    public DriveController(
            DriveRepository driveRepository,
            CompanyRepository companyRepository
    ) {
        this.driveRepository = driveRepository;
        this.companyRepository = companyRepository;
    }

    @PostMapping
    public PlacementDrive createDrive(
            @RequestBody CreateDriveRequest request
    ) {
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        PlacementDrive drive = new PlacementDrive();
        drive.setCompany(company);
        drive.setTitle(request.getTitle());
        drive.setJobRole(request.getJobRole());
        drive.setCtc(request.getCtc());
        drive.setDescription(request.getDescription());
        drive.setApplicationDeadline(request.getApplicationDeadline());
        drive.setStatus(DriveStatus.DRAFT);

        return driveRepository.save(drive);
    }

    @PostMapping("/{driveId}/open")
    public void openDrive(@PathVariable Long driveId) {
        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        drive.setStatus(DriveStatus.OPEN);
        driveRepository.save(drive);
    }
}
