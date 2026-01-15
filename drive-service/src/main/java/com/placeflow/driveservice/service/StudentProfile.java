package com.placeflow.driveservice.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfile {

    private Double cgpa;
    private Integer tenthMarks;
    private Integer twelfthOrDiplomaMarks;
    private String branch;
    private Set<String> skills;
    private Integer experienceMonths;
}
