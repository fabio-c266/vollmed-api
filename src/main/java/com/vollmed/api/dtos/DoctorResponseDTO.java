package com.vollmed.api.dtos;

import com.vollmed.api.domain.address.Address;
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
    public DoctorResponseDTO(Doctor doctor, Address address) {
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
                        address.getId(),
                        address.getCEP(),
                        address.getStreet(),
                        address.getNeighborhood(),
                        address.getComplement(),
                        address.getNumber(),
                        address.getCity().getName(),
                        address.getCity().getUf().getName()
                )
        );
    }
}
