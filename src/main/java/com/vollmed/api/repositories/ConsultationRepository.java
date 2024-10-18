package com.vollmed.api.repositories;

import com.vollmed.api.domain.consultation.Consultation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Optional;

public interface ConsultationRepository extends JpaRepository<Consultation, Long> {

    @Query("""
            SELECT c.id FROM Consultation c
            WHERE c.patient.id = :patientId
            AND FUNCTION('DATE', c.consultationDate) = :consultationDate
            AND c.status != 'CANCELADA'
            """)
    Optional<Consultation> findPatientConsultationInSameDay(Long patientId, LocalDate consultationDate);

    @Query("""
            SELECT c.id FROM Consultation c
            WHERE c.doctor.id = :doctorId
            AND c.consultationDate = :consultationTime
            AND c.status != 'CANCELADA'
            """)
    Optional<Consultation> findDoctorConsultationInSameHour(Long doctorId, LocalDateTime consultationTime);
}
