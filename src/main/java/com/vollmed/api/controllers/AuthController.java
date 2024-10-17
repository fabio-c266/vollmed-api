package com.vollmed.api.controllers;

import com.vollmed.api.dtos.AuthLoginDTO;
import com.vollmed.api.dtos.CreatedTokenResponseDTO;
import com.vollmed.api.services.AuthService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<CreatedTokenResponseDTO> login(@RequestBody @Valid AuthLoginDTO authLoginDTO) throws Exception {
        String token = authService.login(authLoginDTO);

        return ResponseEntity.ok().body(new CreatedTokenResponseDTO(token));
    }
}
