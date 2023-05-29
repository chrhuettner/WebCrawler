package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

public class WebsiteCrawler {

    private String url;
    private HashSet<String> links;
    private String representation;
    private Parser parser;

    public WebsiteCrawler(String url, Parser parser) {
        this.url = url;
        links = new HashSet<>();
        this.parser = parser;

    }

    public String getUrl() {
        return url;
    }

    // A max depth > 2 will take a very long time (exponential growth)
    public List<WebsiteLink> crawl(int maxDepth, String targetLanguage) {
        ArrayList<WebsiteLink> crawledLinks = new ArrayList<>();
        getPageLinks(url, 0, maxDepth, crawledLinks, targetLanguage);
        return crawledLinks;
    }

    private void getPageLinks(String URL, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {
        if ((!links.contains(URL) && (depth < maxDepth))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            handleURL(URL, depth, maxDepth, webLinks, targetLanguage);
        }
    }

    private void handleURL(String URL, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {
        try {
            links.add(URL);
            parser.connectToWebsite(URL);
            ArrayList<String> linksOnPage = parser.getElementsThatMatchCssQuery("a[href]");
            WebsiteLink link = new WebsiteLink(URL, extractHeadings(targetLanguage), depth, false);
            webLinks.add(link);

            crawlLinks(linksOnPage, depth + 1, maxDepth, webLinks, targetLanguage);
        } catch (IllegalArgumentException ex) {
            handleBrokenWebsite(URL, depth, webLinks);
        }
    }

    private void crawlLinks(ArrayList<String> linksOnPage, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {
        for (String page : linksOnPage) {
            getPageLinks(parser.extractAttribute(page, "href"), depth, maxDepth, webLinks, targetLanguage);
        }
    }

    private void handleBrokenWebsite(String URL, int depth, List<WebsiteLink> webLinks) {
        WebsiteLink link = new WebsiteLink(URL, new Heading[]{}, depth, true);
        webLinks.add(link);
    }

    private Heading[] extractHeadings(String targetLanguage) {

        ArrayList<String> headingTagsOnPage = parser.getTagOfElementsThatMatchCssQuery("h1, h2, h3, h4, h5, h6");
        ArrayList<String> headingsOnPage = parser.getElementsThatMatchCssQuery("h1, h2, h3, h4, h5, h6");
        Heading[] headings = new Heading[headingTagsOnPage.size()];
        for (int i = 0; i < headings.length; i++) {
            headings[i] = new Heading(extractHeadingType(headingTagsOnPage.get(i)), headingsOnPage.get(i), targetLanguage);
        }
        return headings;
    }

    private int extractHeadingType(String tag) {
        return Integer.parseInt(tag.substring(1, 2));
    }

    public int insertUniqueHeadingLanguages(List<WebsiteLink> crawledLinks, HashMap<String, Integer> detectedLanguages) {
        int headings = 0;
        for (WebsiteLink website : crawledLinks) {
            website.insertUniqueLanguages(detectedLanguages);
            headings += website.getHeadings().length;
        }
        return headings;
    }

    public ArrayList<Language> getLanguagesSortedByRatio(HashMap<String, Integer> detectedLanguages, int headingCount) {
        ArrayList<Language> sortedLanguages = new ArrayList<>();
        for (String language : detectedLanguages.keySet()) {
            double ratio = (double) detectedLanguages.get(language) / (double) headingCount;
            sortedLanguages.add(new Language(ratio, language));
        }
        Collections.sort(sortedLanguages);

        return sortedLanguages;
    }

    public String getLanguageRepresentation(List<WebsiteLink> crawledLinks) {
        HashMap<String, Integer> detectedLanguages = new HashMap<>();
        int headingCount = insertUniqueHeadingLanguages(crawledLinks, detectedLanguages);
        String languageRepresentation = "";
        ArrayList<Language> sortedLanguages = getLanguagesSortedByRatio(detectedLanguages, headingCount);

        for (Language language : sortedLanguages) {
            if (languageRepresentation.isEmpty()) {
                languageRepresentation = language.toString();
            } else {
                languageRepresentation += ", " + language.toString();
            }
        }
        return languageRepresentation;
    }

    // Be careful with depth, because this method will call the 'translate' method which has a limited budget
    public String createCrawlRepresentation(int depth, String targetLanguage) {
        List<WebsiteLink> crawledLinks = crawl(depth, targetLanguage);

        String languageRepresentation = getLanguageRepresentation(crawledLinks);

        representation = "input: <a>" + getUrl() + "</a>" + System.lineSeparator() +
                "<br>depth: " + depth + System.lineSeparator() +
                "<br>source languages: " + languageRepresentation + System.lineSeparator() +
                "<br>target language: " + Language.translateTargetCodeToLanguage(targetLanguage) + System.lineSeparator() +
                "<br>summary:" + System.lineSeparator();

        createWebsiteLinksRepresentation(crawledLinks);

        return representation;
    }

    private void createWebsiteLinksRepresentation(List<WebsiteLink> crawledLinks) {
        boolean isInputWebsite = true;
        for (WebsiteLink website : crawledLinks) {
            representation += website.getRepresentation(!isInputWebsite);
            if (isInputWebsite) {
                isInputWebsite = false;
            }
        }
    }

}
