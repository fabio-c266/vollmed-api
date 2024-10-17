package com.vollmed.api.repositories;

import com.vollmed.api.domain.doctor.Doctor;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DoctorRepository extends JpaRepository<Doctor, Long> {
    Optional<Doctor> findByEmail(String email);
    Optional<Doctor> findByCrm(String crm);
    Optional<Doctor> findByPhone(String phone);
}
