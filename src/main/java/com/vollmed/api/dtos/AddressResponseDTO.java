package com.vollmed.api.dtos;

import com.vollmed.api.domain.address.Address;

public record AddressResponseDTO(
        Long id,
        String cep,
        String street,
        String neighborhood,
        String complement,
        int number,
        String city,
        String uf
) {
    public AddressResponseDTO(Address address) {
        this(
                address.getId(),
                address.getCEP(),
                address.getStreet(),
                address.getNeighborhood(),
                address.getComplement(),
                address.getNumber(),
                address.getCity().getName(),
                address.getCity().getUf().getName()
        );
    }
}
