package com.vollmed.api.dtos;

import com.vollmed.api.domain.doctor.Doctor;

public record SimpleDoctorResponseDTO(
        Long id,
        String name,
        String crm,
        String email,
        String phone
) {
    public SimpleDoctorResponseDTO(Doctor doctor) {
        this(
                doctor.getId(),
                doctor.getName(),
                doctor.getCrm(),
                doctor.getEmail(),
                doctor.getPhone()
        );
    }
}
