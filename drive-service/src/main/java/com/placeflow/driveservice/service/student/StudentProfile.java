package com.placeflow.driveservice.service.student;

import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class StudentProfile {
    private double cgpa;
    private int marks10;
    private int marks12;
    private String branch;
    private Set<String> skills;
    private int experienceMonths;
}

