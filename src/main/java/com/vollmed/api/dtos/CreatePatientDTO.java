package com.vollmed.api.dtos;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreatePatientDTO(
        @NotBlank(message = "campo obrigatorio")
        @Size(min = 3, max = 100, message = "no mínimo 3 caracteris e no máximo 100")
        String name,

        @NotBlank(message = "campo obrigatório")
        @Size(max = 80, message = "muito grande")
        @Email(message = "email inválido")
        String email,

        @NotBlank(message = "campo obrigatório")
        @Size(min = 11, max = 11, message = "cpf inválido")
        String cpf,

        @NotBlank(message = "campo obrigatório")
        @Size(min = 11, max = 11, message = "número inválido")
        String phone,

        @Valid CreateAddressDTO address
) {
}
