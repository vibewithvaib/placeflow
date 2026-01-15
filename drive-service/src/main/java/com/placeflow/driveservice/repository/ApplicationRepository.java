package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApplicationRepository extends JpaRepository<StudentApplication, Long> {

    Optional<StudentApplication> findByStudentEmailAndDriveId(
            String studentEmail,
            Long driveId
    );
}
