package com.vollmed.api.exceptions;

public class InvalidCredentialsException extends Exception {
    public InvalidCredentialsException() {
        super("Credenciais inválidas");
    }
}
