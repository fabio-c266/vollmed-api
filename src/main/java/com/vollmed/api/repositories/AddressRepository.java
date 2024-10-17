package com.vollmed.api.repositories;

import com.vollmed.api.domain.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AddressRepository extends JpaRepository<Address, Long> {
}
