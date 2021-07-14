package tests.com.company.editors;

import com.company.editors.StreamEditor;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;

public class StreamEditorTest {

    private final StreamEditor streamEditor = new StreamEditor();

    @Test
    public void replaceContent() {
        String fileContent = "old";
        String newFileContent = "new";

        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("old", "old");
        replaceMap.put("new", "new");

        assertEquals(newFileContent, streamEditor.replaceContent(replaceMap, fileContent));
    }

}
