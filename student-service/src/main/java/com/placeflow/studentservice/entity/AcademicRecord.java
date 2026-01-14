package com.placeflow.studentservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "academic_records")
public class AcademicRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double tenthPercentage;
    private Double twelfthPercentage;   // nullable
    private Double diplomaPercentage;   // nullable

    private Double cgpa;
    private Integer backlogs;

    private boolean verified; // set ONLY after document verification

    @OneToOne
    @JoinColumn(name = "student_id")
    private Student student;
}

