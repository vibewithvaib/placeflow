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

    private final ApplicationRepository applicationRepository;
    private final DriveRepository driveRepository;
    private final EligibilityRepository eligibilityRepository;
    private final StudentProfileService studentProfileService;
    private final EligibilityEvaluator eligibilityEvaluator;
    private final InterviewWorkflowService interviewWorkflowService;

    public ApplicationController(
            ApplicationRepository applicationRepository,
            DriveRepository driveRepository,
            EligibilityRepository eligibilityRepository,
            StudentProfileService studentProfileService,
            EligibilityEvaluator eligibilityEvaluator,
            InterviewWorkflowService interviewWorkflowService
    ) {
        this.applicationRepository = applicationRepository;
        this.driveRepository = driveRepository;
        this.eligibilityRepository = eligibilityRepository;
        this.studentProfileService = studentProfileService;
        this.eligibilityEvaluator = eligibilityEvaluator;
        this.interviewWorkflowService = interviewWorkflowService;
    }

    @PostMapping("/{driveId}")
    public StudentApplication apply(
            @PathVariable Long driveId,
            Authentication authentication
    ) {
        String studentEmail = authentication.getName();

        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        if (drive.getStatus() != DriveStatus.OPEN) {
            throw new RuntimeException("Drive is not open");
        }

        applicationRepository.findByStudentEmailAndDriveId(studentEmail, driveId)
                .ifPresent(a -> {
                    throw new RuntimeException("Already applied");
                });

        EligibilityCriteria criteria =
                eligibilityRepository.findByDriveId(driveId);

        if (criteria == null) {
            throw new RuntimeException("Eligibility criteria not defined");
        }

        StudentProfile student =
                studentProfileService.getProfile(studentEmail);

        if (student == null) {
            throw new RuntimeException("Student profile not found");
        }

        boolean eligible =
                eligibilityEvaluator.isEligible(student, criteria);

        StudentApplication app = new StudentApplication();
        app.setStudentEmail(studentEmail);
        app.setDrive(drive);
        app.setAppliedAt(LocalDateTime.now());
        app.setStatus(
                eligible
                        ? ApplicationStatus.ELIGIBLE
                        : ApplicationStatus.REJECTED
        );

        StudentApplication saved =
                applicationRepository.saveAndFlush(app);

        if (saved.getStatus() == ApplicationStatus.ELIGIBLE) {
            interviewWorkflowService.startInterviewProcess(saved);
        }

        return saved;
    }

}
