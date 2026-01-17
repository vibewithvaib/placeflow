package com.placeflow.studentservice.dto;

import com.placeflow.studentservice.entity.StudentProfile;
import com.placeflow.studentservice.entity.VerificationStatus;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class StudentProfileDTO {

    private String email;
    private Double cgpa;
    private Integer tenthMarks;
    private Integer twelfthOrDiplomaMarks;
    private String branch;
    private Set<String> skills;
    private Integer experienceMonths;
    private VerificationStatus verificationStatus;

    public static StudentProfileDTO from(StudentProfile s) {
        StudentProfileDTO d = new StudentProfileDTO();
        d.setEmail(s.getEmail());
        d.setCgpa(s.getCgpa());
        d.setTenthMarks(s.getTenthMarks());
        d.setTwelfthOrDiplomaMarks(s.getTwelfthOrDiplomaMarks());
        d.setBranch(s.getBranch());
        d.setSkills(s.getSkills());
        d.setExperienceMonths(s.getExperienceMonths());
        d.setVerificationStatus(s.getVerificationStatus());
        return d;
    }
}

