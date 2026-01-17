package com.placeflow.driveservice.service.student;

import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class StudentProfileService {

    public StudentProfile getProfile(String email) {
        // mock for now, real service later
        StudentProfile p = new StudentProfile();
        p.setCgpa(8.1);
        p.setMarks10(85);
        p.setMarks12(88);
        p.setBranch("CSE");
        p.setSkills(Set.of("Java", "DSA", "Spring"));
        p.setExperienceMonths(0);
        return p;
    }
}

