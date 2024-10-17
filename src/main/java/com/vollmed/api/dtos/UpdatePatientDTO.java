package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record UpdatePatientDTO(
        @Size(min = 3, max = 100, message = "no mínimo 3 caracteris e no máximo 100")
        String name,

        @Size(max = 80, message = "muito grande")
        @Email(message = "email inválido")
        String email,

        @NotBlank(message = "campo obrigatório")
        @Size(min = 11, max = 11, message = "número inválido")
        String phone,

        @JsonAlias("address") @Valid UpdateAddressDTO updateAddressDTO
) {
}
