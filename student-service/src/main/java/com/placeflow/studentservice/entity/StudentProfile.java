package com.placeflow.studentservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class StudentProfile {

    @Id
    private String email;

    private Double cgpa;
    private Integer tenthMarks;
    private Integer twelfthOrDiplomaMarks;
    private String branch;

    @ElementCollection
    private Set<String> skills;

    private Integer experienceMonths;

    private String tenthMarksheetLink;
    private String twelfthMarksheetLink;
    private String experienceLink;

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;
}

