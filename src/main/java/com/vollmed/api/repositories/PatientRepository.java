package com.vollmed.api.repositories;

import com.vollmed.api.domain.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PatientRepository extends JpaRepository<Patient, Long> {
    Optional<Patient> findByEmail(String email);
    Optional<Patient> findByCpf(String cpf);
    Optional<Patient> findByPhone(String phone);
}
