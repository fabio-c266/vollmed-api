package com.vollmed.api.dtos;

import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.domain.doctor.DoctorSpecialties;

import java.time.LocalDateTime;

public record DoctorResponseDTO(
        Long id,
        String name,
        String email,
        String crm,
        String phone,
        DoctorSpecialties specialty,
        boolean active,
        LocalDateTime created_at,
        AddressResponseDTO address
) {
    public DoctorResponseDTO(Doctor doctor) {
        this(
                doctor.getId(),
                doctor.getName(),
                doctor.getEmail(),
                doctor.getCrm(),
                doctor.getPhone(),
                doctor.getSpecialty(),
                doctor.isActive(),
                doctor.getCreatedAt(),
                new AddressResponseDTO(
                        doctor.getAddress().getId(),
                        doctor.getAddress().getCEP(),
                        doctor.getAddress().getStreet(),
                        doctor.getAddress().getNeighborhood(),
                        doctor.getAddress().getComplement(),
                        doctor.getAddress().getNumber(),
                        doctor.getAddress().getCity().getName(),
                        doctor.getAddress().getCity().getUf().getName()
                )
        );
    }
}
