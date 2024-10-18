package com.vollmed.api.exceptions;

public class ConflictException extends Exception {
    public ConflictException(String field) {
        super("Já possui um registro com esse " + field);
    }
}
