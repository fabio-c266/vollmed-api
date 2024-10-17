package com.vollmed.api.domain.patient;

import com.vollmed.api.domain.address.Address;
import com.vollmed.api.domain.administrator.Administrator;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "patients")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Patient {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;

    @Column(name = "cpf")
    private String CPF;

    private String phone;

    private boolean active;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "fk_address_id")
    private Address address;

    @ManyToOne
    @JoinColumn(name = "fk_administrator_id")
    private Administrator createdBy;

}
