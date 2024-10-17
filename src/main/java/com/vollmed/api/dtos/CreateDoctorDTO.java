package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.vollmed.api.domain.doctor.DoctorSpecialties;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateDoctorDTO(
        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 3, max = 100, message = "No mínimo 3 caracteris e no máximo 100")
        String name,

        @NotBlank(message = "Campo obrigatorio")
        @Email(message = "Email inválido")
        @Size(max = 100, message = "Email muito grande")
        String email,

        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 6, max = 6, message = "CRM inválido")
        String crm,

        @NotBlank(message = "Campo obrigatorio")
        @Size(min = 11, max = 11, message = "Número inválido")
        String phone,

        DoctorSpecialties specialty,

        @NotNull(message = "Objeto endereço vazio")
        @JsonAlias("address") @Valid CreateAddressDTO createAddressDTO
) {
}
