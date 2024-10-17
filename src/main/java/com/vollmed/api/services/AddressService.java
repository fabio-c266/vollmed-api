package com.vollmed.api.services;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.address.City;
import com.vollmed.api.dtos.CreateAddressDTO;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.repositories.AddressRepository;
import com.vollmed.api.repositories.CityRepository;
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
}
