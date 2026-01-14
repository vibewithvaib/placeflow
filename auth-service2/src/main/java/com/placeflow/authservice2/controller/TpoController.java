package com.placeflow.authservice2.controller;

import com.placeflow.authservice2.dto.CreateCompanyUserRequest;
import com.placeflow.authservice2.entity.Role;
import com.placeflow.authservice2.entity.User;
import com.placeflow.authservice2.repository.UserRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tpo")
@PreAuthorize("hasRole('TPO')")
public class TpoController {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public TpoController(UserRepository userRepository,
                         PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @PostMapping("/companies")
    public void createCompany(@RequestBody CreateCompanyUserRequest request) {

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new RuntimeException("Company user already exists");
        }

        User companyUser = new User();
        companyUser.setEmail(request.getEmail());
        companyUser.setPassword(passwordEncoder.encode(request.getPassword()));
        companyUser.setRole(Role.COMPANY);

        userRepository.save(companyUser);
    }
}