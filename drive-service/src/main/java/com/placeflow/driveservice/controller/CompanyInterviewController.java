package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.entity.InterviewRound;
import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.entity.RoundResult;
import com.placeflow.driveservice.entity.StudentApplication;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.InterviewRoundRepository;
import com.placeflow.driveservice.service.InterviewWorkflowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company/interviews")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyInterviewController {

    private final InterviewRoundRepository roundRepo;
    private final ApplicationRepository appRepo;
    private final InterviewWorkflowService workflowService;

    public CompanyInterviewController(
            InterviewRoundRepository roundRepo,
            ApplicationRepository appRepo,
            InterviewWorkflowService workflowService
    ) {
        this.roundRepo = roundRepo;
        this.appRepo = appRepo;
        this.workflowService = workflowService;
    }

    @PostMapping("/drives/{driveId}/rounds")
    public InterviewRound addRound(
            @PathVariable Long driveId,
            @RequestBody InterviewRound round
    ) {
        PlacementDrive drive = round.getDrive();
        round.setDrive(drive);
        return roundRepo.save(round);
    }

    @PostMapping("/applications/{appId}/rounds/{roundId}/result")
    public void updateResult(
            @PathVariable Long appId,
            @PathVariable Long roundId,
            @RequestParam RoundResult result
    ) {
        StudentApplication app =
                appRepo.findById(appId)
                        .orElseThrow(() -> new RuntimeException("Application not found"));

        InterviewRound round =
                roundRepo.findById(roundId)
                        .orElseThrow(() -> new RuntimeException("Round not found"));

        workflowService.updateRoundResult(app, round, result);
    }
}

