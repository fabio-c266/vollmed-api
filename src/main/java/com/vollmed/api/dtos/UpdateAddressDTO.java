package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record UpdateAddressDTO(
        @Size(min = 8, max = 8, message = "Ccep inválido")
        String cep,

        @Size(min = 3, max = 100, message = "no mínimo 3 caracteris e no máximo 100")
        String street,

        @Size(min = 3, max = 100, message = "no mínimo 3 caracteris e no máximo 100")
        String neighborhood,

        @Size(max = 80, message = "o complemento pode ter no máximo 80 caracteris")
        String complement,

        int number,

        @JsonAlias("city_name") String cityName
) {
}
