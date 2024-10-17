package com.vollmed.api.domain.address;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "uf")
@Getter
@NoArgsConstructor
public class UF {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String acronym;
    private String name;
}