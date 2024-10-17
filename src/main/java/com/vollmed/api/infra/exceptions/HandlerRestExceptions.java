package com.vollmed.api.infra.exceptions;
import com.vollmed.api.exceptions.InvalidCredentialsException;
import jakarta.validation.ValidationException;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class HandlerRestExceptions {
    @ExceptionHandler(InvalidCredentialsException.class)
    public ResponseEntity HandleInvalidCredentials(InvalidCredentialsException e) {
        return ResponseEntity.status(401).body(new ExceptionsResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity HandleValidationsExceptions(ValidationException e) {
        return ResponseEntity.badRequest().body(new ExceptionsResponseDTO(e.getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity handle400(MethodArgumentNotValidException e) {
        List<FieldError> errors = e.getFieldErrors();
        List<invalidFieldDTO> invalidFieldsDTO = errors.stream().map(invalidFieldDTO::new).toList();

        return ResponseEntity.badRequest().body(invalidFieldsDTO);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity HandleGenericException(Exception e) {
        return ResponseEntity.badRequest().body(new ExceptionsResponseDTO(e.getMessage()));
    }

    private record invalidFieldDTO(String field, String message) {
        public invalidFieldDTO(FieldError fieldError) {
            this(fieldError.getField(), fieldError.getDefaultMessage());
        }
    }

    private record ExceptionsResponseDTO(String message) {}
}
