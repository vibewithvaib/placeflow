package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.DTO.CreateInterviewRoundRequest;
import com.placeflow.driveservice.entity.*;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.DriveRepository;
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
    private final DriveRepository driveRepository;

    public CompanyInterviewController(
            InterviewRoundRepository roundRepo,
            ApplicationRepository appRepo,
            InterviewWorkflowService workflowService,
            DriveRepository driveRepository
    ) {
        this.roundRepo = roundRepo;
        this.appRepo = appRepo;
        this.workflowService = workflowService;
        this.driveRepository = driveRepository;
    }

    @PostMapping("/drives/{driveId}/rounds")
    public InterviewRound addRound(
            @PathVariable Long driveId,
            @RequestBody CreateInterviewRoundRequest request
    ) {
        PlacementDrive drive = driveRepository.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        InterviewRound round = new InterviewRound();
        round.setDrive(drive);
        round.setRoundOrder(request.getRoundOrder());
        round.setName(request.getName());
        round.setType(request.getType());

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


