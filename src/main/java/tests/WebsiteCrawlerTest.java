package tests;

import core.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import parser.JsoupParser;
import translator.DeeplTranslator;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebsiteCrawlerTest {

    private static WebsiteCrawler crawler;
    private static final String URL = "https://orf.at";
    private static final String WRONG_URL = "wrongUrl.at";
    private static final String TARGET_LANGUAGE_CODE_ENGLISH = "en-GB";
    private static final int AMOUNT_OF_TESTLINKS = 10;
    private static final int AMOUNT_OF_HEADINGS_PER_LINK = 120;
    private static final int DEPTH = 0;
    private int sumOfHeadings;
    private ArrayList<WebsiteLink> links;

    @BeforeEach
    void setUp() {
        crawler = new WebsiteCrawler(URL, new JsoupParser(), new DeeplTranslator());
    }

    private List<WebsiteLink> setUpInsertUniqueHeadingLanguages() {
        List<WebsiteLink> links = new ArrayList<>();

        for (int k = 0; k < AMOUNT_OF_TESTLINKS; k++) {
            Heading[] headings = new Heading[AMOUNT_OF_HEADINGS_PER_LINK];
            for (int i = 0; i < headings.length; i++) {
                int type = (i % 6) + 1;

                headings[i] = mock(Heading.class);
                when(headings[i].getSourceLanguage()).thenReturn("testSourceLanguage" + type);
                when(headings[i].getTranslatedHeading()).thenReturn("translatedHeading" + type);
                when(headings[i].getRepresentation(any(int.class))).thenReturn("Headingrepresentation");
            }
            links.add(new WebsiteLink("testUrl" + k, headings, 1, false));
        }
        return links;
    }

    private HashMap<String, Integer> setUpGetSortedLanguages() {
        HashMap<String, Integer> detectedLanguages = new HashMap<>();
        detectedLanguages.put("language1", 5);
        detectedLanguages.put("language2", 3);
        detectedLanguages.put("language3", 6);
        detectedLanguages.put("language4", 2);
        detectedLanguages.put("language5", 8);

        for (int headingCount : detectedLanguages.values()) {
            sumOfHeadings = headingCount;
        }
        return detectedLanguages;
    }

    private void setUpGetLanguageRepresentation() {
        links = new ArrayList<>();
        int headingAmount = 100;
        for (int j = 0; j < 10; j++) {
            Heading[] headings = new Heading[headingAmount];
            for (int i = 0; i < headings.length; i++) {
                int type = 0;
                if (i % 10 == 0) {
                    type = 1;
                } else if (i % 3 == 0) {
                    type = 2;
                } else if (i % 7 == 0) {
                    type = 3;
                }
                headings[i] = mock(Heading.class);
                when(headings[i].getSourceLanguage()).thenReturn("testSourceLanguage" + type);
            }
            links.add(new WebsiteLink("orf" + j + ".at", headings, 1, false));
        }
    }

    @Test
    void testGetUrl() {
        assertEquals(URL, crawler.getUrl());
    }

    @Test
    void testCrawl() {
        List<WebsiteLink> links = crawler.crawl(1, TARGET_LANGUAGE_CODE_ENGLISH);

        assertTrue(!links.isEmpty());
    }

    @Test
    void testBrokenLink() {
        crawler = new WebsiteCrawler(WRONG_URL, new JsoupParser(), new DeeplTranslator());
        List<WebsiteLink> links = crawler.crawl(1, TARGET_LANGUAGE_CODE_ENGLISH);

        assertTrue(links.size() == 1);
        assertTrue(links.get(0).isBroken());
    }

    @Test
    void testInsertUniqueHeadingLanguages() {
        List<WebsiteLink> links = setUpInsertUniqueHeadingLanguages();

        HashMap<String, Integer> detectedLanguages = new HashMap<>();
        crawler.insertUniqueHeadingLanguages(links, detectedLanguages);

        assertTrue(detectedLanguages.size() == 6);

        for (int i = 1; i < 7; i++) {
            String key = "testSourceLanguage" + i;
            assertTrue(detectedLanguages.containsKey(key));
            assertEquals((AMOUNT_OF_TESTLINKS * AMOUNT_OF_HEADINGS_PER_LINK) / 6, detectedLanguages.get(key));
        }
    }

    @Test
    void testGetLanguagesSortedByRatio() {
        HashMap<String, Integer> detectedLanguages = setUpGetSortedLanguages();
        ArrayList<Language> sortedLanguages = crawler.getLanguagesSortedByRatio(detectedLanguages, sumOfHeadings);

        assertEquals("language5", sortedLanguages.get(0).getName());
        assertEquals("language3", sortedLanguages.get(1).getName());
        assertEquals("language1", sortedLanguages.get(2).getName());
        assertEquals("language2", sortedLanguages.get(3).getName());
        assertEquals("language4", sortedLanguages.get(4).getName());
    }

    @Test
    void testGetLanguageRepresentation() {
        setUpGetLanguageRepresentation();

        String expectedResult = "testSourceLanguage0 (51.0%), testSourceLanguage2 (30.0%), testSourceLanguage1 (10.0%), testSourceLanguage3 (9.0%)";
        assertEquals(expectedResult, crawler.getLanguageRepresentation(links));
    }

    @Test
    void testCreateCrawlRepresentation() {
        String representation = crawler.createCrawlRepresentation(DEPTH, TARGET_LANGUAGE_CODE_ENGLISH);

        assertTrue(representation.startsWith("input: <a>" + URL));
        String[] representationLines = representation.split(System.lineSeparator());
        assertTrue(representationLines[1].startsWith("<br>depth: " + DEPTH));
        assertTrue(representationLines[2].startsWith("<br>source languages:"));
        assertTrue(representationLines[3].startsWith("<br>target language: English"));
        assertTrue(representationLines[4].startsWith("<br>summary:"));
    }
}
