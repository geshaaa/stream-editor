package com.company.exceptions;

public class WriteFileException extends RuntimeException {
    public WriteFileException() {
        super("There was a problem with writing in the file");
    }
}
