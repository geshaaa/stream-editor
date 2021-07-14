package tests.com.company.operations;

import com.company.operations.FileOperations;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;


public class FileOperationsTest {
    private final String FILENAME = "filename.txt";
    private final String FILE_CONTENT = "old";
    private final FileOperations fileOperations = new FileOperations();

    void setUp() throws IOException {
        File myObj = new File(FILENAME);
        if (myObj.createNewFile()) {
            Path path = Path.of(FILENAME);
            Files.write(path, FILE_CONTENT.getBytes());
        }
    }

    void deleteFile() {
        File myObj = new File(FILENAME);
        myObj.delete();
    }

    @Test
    public void getContent() throws IOException {
        setUp();
        assertEquals(FILE_CONTENT, fileOperations.getContent(FILENAME));
        deleteFile();
    }

    @Test
    public void editContent() throws IOException {
        setUp();
        String newFileContent = "new";
        fileOperations.editContent(newFileContent, FILENAME);
        Path fileName = Path.of(FILENAME);
        assertEquals(newFileContent, Files.readString(fileName));
        deleteFile();
    }

}
