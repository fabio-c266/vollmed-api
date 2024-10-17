package com.vollmed.api.dtos;

public record AddressResponseDTO(
        Long id,
        String cep,
        String street,
        String neighborhood,
        String complement,
        int number,
        String city,
        String uf
) {}
