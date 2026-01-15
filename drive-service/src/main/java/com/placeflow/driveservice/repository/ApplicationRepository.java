package com.placeflow.driveservice.repository;

import com.placeflow.driveservice.entity.StudentApplication;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ApplicationRepository extends JpaRepository<StudentApplication, Long> {

    Optional<StudentApplication> findByStudentEmailAndDriveId(
            String studentEmail,
            Long driveId
    );
    List<StudentApplication> findByDriveId(Long driveId);
}
