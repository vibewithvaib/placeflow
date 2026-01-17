package com.placeflow.studentservice.controller.internal;

import com.placeflow.studentservice.dto.StudentProfileDTO;
import com.placeflow.studentservice.service.StudentProfileService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/students")
public class InternalStudentController {

    private final StudentProfileService service;

    public InternalStudentController(StudentProfileService service) {
        this.service = service;
    }

    @GetMapping("/{email}")
    public StudentProfileDTO get(@PathVariable String email) {
        return StudentProfileDTO.from(service.get(email));
    }
}

