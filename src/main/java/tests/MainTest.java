package tests;

import core.Main;
import org.junit.jupiter.api.Test;

import java.io.*;

import static org.junit.jupiter.api.Assertions.*;

class MainTest {

    PrintStream oldOut;
    InputStream oldIn;
    LogStream loggedOutput;

    @Test
    void promptInput() {
        setUpPrompt();

        Main.main(null);

        cleanUpPrompt();

        assertEquals("URL: (Make sure to insert the WHOLE url)",loggedOutput.getOutputLog().get(0));
        assertEquals("Depth: (Greater than 2 takes very long)",loggedOutput.getOutputLog().get(1));
        assertEquals("Language: ",loggedOutput.getOutputLog().get(2));
        assertEquals("Output Path and file name: (Relative. for example: result.md)",loggedOutput.getOutputLog().get(3));
    }

    private void setUpPrompt(){
        InputStream inputPromptURL = new ByteArrayInputStream(("https://orf.at/" + System.lineSeparator() + "0" + System.lineSeparator() + "English" + System.lineSeparator() + "outTest2.md").getBytes());
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        loggedOutput = new LogStream(out);

        oldOut = System.out;
        oldIn = System.in;
        System.setIn(inputPromptURL);
        System.setOut(loggedOutput);
    }

    private void cleanUpPrompt(){
        System.setOut(oldOut);
        System.setIn(oldIn);
    }
}