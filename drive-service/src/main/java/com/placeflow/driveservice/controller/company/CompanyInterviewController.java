package com.placeflow.driveservice.controller.company;

import com.placeflow.driveservice.entity.InterviewRound;
import com.placeflow.driveservice.entity.RoundResult;
import com.placeflow.driveservice.entity.StudentApplication;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.InterviewRoundRepository;
import com.placeflow.driveservice.service.interview.InterviewWorkflowService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company/interviews")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyInterviewController {

    private final ApplicationRepository appRepo;
    private final InterviewRoundRepository roundRepo;
    private final InterviewWorkflowService workflow;

    public CompanyInterviewController(
            ApplicationRepository appRepo,
            InterviewRoundRepository roundRepo,
            InterviewWorkflowService workflow
    ) {
        this.appRepo = appRepo;
        this.roundRepo = roundRepo;
        this.workflow = workflow;
    }

    @PostMapping("/applications/{appId}/rounds/{roundId}")
    public void updateResult(
            @PathVariable Long appId,
            @PathVariable Long roundId,
            @RequestParam RoundResult result,
            Authentication auth
    ) {
        StudentApplication app = appRepo.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        if (!app.getDrive().getCompany().getAuthEmail().equals(auth.getName()))
            throw new RuntimeException("Not your drive");

        InterviewRound round = roundRepo.findById(roundId)
                .orElseThrow(() -> new RuntimeException("Round not found"));

        workflow.updateRoundResult(app, round, result);
    }
}


