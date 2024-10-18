package com.vollmed.api.dtos;

import com.vollmed.api.domain.consultation.Consultation;
import com.vollmed.api.domain.consultation.ConsultationStatus;

import java.time.LocalDateTime;

public record ConsultationResponseDTO(
        Long id,
        SimplePatientResponseDTO patient,
        SimpleDoctorResponseDTO doctor,
        LocalDateTime consultation_date,
        ConsultationStatus status,
        LocalDateTime created_at
) {
    public ConsultationResponseDTO(Consultation consultation) {
        this(
                consultation.getId(),
                new SimplePatientResponseDTO(consultation.getPatient()),
                new SimpleDoctorResponseDTO(consultation.getDoctor()),
                consultation.getConsultationDate(),
                consultation.getStatus(),
                consultation.getCreatedAt()
        );
    }
}
