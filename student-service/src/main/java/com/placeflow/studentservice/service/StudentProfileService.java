package com.placeflow.studentservice.service;

import com.placeflow.studentservice.entity.StudentProfile;
import com.placeflow.studentservice.entity.VerificationStatus;
import com.placeflow.studentservice.repository.StudentProfileRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StudentProfileService {

    private final StudentProfileRepository repo;

    public StudentProfileService(StudentProfileRepository repo) {
        this.repo = repo;
    }

    public StudentProfile save(StudentProfile profile) {
        profile.setVerificationStatus(VerificationStatus.PENDING);
        return repo.save(profile);
    }

    public StudentProfile get(String email) {
        return repo.findById(email)
                .orElseThrow(() -> new RuntimeException("Student not found"));
    }

    public List<StudentProfile> getAll() {
        return repo.findAll();
    }

    public void verify(String email) {
        StudentProfile s = get(email);
        s.setVerificationStatus(VerificationStatus.VERIFIED);
        repo.save(s);
    }
}

