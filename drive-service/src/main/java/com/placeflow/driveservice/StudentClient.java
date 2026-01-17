package com.placeflow.driveservice;

import com.placeflow.driveservice.dto.StudentProfileDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

@FeignClient(name = "student-service")
public interface StudentClient {

    @GetMapping("/internal/students/{email}")
    StudentProfileDTO getStudent(@PathVariable String email);
}

