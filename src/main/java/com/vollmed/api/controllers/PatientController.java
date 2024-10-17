package com.vollmed.api.controllers;

import com.vollmed.api.domain.patient.Patient;
import com.vollmed.api.dtos.CreatePatientDTO;
import com.vollmed.api.dtos.PatientResponseDTO;
import com.vollmed.api.dtos.UpdatePatientDTO;
import com.vollmed.api.exceptions.ConflictException;
import com.vollmed.api.exceptions.DTOEmptyException;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.services.PatientService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/patient")
public class PatientController {
    @Autowired
    private PatientService patientService;

    @PostMapping
    public ResponseEntity<PatientResponseDTO> create(@RequestBody @Valid CreatePatientDTO createPatientDTO) throws ConflictException, EntityNotFoundException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        String administratorEmail = auth.getName();

        Patient newPatient = this.patientService.create(administratorEmail, createPatientDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new PatientResponseDTO(newPatient));
    }

    @GetMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> get(@PathVariable Long patientId) throws EntityNotFoundException {
        Patient patient = this.patientService.getById(patientId);
        return ResponseEntity.status(HttpStatus.OK).body(new PatientResponseDTO(patient));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<PatientResponseDTO>> listDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        Page<Patient> patientsPage = this.patientService.getAll(pageable);
        Page<PatientResponseDTO> formatted = patientsPage.map(PatientResponseDTO::new);

        return ResponseEntity.ok().body(formatted);
    }

    @PutMapping("/{patientId}")
    public ResponseEntity<PatientResponseDTO> update(@PathVariable Long patientId, @RequestBody @Valid UpdatePatientDTO updatePatientDTO) throws DTOEmptyException, ConflictException, EntityNotFoundException {
        Patient patientUpdated = this.patientService.update(patientId, updatePatientDTO);

        return ResponseEntity.status(HttpStatus.OK).body(new PatientResponseDTO(patientUpdated));
    }

    @GetMapping("/toggle/{patientId}")
    public ResponseEntity toggleIsActive(@PathVariable Long patientId) throws EntityNotFoundException {
        this.patientService.toggleIsActive(patientId);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
