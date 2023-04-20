package tests;

import java.io.OutputStream;
import java.io.PrintStream;
import java.util.ArrayList;

public class LogStream extends PrintStream {

    private ArrayList<String> outputLog;

    public LogStream(OutputStream out) {
        super(out);
        outputLog = new ArrayList<>();
    }

    @Override
    public void println(String str) {
        super.println(str);
        outputLog.add(str);
    }

    public ArrayList<String> getOutputLog() {
        return outputLog;
    }
}
