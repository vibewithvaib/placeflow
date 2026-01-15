package com.placeflow.driveservice.service;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentProfileService {

    public StudentProfile getProfile(String studentEmail) {

        // TEMP MOCK DATA
        return new StudentProfile(
                8.1,                   // cgpa
                85,                    // 10th
                78,                    // 12th
                "CSE",                 // branch
                Set.of("Java", "SQL"), // skills
                6                      // experience (months)
        );
    }
}
