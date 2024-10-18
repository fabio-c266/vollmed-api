package com.vollmed.api.repositories;

import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.domain.doctor.DoctorSpecialties;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);

    Optional<Doctor> findByCrm(String crm);

    Optional<Doctor> findByPhone(String phone);

    @Query("""
            SELECT d FROM Doctor d
            WHERE d.active = true
            AND d.specialty = :specialty
            AND d.id NOT IN (
                SELECT c.doctor.id FROM Consultation c
                WHERE c.consultationDate = :consultationDate
                AND c.status != 'CANCELADA'
            )
            """)
    Optional<Doctor> findRandomDoctorBySpecialty(DoctorSpecialties specialty, LocalDateTime consultationDate);
}
