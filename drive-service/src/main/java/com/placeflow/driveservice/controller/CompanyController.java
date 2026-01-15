package com.placeflow.driveservice.controller;

import com.placeflow.driveservice.entity.Company;
import com.placeflow.driveservice.repository.CompanyRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/companies")
@PreAuthorize("hasRole('TPO')")
public class CompanyController {

    private final CompanyRepository companyRepository;

    public CompanyController(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    /**
     * TPO creates company profile
     */
    @PostMapping
    public Company createCompany(
            @RequestBody Company company,
            Authentication authentication
    ) {
        company.setCreatedBy(authentication.getName()); // TPO email
        return companyRepository.save(company);
    }
}
