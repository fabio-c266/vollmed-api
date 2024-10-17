package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record CreateAddressDTO(
        @NotBlank(message = "Campo obrigatório")
        @Size(min = 8, max = 8, message = "CEP inválido")
        String cep,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 100, message = "No mínimo 3 caracteris e no máximo 100")
        String street,

        @NotBlank(message = "Campo obrigatório")
        @Size(min = 3, max = 100, message = "No mínimo 3 caracteris e no máximo 100")
        String neighborhood,

        @Size(max = 80, message = "O complemento pode ter no máximo 80 caracteris")
        String complement,

        @Range(min = 1, message = "Precisa ser maior ou igual a 1")
        int number,

        @NotBlank(message = "Campo obrigatório")
        @JsonAlias("city_name") String cityName
        ) {
}
