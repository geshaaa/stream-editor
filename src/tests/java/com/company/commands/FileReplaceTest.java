package tests.com.company.commands;

import com.company.commands.FileReplace;
import com.company.editors.EditorInterface;
import com.company.exceptions.InvalidParametersException;
import com.company.operations.OperationsInterface;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.*;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class FileReplaceTest {
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final ByteArrayOutputStream errContent = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;
    private final PrintStream originalErr = System.err;
    private FileReplace fileReplace;

    private final EditorInterface editor = Mockito.mock(EditorInterface.class);

    private final OperationsInterface operations = Mockito.mock(OperationsInterface.class);

    @Before
    public void setUp() {
        this.fileReplace = new FileReplace(editor, operations);
        System.setOut(new PrintStream(outContent));
        System.setErr(new PrintStream(errContent));
    }

    @After
    public void restoreStreams() {
        System.setOut(originalOut);
        System.setErr(originalErr);
    }

    @Test
    public void proceed_WithFileEdit() {
        String fileContent = "old";
        String fileName = "test.txt";
        String newFileContent = "new";

        String[] args = new String[3];
        args[0] = "-i";
        args[1] = "s/old/new/";
        args[2] = fileName;
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("old", "old");
        replaceMap.put("new", "new");

        doReturn(fileContent).when(operations).getContent(fileName);
        doReturn(newFileContent).when(editor).replaceContent(replaceMap, fileContent);
        doNothing().when(operations).editContent(newFileContent, fileName);

        fileReplace.proceed(args);
    }


    @Test
    public void proceed_WithoutFileEdit() {
        String fileContent = "old";
        String fileName = "test.txt";
        String newFileContent = "new";

        String[] args = new String[2];
        args[0] = "s/old/new/";
        args[1] = fileName;
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("old", "old");
        replaceMap.put("new", "new");

        doReturn(fileContent).when(operations).getContent(fileName);
        doReturn(newFileContent).when(editor).replaceContent(replaceMap, fileContent);
        verify(operations, never()).editContent(newFileContent, fileName);

        fileReplace.proceed(args);
        assertEquals("new", outContent.toString().trim());
    }


    @Test(expected = InvalidParametersException.class)
    public void proceed_WrongInput() {
        String fileContent = "old";
        String fileName = "test.txt";
        String newFileContent = "new";

        String[] args = new String[2];
        args[0] = "s/old";
        args[1] = fileName;
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("old", "old");
        replaceMap.put("new", "new");

        doReturn(fileContent).when(operations).getContent(fileName);
        verify(operations, never()).editContent(newFileContent, fileName);
        verify(editor, never()).replaceContent(replaceMap, fileContent);

        fileReplace.proceed(args);
    }

    @Test(expected = InvalidParametersException.class)
    public void proceed_LessParams() {
        String fileContent = "old";
        String fileName = "test.txt";
        String newFileContent = "new";

        String[] args = new String[1];
        args[0] = "s/old";
        Map<String, String> replaceMap = new HashMap<>();
        replaceMap.put("old", "old");
        replaceMap.put("new", "new");

        doReturn(fileContent).when(operations).getContent(fileName);
        verify(operations, never()).editContent(newFileContent, fileName);
        verify(editor, never()).replaceContent(replaceMap, fileContent);

        fileReplace.proceed(args);
    }
}
