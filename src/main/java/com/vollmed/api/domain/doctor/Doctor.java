package com.vollmed.api.domain.doctor;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.address.City;
import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.dtos.CreateDoctorDTO;
import com.vollmed.api.dtos.UpdateDoctorDTO;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "doctors")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Doctor {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String crm;
    private String phone;

    @Enumerated(EnumType.STRING)
    private DoctorSpecialties specialty;

    private boolean active;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "fk_address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "fk_administrator_id")
    private Administrator createdBy;
}
