package com.vollmed.api.services;

import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.dtos.AuthLoginDTO;
import com.vollmed.api.exceptions.InvalidCredentialsException;
import com.vollmed.api.infra.security.TokenService;
import com.vollmed.api.repositories.AdministratorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    @Autowired
    private AdministratorRepository administratorRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    public String login(AuthLoginDTO authLoginDTO) throws Exception {
        Administrator adm = this.administratorRepository.findByEmail(authLoginDTO.email())
                .orElseThrow(InvalidCredentialsException::new);

        if (!passwordEncoder.matches(authLoginDTO.password(), adm.getPassword())) {
            throw new InvalidCredentialsException();
        }

        return tokenService.generateToken(adm);
    }
}
