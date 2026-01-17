package com.placeflow.driveservice.controller.tpo;

import com.placeflow.driveservice.dto.CreateDriveRequest;
import com.placeflow.driveservice.entity.Company;
import com.placeflow.driveservice.entity.DriveStatus;
import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.repository.CompanyRepository;
import com.placeflow.driveservice.repository.DriveRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/tpo/drives")
@PreAuthorize("hasRole('TPO')")
public class DriveController {

    private final DriveRepository driveRepo;
    private final CompanyRepository companyRepo;

    public DriveController(
            DriveRepository driveRepo,
            CompanyRepository companyRepo
    ) {
        this.driveRepo = driveRepo;
        this.companyRepo = companyRepo;
    }

    @PostMapping
    public PlacementDrive create(@RequestBody CreateDriveRequest req) {

        Company company = companyRepo.findById(req.getCompanyId())
                .orElseThrow(() -> new RuntimeException("Company not found"));

        PlacementDrive drive = new PlacementDrive();
        drive.setCompany(company);
        drive.setTitle(req.getTitle());
        drive.setJobRole(req.getJobRole());
        drive.setCtc(req.getCtc());
        drive.setDescription(req.getDescription());
        drive.setApplicationDeadline(req.getApplicationDeadline());
        drive.setStatus(DriveStatus.DRAFT);

        return driveRepo.save(drive);
    }

    @PostMapping("/{id}/open")
    public void open(@PathVariable Long id) {
        PlacementDrive drive = driveRepo.findById(id)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        if (drive.getApplicationDeadline().isBefore(LocalDate.now()))
            throw new RuntimeException("Deadline already passed");

        drive.setStatus(DriveStatus.OPEN);
        driveRepo.save(drive);
    }
}

