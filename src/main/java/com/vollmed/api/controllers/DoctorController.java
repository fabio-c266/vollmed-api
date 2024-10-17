package com.vollmed.api.controllers;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.dtos.CreateDoctorDTO;
import com.vollmed.api.dtos.DoctorResponseDTO;
import com.vollmed.api.dtos.UpdateDoctorDTO;
import com.vollmed.api.exceptions.ConflictException;
import com.vollmed.api.exceptions.DTOEmptyException;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.services.DoctorService;
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
@RequestMapping("/doctor")
public class DoctorController {
    @Autowired
    private DoctorService doctorService;

    @PostMapping
    public ResponseEntity<DoctorResponseDTO> create(@RequestBody @Valid CreateDoctorDTO createDoctorDTO) throws EntityNotFoundException, ConflictException {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Doctor doctorCreated = this.doctorService.create(createDoctorDTO, auth.getName());
        Address doctorAddress = doctorCreated.getAddress();
        DoctorResponseDTO doctorResponseDTO = new DoctorResponseDTO(doctorCreated, doctorAddress);

        return ResponseEntity.status(HttpStatus.CREATED).body(doctorResponseDTO);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> get(@PathVariable Long id) throws EntityNotFoundException {
        Doctor doctor = this.doctorService.getById(id);

        return ResponseEntity.status(HttpStatus.OK).body(new DoctorResponseDTO(doctor, doctor.getAddress()));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<DoctorResponseDTO>> listDoctors(@PageableDefault(size = 10, sort = {"name"}) Pageable pageable) {
        Page<Doctor> doctorsPage = this.doctorService.getAll(pageable);
        Page<DoctorResponseDTO> formatted = doctorsPage.map(doctor -> new DoctorResponseDTO(doctor, doctor.getAddress()));

        return ResponseEntity.ok().body(formatted);
    }

    @GetMapping("/toggle/{id}")
    public ResponseEntity toggleIsActive(@PathVariable Long id) throws EntityNotFoundException {
        this.doctorService.toggleIsActive(id);

        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorResponseDTO> alter(@PathVariable Long id, @RequestBody @Valid UpdateDoctorDTO updateDoctorDTO) throws EntityNotFoundException, ConflictException, DTOEmptyException {
        Doctor DoctorUpdated = this.doctorService.update(id, updateDoctorDTO);
        return ResponseEntity.status(HttpStatus.OK).body(new DoctorResponseDTO(DoctorUpdated, DoctorUpdated.getAddress()));
    }
}
