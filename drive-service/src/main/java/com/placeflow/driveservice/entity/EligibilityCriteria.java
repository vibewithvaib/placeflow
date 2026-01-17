package com.placeflow.driveservice.entity;

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
public class EligibilityCriteria {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(optional = false)
    private PlacementDrive drive;

    private Double minCgpa;
    private Integer min10thMarks;
    private Integer min12thOrDiplomaMarks;

    @ElementCollection
    private Set<String> allowedBranches;

    @ElementCollection
    private Set<String> requiredSkills;

    private Integer minExperienceMonths;
}

