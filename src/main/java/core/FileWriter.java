package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;

public class FileWriter {

    public static void writeToFile(String path, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new java.io.FileWriter(new File(path)));
            bw.write(text);
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
