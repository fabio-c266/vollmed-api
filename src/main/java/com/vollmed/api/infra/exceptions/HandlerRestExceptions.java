package com.vollmed.api.infra.exceptions;
import com.vollmed.api.dtos.ExceptionResponseDTO;
import com.vollmed.api.dtos.InvalidFieldValueDTO;
import com.vollmed.api.exceptions.InvalidCredentialsException;
import com.vollmed.api.exceptions.ValidationServiceException;
import jakarta.validation.ValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandlerRestExceptions {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity<ExceptionResponseDTO> HandleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                .body(new ExceptionResponseDTO(e));
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ExceptionResponseDTO> HandlerHttpMessageNotReadableException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO("Body inválido"));
    }

    @ExceptionHandler(MissingPathVariableException.class)
    public ResponseEntity<ExceptionResponseDTO> HandlerMissingPathVariableException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO("Faltando parametros na URI"));
    }

    @ExceptionHandler(ValidationServiceException.class)
    public ResponseEntity<ExceptionResponseDTO> HandlerValidationServiceException(Exception e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ExceptionResponseDTO(e));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ExceptionResponseDTO> HandleValidationsExceptions(ValidationException e) {
        return ResponseEntity.badRequest().body(new ExceptionResponseDTO(e));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<List<InvalidFieldValueDTO>> handle400(MethodArgumentNotValidException except) {
        List<FieldError> errors = except.getFieldErrors();
        List<InvalidFieldValueDTO> errorsFormatted = errors.stream()
                .map(e -> new InvalidFieldValueDTO(e.getField(), e.getDefaultMessage())).toList();

        return ResponseEntity.badRequest().body(errorsFormatted);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ExceptionResponseDTO> handlerErrors(Exception e) {
        System.out.println(e.getMessage());

        return ResponseEntity.badRequest()
                .body(new ExceptionResponseDTO("Infelizmente ocorreu um erro durante a requisição, tente novamente mais tarde"));
    }
}
