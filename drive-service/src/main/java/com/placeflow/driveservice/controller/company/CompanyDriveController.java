package com.placeflow.driveservice.controller.company;

import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.repository.DriveRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/company/drives")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyDriveController {

    private final DriveRepository driveRepo;

    public CompanyDriveController(DriveRepository driveRepo) {
        this.driveRepo = driveRepo;
    }

    @GetMapping
    public List<PlacementDrive> myDrives(Authentication auth) {
        return driveRepo.findByCompanyAuthEmail(auth.getName());
    }
}

