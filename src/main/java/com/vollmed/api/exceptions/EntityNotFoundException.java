package com.vollmed.api.exceptions;

public class EntityNotFoundException extends Exception {
    public EntityNotFoundException(String entityName) {
        super(entityName + " não encontrado");
    }
}
