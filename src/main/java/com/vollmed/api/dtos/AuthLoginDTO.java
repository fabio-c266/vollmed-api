package com.vollmed.api.dtos;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record AuthLoginDTO(
        @Email(message = "Email inválido")
        String email,

        @NotBlank(message = "Campo vazio")
        @Size(min = 8, message = "A senha deve ter no mínimo 8 caracteris")
        @Size(max = 20, message = "A senha deve ter no máximo 20 caracteris")
        String password
) {}
