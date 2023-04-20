package tests;

import core.FileWriter;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class FileWriterTest {

    private static final String path = "path";

    @Test
    void testWriteToFile() {
        assertDoesNotThrow(() -> FileWriter.writeToFile(path, ""));
    }
}