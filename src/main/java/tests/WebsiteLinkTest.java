package tests;

import core.Heading;
import core.Language;
import core.WebsiteLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
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
    private static final String LanguageEnglish = "English";
    private static final String LanguageCodeGerman = "de";

    @BeforeEach
    void setUp() {
        url = "https://orf.at";
        heading1 = new Heading(1,"Test",LanguageCodeGerman);
        heading2 = new Heading(2,"again",LanguageCodeGerman);
        headingArray = new Heading[2];
        headingArray[0] = heading1;
        headingArray[1] = heading2;
        depth = 1;
        websiteLink = new WebsiteLink(url,headingArray,depth,false);
    }

    @Test
    void testGetUrl() {
        assertEquals(websiteLink.getUrl(), url);
    }

    @Test
    void testGetHeadings() {
        assertEquals(websiteLink.getHeadings(), headingArray);
    }

    @Test
    void testGetDepth() {
        assertEquals(websiteLink.getDepth(), depth);
    }

    @Test
    void testIsBroken() {
        assertEquals(websiteLink.isBroken(), false);
    }

    @Test
    void testGetRepresentation() {
        assertEquals(websiteLink.getRepresentation(true), "<br>--> link to <a>https://orf.at</a>" +
                System.lineSeparator() + "# ->Test" + System.lineSeparator() +
                "## ->wieder" + System.lineSeparator() + System.lineSeparator());
    }
    @Test
    void testGetRepresentationWithBrokenLink() {
        websiteLink.setBroken(true);
        assertEquals(websiteLink.getRepresentation(true),  "<br>--> broken link to <a>" + url + "</a>" +
                System.lineSeparator()+"# ->Test"+System.lineSeparator()+
                "## ->wieder"+System.lineSeparator()+System.lineSeparator());
    }
    @Test
    void testGetRepresentationWithNotDisplaying() {
        websiteLink.setBroken(false);
        assertEquals(websiteLink.getRepresentation(false),  "# ->Test"+System.lineSeparator()+
                "## ->wieder"+System.lineSeparator()+System.lineSeparator());
    }
    @Test
    void testGetRepresentationNoText() {
        heading1 = new Heading(1,"",LanguageCodeGerman);
        heading2 = new Heading(2,"",LanguageCodeGerman);
        headingArray[0] = heading1;
        headingArray[1] = heading2;
        websiteLink = new WebsiteLink(url,headingArray,depth,false);
        assertEquals(websiteLink.getRepresentation(false),  ""+System.lineSeparator());
    }

    @Test
    void insertUniqueLanguages() {
        setUpInsertUniqueLanguages();
        Map<String, Integer> languages = new HashMap<>();
        websiteLink.insertUniqueLanguages(languages);
        assertTrue(languages.containsKey(LanguageEnglish));
        assertEquals(2, languages.get(LanguageEnglish));
    }

    private void setUpInsertUniqueLanguages(){
        Field field;
        try {
            field = Heading.class.getDeclaredField("sourceLanguage");
            field.setAccessible(true);
            field.set(heading1,"en");
            field.set(heading2,"en");
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}