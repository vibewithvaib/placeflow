package com.placeflow.driveservice.service.offer;

import com.placeflow.driveservice.entity.ApplicationStatus;
import com.placeflow.driveservice.entity.Offer;
import com.placeflow.driveservice.entity.OfferStatus;
import com.placeflow.driveservice.entity.StudentApplication;
import com.placeflow.driveservice.repository.ApplicationRepository;
import com.placeflow.driveservice.repository.OfferRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class OfferService {

    private final OfferRepository offerRepo;
    private final ApplicationRepository appRepo;

    public OfferService(
            OfferRepository offerRepo,
            ApplicationRepository appRepo
    ) {
        this.offerRepo = offerRepo;
        this.appRepo = appRepo;
    }

    /** Company issues offer */
    public Offer issueOffer(StudentApplication app) {

        if (app.getStatus() != ApplicationStatus.SELECTED)
            throw new RuntimeException("Student not selected");

        offerRepo.findByApplicationId(app.getId())
                .ifPresent(o -> {
                    throw new RuntimeException("Offer already issued");
                });

        Offer offer = new Offer();
        offer.setApplication(app);
        offer.setStatus(OfferStatus.ISSUED);
        offer.setIssuedAt(LocalDateTime.now());

        return offerRepo.save(offer);
    }

    /** Student accepts offer */
    public void acceptOffer(Long offerId, String studentEmail) {

        Offer offer = offerRepo.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        StudentApplication app = offer.getApplication();

        if (!app.getStudentEmail().equals(studentEmail))
            throw new RuntimeException("Unauthorized");

        if (offer.getStatus() != OfferStatus.ISSUED)
            throw new RuntimeException("Offer not active");

        offer.setStatus(OfferStatus.ACCEPTED);
        offerRepo.save(offer);

        // 🚫 reject all other applications of this student
        List<StudentApplication> others =
                appRepo.findByStudentEmailAndDriveIdNot(
                        studentEmail,
                        app.getDrive().getId()
                );

        for (StudentApplication a : others) {
            a.setStatus(ApplicationStatus.NOT_SELECTED);
        }
        appRepo.saveAll(others);
    }

    /** Student rejects offer */
    public void rejectOffer(Long offerId, String studentEmail) {

        Offer offer = offerRepo.findById(offerId)
                .orElseThrow(() -> new RuntimeException("Offer not found"));

        StudentApplication app = offer.getApplication();

        if (!app.getStudentEmail().equals(studentEmail))
            throw new RuntimeException("Unauthorized");

        offer.setStatus(OfferStatus.REJECTED);
        offerRepo.save(offer);
    }
}

