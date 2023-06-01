package tests;

import io.Log;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.JsoupParser;
import parser.Parser;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class JsoupParserTest {

    private JsoupParser jsoupParser;

    @BeforeEach
    void setUp(){
        jsoupParser = (JsoupParser) Parser.getParser();

        try {
            Constructor constructor = JsoupParser.class.getDeclaredConstructor();
            constructor.setAccessible(true);
            jsoupParser = (JsoupParser) constructor.newInstance();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        Log.getLog().getLoggedErrors().clear();
    }

    @Test
    void testGetElementsThatMatchCssQuery() {
        assertTrue(jsoupParser.getElementsThatMatchCssQuery("s").isEmpty());
    }

    @Test
    void testGetTagOfElementsThatMatchCssQuery() {
        assertTrue(jsoupParser.getTagOfElementsThatMatchCssQuery("s").isEmpty());
    }

    @Test
    void testConnectToCorrectWebsite() {
        jsoupParser.connectToWebsite("https://orf.at");

        assertTrue(Log.getLog().getLoggedErrors().isEmpty());
    }

    @Test
    void testConnectToNotAWebsite() {
        jsoupParser.connectToWebsite("test");

        assertEquals("Parser detected invalid URL: test",Log.getLog().getLoggedErrors().get(0));
    }

    @Test
    void testConnectToNonExistingWebsite() {
        jsoupParser.connectToWebsite("https://hkhkajhajhsjla.com");

        assertEquals("Parser couldn't connect to https://hkhkajhajhsjla.com",Log.getLog().getLoggedErrors().get(0));
    }

    @Test
    void testCheckDocument(){
        setUpCheckDocument();

        assertEquals("No Website has been accessed yet. connectToWebsite has to be called first", Log.getLog().getLoggedErrors().get(0));
    }

    @Test
    void testGetLinksOnWebsite() {
        assertTrue(jsoupParser.getLinksOnWebsite().isEmpty());
    }


    void setUpCheckDocument(){
        try {
            Method m = JsoupParser.class.getDeclaredMethod("checkDocument");
            m.setAccessible(true);
            m.invoke(jsoupParser);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}