package tests;

import core.Heading;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class HeadingTest {

    private Heading heading;
    private static final String TEXT = "Test";
    private static final int TYPE = 1;
    private static final String TARGET_LANGUAGE_CODE_GERMAN = "de";
    private static final String LANGUAGE_ENGLISH = "English";

    @BeforeEach
    void setUp() {
        heading = new Heading(TYPE, TEXT, TARGET_LANGUAGE_CODE_GERMAN);
    }

    @Test
    void testGetType() {
        assertEquals(TYPE, heading.getType());
    }

    @Test
    void testGetText() {
        assertEquals(TEXT, heading.getText());
    }

    @Test
    void testGetTranslatedHeading() {
        assertEquals(TEXT, heading.getTranslatedHeading());
    }

    @Test
    void testGetSourceLanguage() {
        assertEquals(LANGUAGE_ENGLISH, heading.getSourceLanguage());
    }

    @Test
    void testGetRepresentation() {
        assertEquals("# ->" + TEXT, heading.getRepresentation(1));
    }

    @Test
    void testGetRepresentationNoText() {
        heading = new Heading(TYPE, "", TARGET_LANGUAGE_CODE_GERMAN);
        assertEquals("", heading.getRepresentation(1));
    }
}