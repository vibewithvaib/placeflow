package com.placeflow.driveservice.controller.offer;

import com.placeflow.driveservice.entity.Offer;
import com.placeflow.driveservice.entity.StudentApplication;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.service.offer.OfferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/company/offers")
@PreAuthorize("hasRole('COMPANY')")
public class CompanyOfferController {

    private final OfferService offerService;
    private final ApplicationRepository appRepo;

    public CompanyOfferController(
            OfferService offerService,
            ApplicationRepository appRepo
    ) {
        this.offerService = offerService;
        this.appRepo = appRepo;
    }

    @PostMapping("/applications/{appId}")
    public Offer issueOffer(@PathVariable Long appId) {

        StudentApplication app = appRepo.findById(appId)
                .orElseThrow(() -> new RuntimeException("Application not found"));

        return offerService.issueOffer(app);
    }
}

