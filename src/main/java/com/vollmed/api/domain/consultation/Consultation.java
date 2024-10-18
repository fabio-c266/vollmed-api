package com.vollmed.api.domain.consultation;

import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.domain.patient.Patient;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "consultations")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Consultation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "fk_doctor_id")
    private Doctor doctor;

    @ManyToOne
    @JoinColumn(name = "fk_patient_id")
    private Patient patient;

    @Column(name = "consultation_date")
    private LocalDateTime consultationDate;

    @Enumerated(EnumType.STRING)
    private ConsultationStatus status;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
