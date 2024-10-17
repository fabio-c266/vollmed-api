package com.vollmed.api.infra.security;

import com.vollmed.api.domain.administrator.Administrator;
import com.vollmed.api.repositories.AdministratorRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;

    @Autowired
    private AdministratorRepository administratorRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = this.getToken(request);
        String email = tokenService.validateToken(token);

        if(email != null){
            Administrator adm = administratorRepository.findByEmail(email).orElseThrow(() -> new RuntimeException("Administrator not found"));

            var authentication = new UsernamePasswordAuthenticationToken(adm, null, List.of());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }

        filterChain.doFilter(request, response);
    }

    private String getToken(HttpServletRequest httpRequest) {
        String authHeader = httpRequest.getHeader("Authorization");
        if(authHeader == null) return null;

        return authHeader.replace("Bearer ", "");
    }
}
