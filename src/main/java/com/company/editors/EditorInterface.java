package com.company.editors;

import java.util.Map;

public interface EditorInterface {

    String replaceContent(Map<String, String> replaceParams, String fileContent);
}
