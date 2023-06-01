package tests;

import io.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    @Test
    void testGetErrorsAsStringEmpty() {
        Log log = Log.getLog();
        log.getLoggedErrors().clear();
        assertEquals("",log.getErrorsAsString());
    }

    @Test
    void testGetErrorsAsStringNotEmpty() {
        Log log = Log.getLog();
        log.getLoggedErrors().clear();
        log.getLoggedErrors().add("Adding test log");
        log.getLoggedErrors().add("Adding test log");
        assertEquals("<br> --------- ERRORS --------" + System.lineSeparator()+ "<br>Adding test log"+System.lineSeparator()+
                "<br>Adding test log"+ System.lineSeparator(),log.getErrorsAsString());
    }
}