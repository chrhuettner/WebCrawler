package tests;

import translator.DeeplTranslator;
import core.Heading;
import core.WebsiteLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class WebsiteLinkTest {

    private WebsiteLink websiteLink;
    private String url;
    private Heading heading1;
    private Heading heading2;
    private Heading[] headingArray;
    private int depth;
    private static final String LANGUAGE_ENGLISH = "English";
    private static final String LANGUAGE_CODE_GERMAN = "de";

    @BeforeEach
    void setUp() {
        url = "https://orf.at";
        heading1 = new Heading(1, "Test", LANGUAGE_CODE_GERMAN, new DeeplTranslator());
        heading2 = new Heading(2, "again", LANGUAGE_CODE_GERMAN, new DeeplTranslator());
        headingArray = new Heading[2];
        headingArray[0] = heading1;
        headingArray[1] = heading2;
        depth = 1;
        websiteLink = new WebsiteLink(url, headingArray, depth, false);
    }

    @Test
    void testGetUrl() {
        assertEquals(url, websiteLink.getUrl());
    }

    @Test
    void testGetHeadings() {
        assertEquals(headingArray, websiteLink.getHeadings());
    }

    @Test
    void testGetDepth() {
        assertEquals(depth, websiteLink.getDepth());
    }

    @Test
    void testIsBroken() {
        assertEquals(false, websiteLink.isBroken());
    }

    @Test
    void testGetRepresentation() {
        assertEquals("<br>--> link to <a>https://orf.at</a>" + System.lineSeparator() + "# ->Test" + System.lineSeparator() + "## ->wieder" + System.lineSeparator() + System.lineSeparator(),
                websiteLink.getRepresentation(true));
    }

    @Test
    void testGetRepresentationBrokenLink() {
        websiteLink.setBroken(true);
        assertEquals("<br>--> broken link to <a>" + url + "</a>" + System.lineSeparator() + "# ->Test" + System.lineSeparator() + "## ->wieder" + System.lineSeparator() + System.lineSeparator(),
                websiteLink.getRepresentation(true));
    }

    @Test
    void testGetRepresentationNotDisplaying() {
        websiteLink.setBroken(false);
        assertEquals("# ->Test" + System.lineSeparator() + "## ->wieder" + System.lineSeparator() + System.lineSeparator(),
                websiteLink.getRepresentation(false));
    }

    @Test
    void testGetRepresentationNoText() {
        heading1 = new Heading(1, "", LANGUAGE_CODE_GERMAN, new DeeplTranslator());
        heading2 = new Heading(2, "", LANGUAGE_CODE_GERMAN, new DeeplTranslator());
        headingArray[0] = heading1;
        headingArray[1] = heading2;
        websiteLink = new WebsiteLink(url, headingArray, depth, false);
        assertEquals("" + System.lineSeparator(), websiteLink.getRepresentation(false));
    }

    @Test
    void insertUniqueLanguages() {
        setUpInsertUniqueLanguages();
        Map<String, Integer> languages = new HashMap<>();
        websiteLink.insertUniqueLanguages(languages);
        assertTrue(languages.containsKey(LANGUAGE_ENGLISH));
        assertEquals(2, languages.get(LANGUAGE_ENGLISH));
    }

    private void setUpInsertUniqueLanguages() {
        Field field;
        try {
            field = Heading.class.getDeclaredField("sourceLanguage");
            field.setAccessible(true);
            field.set(heading1, "en");
            field.set(heading2, "en");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}