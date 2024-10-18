package com.vollmed.api.services;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.domain.doctor.DoctorSpecialties;
import com.vollmed.api.dtos.CreateDoctorDTO;
import com.vollmed.api.dtos.UpdateDoctorDTO;
import com.vollmed.api.exceptions.ConflictException;
import com.vollmed.api.exceptions.DTOEmptyException;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.exceptions.ValidationServiceException;
import com.vollmed.api.repositories.DoctorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class DoctorService {
    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AdministratorService administratorService;

    public Doctor create(String administratorEmail, CreateDoctorDTO createDoctorDTO) throws ConflictException, EntityNotFoundException {
        Administrator administrator = this.administratorService.getByEmail(administratorEmail);

        if (this.doctorRepository.findByEmail(createDoctorDTO.email()).isPresent()) {
            throw new ConflictException("email");
        }

        if (this.doctorRepository.findByCrm(createDoctorDTO.crm()).isPresent()) {
            throw new ConflictException("CRM");
        }

        if (this.doctorRepository.findByPhone(createDoctorDTO.phone()).isPresent()) {
            throw new ConflictException("phone");
        }

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
        return this.doctorRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Doctor"));
    }

    @Transactional
    public Doctor update(Long id, UpdateDoctorDTO updateDoctorDTO) throws DTOEmptyException, ConflictException, EntityNotFoundException {
        Doctor doctor = this.getById(id);

        if (updateDoctorDTO == null) throw new DTOEmptyException();
        if (updateDoctorDTO.name() != null) doctor.setName(updateDoctorDTO.name());

        if (updateDoctorDTO.email() != null) {
            if (this.doctorRepository.findByEmail(updateDoctorDTO.email()).isPresent()) {
                throw new ConflictException("email");
            }

            doctor.setEmail(updateDoctorDTO.email());
        }

        if (updateDoctorDTO.phone() != null) {
            if (this.doctorRepository.findByPhone(updateDoctorDTO.phone()).isPresent()) {
                throw new ConflictException("phone");
            }

            doctor.setPhone(updateDoctorDTO.phone());
        }

        if (updateDoctorDTO.updateAddressDTO() != null) {
            Address addressUpdated = this.addressService.update(doctor.getAddress().getId(), updateDoctorDTO.updateAddressDTO());

            if (!doctor.getAddress().equals(addressUpdated)) doctor.setAddress(addressUpdated);
        }

        return doctor;
    }

    @Transactional
    public void toggleIsActive(Long id) throws EntityNotFoundException {
        Doctor doctor = this.getById(id);
        doctor.setActive(!doctor.isActive());
    }

    public Doctor getRandomDoctorBySpecialty(DoctorSpecialties specialty, LocalDateTime consultationDate) throws EntityNotFoundException, ValidationServiceException {
        return this.doctorRepository.findRandomDoctorBySpecialty(specialty, consultationDate)
                .orElseThrow(() -> new ValidationServiceException("Nenhum médico disponível nessa data"));
    }

    public Page<Doctor> getAll(Pageable pageable) {
        return doctorRepository.findAll(pageable);
    }
}
