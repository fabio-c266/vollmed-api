package com.vollmed.api.dtos;

import com.vollmed.api.domain.patient.Patient;

public record SimplePatientResponseDTO(
        Long id,
        String name,
        String email,
        String phone,
        String cpf
) {
    public SimplePatientResponseDTO(Patient patient) {
        this(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getCpf()
        );
    }
}
