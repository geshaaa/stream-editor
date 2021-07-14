package com.company.operations;

import com.company.exceptions.ReadFileException;
import com.company.exceptions.WriteFileException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileOperations implements OperationsInterface {
    public String getContent(String file) {
        Path fileName = Path.of(file);
        String result;
        try {
            result = Files.readString(fileName);
        } catch (IOException e) {
            throw new ReadFileException();
        }
        return result;
    }

    public void editContent(String result, String file) {
        Path path = Path.of(file);
        try {
            Files.write(path, result.getBytes());
        } catch (IOException exception) {
            throw new WriteFileException();
        }
    }
}
