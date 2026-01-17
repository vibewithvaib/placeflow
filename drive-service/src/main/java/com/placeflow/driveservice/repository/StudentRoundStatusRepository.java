package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.StudentRoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface StudentRoundStatusRepository
        extends JpaRepository<StudentRoundStatus, Long> {

    List<StudentRoundStatus> findByApplicationId(Long appId);

    Optional<StudentRoundStatus>
    findByApplicationIdAndRoundId(Long appId, Long roundId);
}

