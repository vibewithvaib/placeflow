package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.InterviewRound;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface InterviewRoundRepository
        extends JpaRepository<InterviewRound, Long> {

    List<InterviewRound> findByDriveIdOrderByRoundOrder(Long driveId);
}

