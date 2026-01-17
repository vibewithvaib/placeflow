package com.placeflow.studentservice.controller.student;

import com.placeflow.studentservice.entity.StudentProfile;
import com.placeflow.studentservice.service.StudentProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/students")
@PreAuthorize("hasRole('STUDENT')")
public class StudentProfileController {

    private final StudentProfileService service;

    public StudentProfileController(StudentProfileService service) {
        this.service = service;
    }

    @PostMapping
    public StudentProfile create(@RequestBody StudentProfile profile) {
        return service.save(profile);
    }

    @GetMapping("/me")
    public StudentProfile me(Authentication auth) {
        return service.get(auth.getName());
    }
}

