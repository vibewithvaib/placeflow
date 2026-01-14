package com.placeflow.studentservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_skills")
public class StudentSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String skillName;
    private String proficiency;

    private boolean verified;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
