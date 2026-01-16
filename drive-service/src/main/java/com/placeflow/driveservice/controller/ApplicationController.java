package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.entity.*;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.DriveRepository;
import com.placeflow.driveservice.repository.EligibilityRepository;
import com.placeflow.driveservice.service.EligibilityEvaluator;
import com.placeflow.driveservice.service.InterviewWorkflowService;
import com.placeflow.driveservice.service.StudentProfile;
import com.placeflow.driveservice.service.StudentProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/applications")
@PreAuthorize("hasRole('STUDENT')")
public class ApplicationController {

    private final ApplicationRepository applicationRepo;
    private final DriveRepository driveRepo;
    private final EligibilityRepository eligibilityRepo;
    private final StudentProfileService profileService;
    private final EligibilityEvaluator evaluator;
    private final InterviewWorkflowService interviewService;

    public ApplicationController(
            ApplicationRepository applicationRepo,
            DriveRepository driveRepo,
            EligibilityRepository eligibilityRepo,
            StudentProfileService profileService,
            EligibilityEvaluator evaluator,
            InterviewWorkflowService interviewService
    ) {
        this.applicationRepo = applicationRepo;
        this.driveRepo = driveRepo;
        this.eligibilityRepo = eligibilityRepo;
        this.profileService = profileService;
        this.evaluator = evaluator;
        this.interviewService = interviewService;
    }

    @PostMapping("/{driveId}")
    public StudentApplication apply(
            @PathVariable Long driveId,
            Authentication auth
    ) {
        String email = auth.getName();

        PlacementDrive drive = driveRepo.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        if (drive.getStatus() != DriveStatus.OPEN) {
            throw new RuntimeException("Drive not open");
        }

        applicationRepo.findByStudentEmailAndDriveId(email, driveId)
                .ifPresent(a -> {
                    throw new RuntimeException("Already applied");
                });

        EligibilityCriteria criteria =
                eligibilityRepo.findByDriveId(driveId);

        StudentProfile student =
                profileService.getProfile(email);

        boolean eligible =
                evaluator.isEligible(student, criteria);

        StudentApplication app = new StudentApplication();
        app.setStudentEmail(email);
        app.setDrive(drive);
        app.setAppliedAt(LocalDateTime.now());
        app.setStatus(
                eligible
                        ? ApplicationStatus.ELIGIBLE
                        : ApplicationStatus.REJECTED
        );

        StudentApplication saved =
                applicationRepo.saveAndFlush(app);

        if (saved.getStatus() == ApplicationStatus.ELIGIBLE) {
            interviewService.startInterviewProcess(saved);
        }

        return saved;
    }
}

