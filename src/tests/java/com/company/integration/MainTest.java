package tests.com.company.integration;

import com.company.Main;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.Assert.assertEquals;

public class MainTest {

    private final String FILENAME = "filename.txt";
    private final String FILE_CONTENT = "old";

    public void setUp() throws IOException {
        File myObj = new File(FILENAME);
        if (myObj.createNewFile()) {
            Path path = Path.of(FILENAME);
            Files.write(path, FILE_CONTENT.getBytes());
        }
    }

    public void deleteFile() {
        File myObj = new File(FILENAME);
        myObj.delete();
    }

    @Test
    public void happyPath() throws IOException {
        setUp();
        String newFileContent = "new";

        String[] args = new String[3];
        args[0] = "-i";
        args[1] = "s/" + FILE_CONTENT + "/" + newFileContent +"/";
        args[2] = FILENAME;
        Main.main(args);

        Path fileName = Path.of(FILENAME);
        assertEquals(newFileContent, Files.readString(fileName));
        deleteFile();
    }
}
