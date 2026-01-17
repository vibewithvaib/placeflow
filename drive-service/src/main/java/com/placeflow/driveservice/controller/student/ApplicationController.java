package com.placeflow.driveservice.controller.student;

import com.placeflow.driveservice.StudentClient;
import com.placeflow.driveservice.dto.StudentProfileDTO;
import com.placeflow.driveservice.entity.*;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.DriveRepository;
import com.placeflow.driveservice.repository.EligibilityRepository;
import com.placeflow.driveservice.service.eligibility.EligibilityEvaluator;
import com.placeflow.driveservice.service.interview.InterviewWorkflowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.LocalDateTime;

@RestController
@RequestMapping("/student/applications")
@PreAuthorize("hasRole('STUDENT')")
public class ApplicationController {

    private final ApplicationRepository appRepo;
    private final DriveRepository driveRepo;
    private final EligibilityRepository eligibilityRepo;
    private final StudentClient studentClient;
    private final EligibilityEvaluator evaluator;
    private final InterviewWorkflowService workflowService;

    public ApplicationController(
            ApplicationRepository appRepo,
            DriveRepository driveRepo,
            EligibilityRepository eligibilityRepo,
            EligibilityEvaluator evaluator,
            InterviewWorkflowService workflowService,
            StudentClient studentClient
    ) {
        this.appRepo = appRepo;
        this.driveRepo = driveRepo;
        this.eligibilityRepo = eligibilityRepo;
        this.studentClient = studentClient;
        this.evaluator = evaluator;
        this.workflowService = workflowService;
    }

    @PostMapping("/{driveId}")
    public StudentApplication apply(
            @PathVariable Long driveId,
            Authentication auth
    ) {
        String email = auth.getName();

        PlacementDrive drive = driveRepo.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        if (drive.getStatus() != DriveStatus.OPEN)
            throw new RuntimeException("Drive not open");

        if (drive.getApplicationDeadline().isBefore(LocalDate.now()))
            throw new RuntimeException("Deadline passed");

        appRepo.findByStudentEmailAndDriveId(email, driveId)
                .ifPresent(a -> { throw new RuntimeException("Already applied"); });

        EligibilityCriteria criteria =
                eligibilityRepo.findByDriveId(driveId);

        if (criteria == null)
            throw new RuntimeException("Eligibility not defined");
        StudentProfileDTO student = null;
        try {
            student = studentClient.getStudent(email);
        } catch (Exception e) {
            throw new RuntimeException("Student service unavailable");
        }

        boolean eligible =
                evaluator.isEligible(student, criteria);


        StudentApplication app = new StudentApplication();
        app.setStudentEmail(email);
        app.setDrive(drive);
        app.setAppliedAt(LocalDateTime.now());
        app.setStatus(
                eligible ? ApplicationStatus.ELIGIBLE : ApplicationStatus.REJECTED
        );

        StudentApplication saved = appRepo.save(app);

        if (eligible) {
            workflowService.startInterviewProcess(saved);
        }

        return saved;
    }

}

