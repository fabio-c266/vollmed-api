package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateDoctorDTO(
        @Size(min = 3, max = 100, message = "No mínimo 3 caracteres e no máximo 100")
        String name,

        @Email(message = "Email inválido")
        @Size(max = 100, message = "Email muito grande")
        String email,

        @Size(max = 11, min = 11, message = "Telefone inválido")
        String phone,

        @JsonAlias("is_active") boolean isActive,

        @Valid UpdateAddressDTO updateAddressDTO
) {
}
