package com.vollmed.api.exceptions;

public class DTOEmptyException extends Exception {
    public DTOEmptyException() {
        super("Deve ser informado por o menos um campo");
    }
}
