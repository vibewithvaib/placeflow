package com.placeflow.studentservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_experiences")
public class StudentExperience {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String companyName;
    private String role;
    private Integer durationMonths;

    private boolean verified;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}

