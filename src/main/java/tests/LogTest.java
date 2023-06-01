package tests;

import io.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LogTest {

    private Log log;

    @BeforeEach
    void setUp(){
        log = Log.getLog();
        log.getLoggedErrors().clear();
    }

    @Test
    void testGetErrorsAsStringEmpty() {
        assertEquals("",log.getErrorsAsString());
    }

    @Test
    void testGetErrorsAsStringNotEmpty() {
        log.getLoggedErrors().add("Adding test log");
        log.getLoggedErrors().add("Adding test log");
        assertEquals("<br> --------- ERRORS --------" + System.lineSeparator()+ "<br>Adding test log"+System.lineSeparator()+
                "<br>Adding test log"+ System.lineSeparator(),log.getErrorsAsString());
    }
}