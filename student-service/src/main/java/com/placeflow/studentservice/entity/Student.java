package com.placeflow.studentservice.entity;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String branch;

    @OneToOne(mappedBy = "student", cascade = CascadeType.ALL)
    private AcademicRecord academicRecord;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentSkill> skills;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentExperience> experiences;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL)
    private List<StudentDocument> documents;
}
