package com.placeflow.driveservice.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CreateDriveRequest {

    private Long companyId;
    private String title;
    private String jobRole;
    private Double ctc;
    private String description;
    private LocalDate applicationDeadline;

    // getters & setters
}
