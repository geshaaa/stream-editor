package com.company;

import com.company.commands.FileReplace;
import com.company.editors.StreamEditor;
import com.company.operations.FileOperations;

public class Main {

    public static void main(String[] args) {
        FileReplace fileReplace = new FileReplace(new StreamEditor(), new FileOperations());
        fileReplace.proceed(args);
    }
}
