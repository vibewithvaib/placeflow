package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.InterviewRound;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface InterviewRoundRepository
        extends JpaRepository<InterviewRound, Long> {

    List<InterviewRound> findByDriveIdOrderByRoundOrder(Long driveId);
}

