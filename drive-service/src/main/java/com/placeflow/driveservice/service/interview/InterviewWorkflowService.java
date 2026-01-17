package com.placeflow.driveservice.service.interview;

import com.placeflow.driveservice.entity.*;
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

    /** Start interview only once */
    public void startInterviewProcess(StudentApplication app) {

        List<InterviewRound> rounds =
                roundRepo.findByDriveIdOrderByRoundOrder(
                        app.getDrive().getId()
                );

        if (rounds.isEmpty())
            throw new RuntimeException("No rounds defined");

        // initialize ONLY first round
        StudentRoundStatus status = new StudentRoundStatus();
        status.setApplication(app);
        status.setRound(rounds.get(0));
        status.setResult(RoundResult.PENDING);

        statusRepo.save(status);
    }

    /** Update round result */
    public void updateRoundResult(
            StudentApplication app,
            InterviewRound round,
            RoundResult result
    ) {

        StudentRoundStatus current =
                statusRepo.findByApplicationIdAndRoundId(
                        app.getId(), round.getId()
                ).orElseThrow(() ->
                        new RuntimeException("Round not initialized")
                );

        // ❌ prevent re-update
        if (current.getResult() != RoundResult.PENDING)
            throw new RuntimeException("Round already evaluated");

        current.setResult(result);
        statusRepo.save(current);

        if (result == RoundResult.PASSED) {
            moveToNextRound(app, round);
        } else {
            // failed → stop workflow
            app.setStatus(ApplicationStatus.REJECTED);
        }
    }

    /** Move to next round ONLY if passed */
    private void moveToNextRound(StudentApplication app, InterviewRound current) {

        List<InterviewRound> rounds =
                roundRepo.findByDriveIdOrderByRoundOrder(
                        app.getDrive().getId()
                );

        int idx = rounds.indexOf(current);

        if (idx + 1 >= rounds.size()) {
            // 🎉 selected
            app.setStatus(ApplicationStatus.SELECTED);
            return;
        }

        InterviewRound next = rounds.get(idx + 1);

        StudentRoundStatus nextStatus = new StudentRoundStatus();
        nextStatus.setApplication(app);
        nextStatus.setRound(next);
        nextStatus.setResult(RoundResult.PENDING);

        statusRepo.save(nextStatus);
    }
}

