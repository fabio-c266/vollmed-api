package com.vollmed.api.services;

import com.vollmed.api.domain.consultation.Consultation;
import com.vollmed.api.domain.consultation.ConsultationStatus;
import com.vollmed.api.domain.doctor.Doctor;
import com.vollmed.api.domain.patient.Patient;
import com.vollmed.api.dtos.CreateConsultationDTO;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.exceptions.ValidationServiceException;
import com.vollmed.api.repositories.ConsultationRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Service
public class ConsultationService {
    @Autowired
    private ConsultationRepository consultationRepository;

    @Autowired
    private PatientService patientService;

    @Autowired
    private DoctorService doctorService;

    public Consultation schedule(CreateConsultationDTO createConsultationDTO) throws EntityNotFoundException, ValidationServiceException {
        Patient patient = this.patientService.getById(createConsultationDTO.patientId());

        if (!patient.isActive()) {
            throw new ValidationServiceException("O paciente precisa está ativo no sistema para marcar uma consulta");
        }

        if (this.consultationRepository.findPatientConsultationInSameDay(
                patient.getId(),
                LocalDate.from(createConsultationDTO.consultationDate()
                )).isPresent()) {
            throw new ValidationServiceException("O paciente só pode marcar uma consulta por dia");
        }

        Doctor doctor = this.handleDoctor(createConsultationDTO);

        LocalDateTime consultationDate = createConsultationDTO.consultationDate();
        LocalDateTime currentDate = LocalDateTime.now();

        boolean isSunday = consultationDate.getDayOfWeek().equals(DayOfWeek.SUNDAY);
        boolean isBeforeHours = consultationDate.getHour() < 7;
        boolean isAfterHours = consultationDate.getHour() > 18;

        if (isSunday | isBeforeHours | isAfterHours) {
            throw new ValidationServiceException("Fora do horário de funcionamento da clínica");
        }

        if (currentDate.plusMinutes(30).isAfter(consultationDate)) {
            throw new ValidationServiceException("As consultas devem ser marcadas com 30 minutos de antecedência");
        }

        Consultation newConsultation = new Consultation(
                null,
                doctor,
                patient,
                consultationDate,
                ConsultationStatus.AGENDADA,
                null
        );

        return this.consultationRepository.save(newConsultation);
    }

    public Page<Consultation> getAll(Pageable pageable) {
        return this.consultationRepository.findAll(pageable);
    }

    public Consultation getById(Long id) throws EntityNotFoundException {
        return this.consultationRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Consulta"));
    }

    @Transactional
    public void cancel(Long id) throws EntityNotFoundException {
        Consultation consultation = this.getById(id);
        consultation.setStatus(ConsultationStatus.CANCELADA);
    }

    private Doctor handleDoctor(CreateConsultationDTO createConsultationDTO) throws EntityNotFoundException, ValidationServiceException {
        if (createConsultationDTO.doctorId() != null) {
            Doctor doctor = this.doctorService.getById(createConsultationDTO.doctorId());

            if (!doctor.isActive()) {
                throw new ValidationServiceException("O médico precisa está ativo no sistema para fazer uma consulta");
            }

            if (this.consultationRepository.findDoctorConsultationInSameHour(
                    doctor.getId(),
                    createConsultationDTO.consultationDate()
            ).isPresent()) {
                throw new ValidationServiceException("Esse doutor já possui uma consulta marcada nesse horário");
            }

            return doctor;
        }

        if (createConsultationDTO.specialty() == null) {
            throw new ValidationServiceException("É necessário informar a especialidade caso não insira um doutor");
        }

        return this.doctorService.getRandomDoctorBySpecialty(
                createConsultationDTO.specialty(),
                createConsultationDTO.consultationDate()
        );
    }
}
