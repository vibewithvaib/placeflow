package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.StudentRoundStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StudentRoundStatusRepository
        extends JpaRepository<StudentRoundStatus, Long> {

    Optional<StudentRoundStatus> findByApplicationIdAndRoundId(
            Long applicationId,
            Long roundId
    );

    List<StudentRoundStatus> findByApplicationId(Long applicationId);

    boolean existsByApplicationId(Long applicationId);
}
