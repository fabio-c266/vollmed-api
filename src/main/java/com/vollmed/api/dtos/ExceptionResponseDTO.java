package com.vollmed.api.dtos;

public record ExceptionResponseDTO(String message)
{
    public ExceptionResponseDTO(Exception e) {
        this(e.getMessage());
    }
}
