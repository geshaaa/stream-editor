package com.company.exceptions;

public class InvalidParametersException extends RuntimeException {
    public InvalidParametersException(String message) {
        super(message);
    }
}
