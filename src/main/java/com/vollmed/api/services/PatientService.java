package com.vollmed.api.services;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.domain.patient.Patient;
import com.vollmed.api.dtos.CreatePatientDTO;
import com.vollmed.api.dtos.UpdatePatientDTO;
import com.vollmed.api.exceptions.ConflictException;
import com.vollmed.api.exceptions.DTOEmptyException;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.repositories.PatientRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class PatientService {
    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private AddressService addressService;

    @Autowired
    private AdministratorService administratorService;

    public Patient create(String administratorEmail, CreatePatientDTO createPatientDTO) throws ConflictException, EntityNotFoundException {
        Administrator createdBy = this.administratorService.getByEmail(administratorEmail);

        if (this.patientRepository.findByEmail(createPatientDTO.email()).isPresent()){
            throw new ConflictException("Email");
        }

        if (this.patientRepository.findByCpf(createPatientDTO.cpf()).isPresent()){
            throw new ConflictException("CPF");
        }

        if (this.patientRepository.findByPhone(createPatientDTO.phone()).isPresent()){
            throw new ConflictException("Phone");
        }

        Address newAddress = this.addressService.create(createPatientDTO.address());
        Patient newPatient = new Patient(
                null,
                createPatientDTO.name(),
                createPatientDTO.email(),
                createPatientDTO.cpf(),
                createPatientDTO.phone(),
                true,
                null,
                newAddress,
                createdBy
        );

        return this.patientRepository.save(newPatient);
    }

    public Patient getById(Long id) throws EntityNotFoundException {
        return this.patientRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Patient"));
    }

    public Page<Patient> getAll(Pageable pageable) {
        return this.patientRepository.findAll(pageable);
    }

    @Transactional
    public Patient update(Long id, UpdatePatientDTO updatePatientDTO) throws EntityNotFoundException, DTOEmptyException, ConflictException {
        Patient patient = this.getById(id);

        if (updatePatientDTO == null) {
            throw new DTOEmptyException();
        }

        if (updatePatientDTO.name() != null) patient.setName(updatePatientDTO.name());
        if (updatePatientDTO.email() != null) {
            if (this.patientRepository.findByEmail(updatePatientDTO.email()).isPresent()) {
                throw new ConflictException("email");
            }

            patient.setEmail(updatePatientDTO.email());
        }

        if (updatePatientDTO.phone() != null) {
            if (this.patientRepository.findByPhone(updatePatientDTO.phone()).isPresent()) {
                throw new ConflictException("phone");
            }

            patient.setPhone(updatePatientDTO.phone());
        }

        if (updatePatientDTO.updateAddressDTO() != null) {
            Address addressUpdated = this.addressService.update(patient.getAddress().getId(), updatePatientDTO.updateAddressDTO());

            if (!patient.getAddress().equals(addressUpdated)) patient.setAddress(addressUpdated);
        }

        return patient;
    }

    @Transactional
    public void toggleIsActive(Long id) throws EntityNotFoundException {
        Patient patient = this.getById(id);
        patient.setActive(!patient.isActive());
    }
}
