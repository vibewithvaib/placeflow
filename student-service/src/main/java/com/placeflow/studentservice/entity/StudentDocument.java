package com.placeflow.studentservice.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "student_documents")
public class StudentDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    private String fileName;
    private String filePath;      // e.g. uploads/student/{id}/file.pdf
    private String contentType;   // application/pdf

    @Enumerated(EnumType.STRING)
    private VerificationStatus verificationStatus;

    private String rejectionReason;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;
}
