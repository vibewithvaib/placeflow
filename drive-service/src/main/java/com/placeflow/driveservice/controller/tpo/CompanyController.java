package com.placeflow.driveservice.controller.tpo;

import com.placeflow.driveservice.entity.Company;
import com.placeflow.driveservice.repository.CompanyRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tpo/companies")
@PreAuthorize("hasRole('TPO')")
public class CompanyController {

    private final CompanyRepository repo;

    public CompanyController(CompanyRepository repo) {
        this.repo = repo;
    }

    @PostMapping
    public Company create(@RequestBody Company company) {
        return repo.save(company);
    }

    @GetMapping
    public List<Company> list() {
        return repo.findAll();
    }
}

