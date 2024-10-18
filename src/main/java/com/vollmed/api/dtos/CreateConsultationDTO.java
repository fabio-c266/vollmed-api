package com.vollmed.api.dtos;

import com.fasterxml.jackson.annotation.JsonAlias;
import com.vollmed.api.domain.doctor.DoctorSpecialties;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotNull;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

public record CreateConsultationDTO(
        @JsonAlias("doctor_id") Long doctorId,

        @NotNull(message = "campo vazio")
        @JsonAlias("patient_id") Long patientId,

        DoctorSpecialties specialty,

        @NotNull(message = "campo vazio")
        @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
        @Future(message = "A data precisa está no futuro")
        @JsonAlias("consultation_date") LocalDateTime consultationDate
) {
}
