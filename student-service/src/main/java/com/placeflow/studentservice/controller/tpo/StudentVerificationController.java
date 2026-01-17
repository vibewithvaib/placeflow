package com.placeflow.studentservice.controller.tpo;

import com.placeflow.studentservice.service.StudentProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tpo/students")
@PreAuthorize("hasRole('TPO')")
public class StudentVerificationController {

    private final StudentProfileService service;

    public StudentVerificationController(StudentProfileService service) {
        this.service = service;
    }

    @PatchMapping("/{email}/verify")
    public void verify(@PathVariable String email) {
        service.verify(email);
    }
}

