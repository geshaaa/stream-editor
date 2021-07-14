package com.company.exceptions;

public class ReadFileException extends RuntimeException {
    public ReadFileException() {
        super("There was a problem with reading the file");
    }
}
