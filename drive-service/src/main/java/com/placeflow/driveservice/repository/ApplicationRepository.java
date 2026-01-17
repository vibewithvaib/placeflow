package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ApplicationRepository
        extends JpaRepository<StudentApplication, Long> {

    Optional<StudentApplication> findByStudentEmailAndDriveId(
            String email, Long driveId
    );

    List<StudentApplication> findByDriveId(Long driveId);

    List<StudentApplication> findByStudentEmailAndDriveIdNot(String studentEmail, Long driveId);
}

