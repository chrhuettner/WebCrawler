package tests;

import core.Heading;
import core.Translator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadingTest {

    private Heading heading;
    private static final String text = "Test";
    private static final int type = 1;
    private static final String targetLanguage = "de";
    private static final String language = "English";

    @BeforeEach
    void setUp() {
        heading = new Heading(type, text, targetLanguage);
    }

    @Test
    void testGetType() {
        assertEquals(heading.getType(), type);
    }

    @Test
    void testGetText() {
        assertEquals(heading.getText(), text);
    }

    @Test
    void testGetTranslatedHeading() {
        assertEquals(heading.getTranslatedHeading(), text);
    }

    @Test
    void testGetSourceLanguage() {
        assertEquals(heading.getSourceLanguage(), language);
    }

    @Test
    void testGetRepresentation() {
        assertEquals(heading.getRepresentation(1), "# ->" + text);
    }

    @Test
    void testGetRepresentationNoText() {
        heading = new Heading(type, "", targetLanguage);
        assertEquals(heading.getRepresentation(1), "");
    }
}