package com.company.commands;

import com.company.editors.EditorInterface;
import com.company.exceptions.InvalidParametersException;
import com.company.operations.OperationsInterface;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class FileReplace {
    private static final String IN_PLACE_FLAG = "-i";
    private final ArrayList<String> filters;

    private final EditorInterface editor;
    private final OperationsInterface operations;

    public FileReplace(EditorInterface editor, OperationsInterface operationsInterface) {
        this.editor = editor;
        this.operations = operationsInterface;

        filters = new ArrayList<>();
        filters.add(IN_PLACE_FLAG);
    }

    public void proceed(String[] args) {
        List<String> commandFilters = Arrays.stream(args)
                .filter(filters::contains)
                .collect(Collectors.toList());

        List<String> commandParams = Arrays.stream(args)
                .filter(a -> !filters.contains(a))
                .collect(Collectors.toList());

        if (commandParams.size() != 2) {
            throw new InvalidParametersException("Invalid parameters");
        }

        String result = getAndReplaceFileContent(commandParams.get(0), commandParams.get(1));

        proceedAfterTransform(result, commandFilters, commandParams);
    }

    private void proceedAfterTransform(String result, List<String> commandFilters, List<String> commandParams) {
        if (commandFilters.contains(IN_PLACE_FLAG)) {
            operations.editContent(result, commandParams.get(1));
            System.out.println("Replace done!");
        } else {
            System.out.println(result);
        }
    }

    private String getAndReplaceFileContent(String expression, String file) {
        String fileContent = operations.getContent(file);

        Map<String, String> replaceParams = getReplaceParams(expression);

        return editor.replaceContent(replaceParams, fileContent);
    }

    private Map<String, String> getReplaceParams(String expression) {
        Pattern pattern = Pattern.compile("s/(?<old>[^/]+)/(?<new>[^/]+)/");
        Matcher matcher = pattern.matcher(expression);

        Map<String, String> result = new HashMap<>();
        if (matcher.matches()) {
            result.put("old", matcher.group("old"));
            result.put("new", matcher.group("new"));
        } else {
            throw new InvalidParametersException("Invalid expression");
        }

        return result;
    }
}
