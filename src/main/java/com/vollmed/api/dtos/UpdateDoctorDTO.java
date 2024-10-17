package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;

public record UpdateDoctorDTO(
        @Size(min = 3, max = 100, message = "no mínimo 3 caracteres e no máximo 100")
        String name,

        @Email(message = "Email inválido")
        @Size(max = 100, message = "email muito grande")
        String email,

        @Size(max = 11, min = 11, message = "telefone inválido")
        String phone,

        @JsonAlias("address") @Valid UpdateAddressDTO updateAddressDTO
) {
}
