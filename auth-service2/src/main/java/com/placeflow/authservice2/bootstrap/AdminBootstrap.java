package com.placeflow.authservice2.bootstrap;

import com.placeflow.authservice2.entity.Role;
import com.placeflow.authservice2.entity.User;
import com.placeflow.authservice2.repository.UserRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class AdminBootstrap {

    private final UserRepository repo;
    private final PasswordEncoder encoder;

    @Value("${app.bootstrap.admin.email}")
    private String email;

    @Value("${app.bootstrap.admin.password}")
    private String password;

    public AdminBootstrap(UserRepository repo, PasswordEncoder encoder) {
        this.repo = repo;
        this.encoder = encoder;
    }

    @PostConstruct
    public void init() {
        if (!repo.existsByRole(Role.ADMIN)) {
            User admin = new User();
            admin.setEmail(email);
            admin.setPassword(encoder.encode(password));
            admin.setRole(Role.ADMIN);
            repo.save(admin);
            System.out.println("✅ ADMIN CREATED");
        }
    }
}
