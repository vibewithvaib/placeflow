package com.placeflow.driveservice.controller.company;

import com.placeflow.driveservice.dto.CreateInterviewRoundRequest;
import com.placeflow.driveservice.entity.InterviewRound;
import com.placeflow.driveservice.entity.PlacementDrive;
import com.placeflow.driveservice.repository.DriveRepository;
import com.placeflow.driveservice.repository.InterviewRoundRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/company/drives")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyRoundController {

    private final InterviewRoundRepository roundRepo;
    private final DriveRepository driveRepo;

    public CompanyRoundController(
            InterviewRoundRepository roundRepo,
            DriveRepository driveRepo
    ) {
        this.roundRepo = roundRepo;
        this.driveRepo = driveRepo;
    }

    @PostMapping("/{driveId}/rounds")
    public InterviewRound addRound(
            @PathVariable Long driveId,
            @RequestBody CreateInterviewRoundRequest req,
            Authentication auth
    ) {
        PlacementDrive drive = driveRepo.findById(driveId)
                .orElseThrow(() -> new RuntimeException("Drive not found"));

        // 🔐 ownership check
        if (!drive.getCompany().getAuthEmail().equals(auth.getName()))
            throw new RuntimeException("Not your drive");

        InterviewRound round = new InterviewRound();
        round.setDrive(drive);
        round.setRoundOrder(req.getRoundOrder());
        round.setName(req.getName());
        round.setType(req.getType());

        return roundRepo.save(round);
    }
}

