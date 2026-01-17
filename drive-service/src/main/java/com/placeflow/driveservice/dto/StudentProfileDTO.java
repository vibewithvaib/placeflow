package com.placeflow.driveservice.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter @Setter
public class StudentProfileDTO {
    private String email;
    private Double cgpa;
    private Integer tenthMarks;
    private Integer twelfthOrDiplomaMarks;
    private String branch;
    private Set<String> skills;
    private Integer experienceMonths;
    private VerificationStatus verificationStatus;
}


