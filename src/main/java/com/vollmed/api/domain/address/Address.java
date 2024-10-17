package com.vollmed.api.domain.address;

import com.vollmed.api.dtos.CreateAddressDTO;
import com.vollmed.api.dtos.UpdateAddressDTO;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "addresses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "cep")
    private String CEP;

    private String street;
    private String neighborhood;
    private String complement;
    private int number;

    @ManyToOne
    @JoinColumn(name = "fk_city_id")
    private City city;

    public Address(CreateAddressDTO createAddressDTO, City city) {
        this.CEP = createAddressDTO.cep();
        this.street = createAddressDTO.street();
        this.neighborhood = createAddressDTO.neighborhood();
        this.complement = createAddressDTO.complement();
        this.number = createAddressDTO.number();

        this.city = city;
    }

    public void update(UpdateAddressDTO updateAddressDTO) {
        if (updateAddressDTO.cep() != null) this.setCEP(updateAddressDTO.cep());
        if (updateAddressDTO.street() != null) this.setStreet(updateAddressDTO.street());
        if (updateAddressDTO.neighborhood() != null) this.setNeighborhood(updateAddressDTO.neighborhood());
        if (updateAddressDTO.complement() != null) this.setComplement(updateAddressDTO.complement());
        if (updateAddressDTO.number() != this.number) this.setNumber(updateAddressDTO.number());

    }
}
