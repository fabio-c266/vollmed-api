package com.vollmed.api.services;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.address.City;
import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.dtos.CreateDoctorDTO;
import com.vollmed.api.dtos.UpdateDoctorDTO;
import com.vollmed.api.exceptions.ConflictException;
import com.vollmed.api.exceptions.DTOEmptyException;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AdministratorService administratorService;

    public Doctor create(CreateDoctorDTO createDoctorDTO, String administratorEmail) throws ConflictException, EntityNotFoundException {
        if (this.doctorRepository.findByEmail(createDoctorDTO.email()).isPresent()) {
            throw new ConflictException("email");
        }

        if (this.doctorRepository.findByCrm(createDoctorDTO.crm()).isPresent()) {
            throw new ConflictException("CRM");
        }

        if (this.doctorRepository.findByPhone(createDoctorDTO.phone()).isPresent()) {
            throw new ConflictException("phone");
        }

        Administrator administrator = this.administratorService.getAdministratorByEmail(administratorEmail);
        String cityName = createDoctorDTO.createAddressDTO().cityName();
        City city = addressService.getCityByName(cityName);

        Address newAddress = this.addressService.create(createDoctorDTO.createAddressDTO());

        Doctor newDoctor = new Doctor(
                null,
                createDoctorDTO.name(),
                createDoctorDTO.email(),
                createDoctorDTO.crm(),
                createDoctorDTO.phone(),
                createDoctorDTO.specialty(),
                true,
                null,
                newAddress,
                administrator
                );

        return this.doctorRepository.save(newDoctor);
    }

    public Doctor getById(Long id) throws EntityNotFoundException {
        return this.doctorRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Doctor"));
    }

    public Doctor getByEmail(String email) throws EntityNotFoundException {
        return this.doctorRepository.findByEmail(email).orElseThrow(() -> new EntityNotFoundException("Doctor"));
    }

    public Doctor getByPhone(String phone) throws EntityNotFoundException {
        return this.doctorRepository.findByPhone(phone).orElseThrow(() -> new EntityNotFoundException("Doctor"));
    }

    public void isValidId(Long id) throws EntityNotFoundException {
        if (!this.doctorRepository.existsById(id)) {
            throw new EntityNotFoundException("Doctor");
        }
    }

    @Transactional
    public Doctor update(Long id, UpdateDoctorDTO updateDoctorDTO) throws DTOEmptyException, ConflictException, EntityNotFoundException {
        this.isValidId(id);
        Doctor target = this.doctorRepository.getReferenceById(id);

        if (updateDoctorDTO == null) throw new DTOEmptyException();
        if (updateDoctorDTO.name() != null) target.setName(updateDoctorDTO.name());

        if (updateDoctorDTO.email() != null) {
            if (this.getByEmail(updateDoctorDTO.email()) != null) {
                throw new ConflictException("Email");
            }

            target.setEmail(updateDoctorDTO.email());
        }

        if (updateDoctorDTO.phone() != null) {
            if (this.getByPhone(updateDoctorDTO.phone()) != null) {
                throw new ConflictException("Phone");
            }

            target.setPhone(updateDoctorDTO.phone());
        }

        if (updateDoctorDTO.updateAddressDTO() != null && updateDoctorDTO.updateAddressDTO().cityName() != null) {
            City city = this.addressService.getCityByName(updateDoctorDTO.updateAddressDTO().cityName());

            target.getAddress().setCity(city);
        }

        return target;
    }

    @Transactional
    public void toggleIsActive(Long id) throws EntityNotFoundException {
        this.isValidId(id);
        Doctor doctor = this.doctorRepository.getReferenceById(id);

        doctor.setActive(!doctor.isActive());
    }

    public Page<Doctor> getAll(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }
}
