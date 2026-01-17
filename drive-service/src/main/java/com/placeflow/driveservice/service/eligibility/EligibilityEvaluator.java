package com.placeflow.driveservice.service.eligibility;

import com.placeflow.driveservice.dto.StudentProfileDTO;
import com.placeflow.driveservice.dto.VerificationStatus;
import com.placeflow.driveservice.entity.EligibilityCriteria;
import com.placeflow.driveservice.service.student.StudentProfile;
import org.springframework.stereotype.Service;

@Service
public class EligibilityEvaluator {

    public boolean isEligible(StudentProfileDTO s, EligibilityCriteria c) {

        if (s.getVerificationStatus() != VerificationStatus.VERIFIED)
            return false;

        if (s.getCgpa() < c.getMinCgpa()) return false;
        if (s.getTenthMarks() < c.getMin10thMarks()) return false;
        if (s.getTwelfthOrDiplomaMarks() < c.getMin12thOrDiplomaMarks()) return false;

        if (!c.getAllowedBranches().contains(s.getBranch())) return false;
        if (!s.getSkills().containsAll(c.getRequiredSkills())) return false;

        if (s.getExperienceMonths() < c.getMinExperienceMonths()) return false;

        return true;
    }
}


