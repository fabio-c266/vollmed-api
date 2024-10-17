package com.vollmed.api.services;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.address.City;
import com.vollmed.api.dtos.CreateAddressDTO;
import com.vollmed.api.dtos.UpdateAddressDTO;
import com.vollmed.api.exceptions.DTOEmptyException;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.repositories.AddressRepository;
import com.vollmed.api.repositories.CityRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    private CityRepository cityRepository;

    @Autowired
    private AddressRepository addressRepository;

    public City getCityByName(String name) throws EntityNotFoundException {
        return cityRepository.findByName(name).orElseThrow(() -> new EntityNotFoundException("City"));
    }

    public Address create(CreateAddressDTO createAddressDTO) throws EntityNotFoundException {
        String cityName = createAddressDTO.cityName();
        City city = this.getCityByName(cityName);

        Address newAddress = new Address(createAddressDTO, city);
        return this.addressRepository.save(newAddress);
    }

    public Address getById(Long id) throws EntityNotFoundException {
        return this.addressRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Address"));
    }

    @Transactional
    public Address update(Long id, UpdateAddressDTO updateAddressDTO) throws EntityNotFoundException, DTOEmptyException {
        Address address = this.getById(id);

        if (updateAddressDTO == null) throw new DTOEmptyException();
        if (updateAddressDTO.cep() != null) address.setCEP(updateAddressDTO.cep());
        if (updateAddressDTO.street() != null) address.setStreet(updateAddressDTO.street());
        if (updateAddressDTO.neighborhood() != null) address.setNeighborhood(updateAddressDTO.neighborhood());
        if (updateAddressDTO.complement() != null) address.setComplement(updateAddressDTO.complement());
        if (updateAddressDTO.number()  > 0) address.setNumber(updateAddressDTO.number());

        if (updateAddressDTO.cityName() != null) {
            City city = this.getCityByName(updateAddressDTO.cityName());
            address.setCity(city);
        }

        return address;
    }
}
