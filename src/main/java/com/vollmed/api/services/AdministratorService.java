package com.vollmed.api.services;

import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.exceptions.EntityNotFoundException;
import com.vollmed.api.repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdministratorService {
    @Autowired
    private AdministratorRepository administratorRepository;

    public Administrator getAdministratorByEmail(String email) throws EntityNotFoundException {
        return this.administratorRepository.findByEmail(email)
                .orElseThrow(() -> new EntityNotFoundException("Administrator"));
    }
}
