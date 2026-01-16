package com.placeflow.driveservice.entity;

public enum ApplicationStatus {

    APPLIED,        // student applied
    ELIGIBLE,       // passed eligibility
    REJECTED,       // failed eligibility
    IN_PROGRESS,    // interview rounds running
    SELECTED,       // passed all interview rounds
    PLACED          // offer accepted
}

