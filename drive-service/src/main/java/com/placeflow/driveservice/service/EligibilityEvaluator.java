package com.placeflow.driveservice.service;

import com.placeflow.driveservice.entity.EligibilityCriteria;
import org.springframework.stereotype.Component;

@Component
public class EligibilityEvaluator {

    public boolean isEligible(
            StudentProfile student,
            EligibilityCriteria criteria
    ) {

        if (student.getCgpa() < criteria.getMinCgpa()) return false;
        if (student.getTenthMarks() < criteria.getMin10thMarks()) return false;
        if (student.getTwelfthOrDiplomaMarks() < criteria.getMin12thOrDiplomaMarks()) return false;

        if (!criteria.getAllowedBranches().contains(student.getBranch())) return false;

        if (!student.getSkills().containsAll(criteria.getRequiredSkills())) return false;

        if (student.getExperienceMonths() < criteria.getMinExperienceMonths()) return false;

        return true;
    }
}
