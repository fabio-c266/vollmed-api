package com.vollmed.api.dtos;

import com.vollmed.api.domain.patient.Patient;

import java.time.LocalDateTime;

public record PatientResponseDTO(
        Long id,
        String name,
        String email,
        String cpf,
        String phone,
        boolean active,
        LocalDateTime created_at,
        AddressResponseDTO address
) {
    public PatientResponseDTO(Patient patient) {
        this(
                patient.getId(),
                patient.getName(),
                patient.getEmail(),
                patient.getPhone(),
                patient.getCpf(),
                patient.isActive(),
                patient.getCreatedAt(),
                new AddressResponseDTO(patient.getAddress())
        );
    }
}
