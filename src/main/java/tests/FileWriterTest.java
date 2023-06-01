package tests;

import io.FileWriter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    private static final String PATH = "path";

    @Test
    void testWriteToFile() {
        assertDoesNotThrow(() -> FileWriter.writeToFile(PATH, ""));
    }
}