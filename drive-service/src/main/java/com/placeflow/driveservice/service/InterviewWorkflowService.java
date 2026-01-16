package com.placeflow.driveservice.service;

import com.placeflow.driveservice.entity.*;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.InterviewRoundRepository;
import com.placeflow.driveservice.repository.StudentRoundStatusRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InterviewWorkflowService {

    private final InterviewRoundRepository roundRepo;
    private final StudentRoundStatusRepository statusRepo;

    public InterviewWorkflowService(
            InterviewRoundRepository roundRepo,
            StudentRoundStatusRepository statusRepo
    ) {
        this.roundRepo = roundRepo;
        this.statusRepo = statusRepo;
    }

    /**
     * Called ONLY when student becomes ELIGIBLE
     */
    public void startInterviewProcess(StudentApplication app) {

        // 🔐 Prevent duplicate initialization
        if (statusRepo.existsByApplicationId(app.getId())) {
            return;
        }

        List<InterviewRound> rounds =
                roundRepo.findByDriveIdOrderByRoundOrder(
                        app.getDrive().getId()
                );

        if (rounds.isEmpty()) {
            throw new RuntimeException("No interview rounds defined");
        }

        InterviewRound firstRound = rounds.get(0);

        StudentRoundStatus status = new StudentRoundStatus();
        status.setApplication(app);
        status.setRound(firstRound);
        status.setResult(RoundResult.PENDING);

        statusRepo.save(status);
    }

    /**
     * Company updates result
     */
    public void updateRoundResult(
            StudentApplication app,
            InterviewRound round,
            RoundResult result
    ) {
        StudentRoundStatus status =
                statusRepo.findByApplicationIdAndRoundId(
                        app.getId(), round.getId()
                ).orElseThrow(
                        () -> new RuntimeException("Interview round not started yet")
                );

        // ❌ Prevent re-updating completed round
        if (status.getResult() != RoundResult.PENDING) {
            throw new RuntimeException("Round already evaluated");
        }

        status.setResult(result);
        statusRepo.save(status);

        if (result == RoundResult.PASSED) {
            moveToNextRound(app, round);
        }
    }

    private void moveToNextRound(
            StudentApplication app,
            InterviewRound currentRound
    ) {
        List<InterviewRound> rounds =
                roundRepo.findByDriveIdOrderByRoundOrder(
                        app.getDrive().getId()
                );

        int index = rounds.indexOf(currentRound);

        if (index + 1 < rounds.size()) {
            InterviewRound nextRound = rounds.get(index + 1);

            StudentRoundStatus nextStatus = new StudentRoundStatus();
            nextStatus.setApplication(app);
            nextStatus.setRound(nextRound);
            nextStatus.setResult(RoundResult.PENDING);

            statusRepo.save(nextStatus);
        }
        // else → final round completed
    }
}
