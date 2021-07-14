package com.company.editors;

import java.util.Map;

public class StreamEditor implements EditorInterface {

    @Override
    public String replaceContent(Map<String, String> replaceParams, String fileContent) {
        return fileContent.replaceAll(replaceParams.get("old"), replaceParams.get("new"));
    }
}
