package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.entity.StudentRoundStatus;
import com.placeflow.driveservice.repository.StudentRoundStatusRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/student/interviews")
@PreAuthorize("hasRole('STUDENT')")
public class StudentInterviewController {

    private final StudentRoundStatusRepository repo;

    public StudentInterviewController(StudentRoundStatusRepository repo) {
        this.repo = repo;
    }

    @GetMapping("/applications/{appId}")
    public List<StudentRoundStatus> viewStatus(@PathVariable Long appId) {
        return repo.findByApplicationId(appId);
    }
}
