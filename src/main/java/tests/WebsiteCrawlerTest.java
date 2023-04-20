package tests;

import core.Heading;
import core.Language;
import core.WebsiteCrawler;
import core.WebsiteLink;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WebsiteCrawlerTest {
    private static WebsiteCrawler crawler;
    private static final String url = "https://orf.at";
    private static final String wrongURL = "wrongUrl.at";
    private static final String targetLanguageEn = "en-GB";
    private static final int amountOfTestLinks = 10;
    private static final int amountOfHeadingPerLink = 120;
    private static final int depthCrawl = 0;
    private int sumOfHeadings;
    private ArrayList<WebsiteLink> links;


    @BeforeEach
    void setUp() {
        crawler = new WebsiteCrawler(url);
    }

    private List<WebsiteLink> setUpInsertUniqueHeadingLanguages() {
        List<WebsiteLink> links = new ArrayList<>();

        for (int k = 0; k < amountOfTestLinks; k++) {
            Heading[] headings = new Heading[amountOfHeadingPerLink];
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
        assertEquals(url, crawler.getUrl());
    }

    @Test
    void testCrawl() {
        List<WebsiteLink> links = crawler.crawl(1, targetLanguageEn);
        assertTrue(!links.isEmpty());
    }

    @Test
    void testBrokenLink() {
        crawler = new WebsiteCrawler(wrongURL);
        List<WebsiteLink> links = crawler.crawl(1, targetLanguageEn);
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
            assertEquals(detectedLanguages.get(key), (amountOfTestLinks * amountOfHeadingPerLink) / 6);
        }
    }

    @Test
    void testGetSortedLanguages() {
        HashMap<String, Integer> detectedLanguages = setUpGetSortedLanguages();
        ArrayList<Language> sortedLanguages = crawler.getSortedLanguages(detectedLanguages, sumOfHeadings);

        assertEquals(sortedLanguages.get(0).getName(), "language5");
        assertEquals(sortedLanguages.get(1).getName(), "language3");
        assertEquals(sortedLanguages.get(2).getName(), "language1");
        assertEquals(sortedLanguages.get(3).getName(), "language2");
        assertEquals(sortedLanguages.get(4).getName(), "language4");
    }

    @Test
    void testGetLanguageRepresentation() {
        setUpGetLanguageRepresentation();

        String expectedResult = "testSourceLanguage0 (51.0%), testSourceLanguage2 (30.0%), testSourceLanguage1 (10.0%), testSourceLanguage3 (9.0%)";
        assertEquals(expectedResult, crawler.getLanguageRepresentation(links));
    }

    @Test
    void createCrawlRepresentation() {
        String representation = crawler.createCrawlRepresentation(depthCrawl, "en-GB");

        assertTrue(representation.startsWith("input: <a>" + url));
        String[] representationLines = representation.split(System.lineSeparator());
        assertTrue(representationLines[1].startsWith("<br>depth: " + depthCrawl));
        assertTrue(representationLines[2].startsWith("<br>source languages:"));
        assertTrue(representationLines[3].startsWith("<br>target language: English"));
        assertTrue(representationLines[4].startsWith("<br>summary:"));
    }
}
