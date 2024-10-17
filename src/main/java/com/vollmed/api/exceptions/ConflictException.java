package com.vollmed.api.exceptions;

public class ConflictException extends Exception {
    public ConflictException(String field) {
        super("já possui um registro com esse " + field);
    }
}
