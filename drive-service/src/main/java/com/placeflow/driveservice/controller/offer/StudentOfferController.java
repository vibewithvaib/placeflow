package com.placeflow.driveservice.controller.offer;

import com.placeflow.driveservice.service.offer.OfferService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/student/offers")
@PreAuthorize("hasRole('STUDENT')")
public class StudentOfferController {

    private final OfferService offerService;

    public StudentOfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/{offerId}/accept")
    public void accept(
            @PathVariable Long offerId,
            Authentication auth
    ) {
        offerService.acceptOffer(offerId, auth.getName());
    }

    @PostMapping("/{offerId}/reject")
    public void reject(
            @PathVariable Long offerId,
            Authentication auth
    ) {
        offerService.rejectOffer(offerId, auth.getName());
    }
}

