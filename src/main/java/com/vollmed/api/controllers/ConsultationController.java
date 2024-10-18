package com.vollmed.api.controllers;

import com.vollmed.api.domain.consultation.Consultation;
import com.vollmed.api.dtos.ConsultationResponseDTO;
import com.vollmed.api.dtos.CreateConsultationDTO;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.exceptions.ValidationServiceException;
import com.vollmed.api.services.ConsultationService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/consultation")
@SecurityRequirement(name = "bearer-key")
public class ConsultationController {
    @Autowired
    private ConsultationService consultationService;

    @PostMapping
    public ResponseEntity<ConsultationResponseDTO> create(@RequestBody @Valid CreateConsultationDTO createConsultationDTO) throws ValidationServiceException, EntityNotFoundException {
        Consultation newConsultation = this.consultationService.schedule(createConsultationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(new ConsultationResponseDTO(newConsultation));
    }

    @GetMapping("/all")
    public ResponseEntity<Page<ConsultationResponseDTO>> getAll(@PageableDefault(size = 10, sort = {"status", "createdAt"}) Pageable pageable) {
        Page<Consultation> consultationsPage = this.consultationService.getAll(pageable);
        Page<ConsultationResponseDTO> formatted = consultationsPage.map(ConsultationResponseDTO::new);

        return ResponseEntity.ok(formatted);
    }

    @GetMapping("/cancel/{consultationId}")
    public ResponseEntity<Void> cancel(@PathVariable Long consultationId) throws EntityNotFoundException {
        this.consultationService.cancel(consultationId);
        return ResponseEntity.ok().build();
    }
}
