package com.placeflow.driveservice.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlacementDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Which company is hiring
    @ManyToOne
    private Company company;

    private String title;          // e.g. "SDE Hiring 2026"
    private String jobRole;        // SDE, Analyst
    private Double ctc;            // 12.5 LPA

    @Column(length = 2000)
    private String description;

    private LocalDate applicationDeadline;

    @Enumerated(EnumType.STRING)
    private DriveStatus status;
}
