package core;

import io.Log;
import parser.Parser;
import translator.Translator;
import java.util.*;

public class WebsiteCrawler {

    private String url;
    private HashSet<String> links;
    private String representation;
    private Parser parser;
    private Log errorLog;
    private Translator translator;

    private static String unknownTargetLanguageRepresentation = "unknown Target Language";

    private static int unknownHeadingType = 1;

    public WebsiteCrawler(String url, Parser parser, Translator translator) {
        this.url = url;
        links = new HashSet<>();
        this.parser = parser;
        this.errorLog = Log.getLog();
        this.translator = translator;

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
            attemptCrawl(URL, depth, maxDepth, webLinks, targetLanguage);
        }
    }

    private void attemptCrawl(String URL, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {
        links.add(URL);

        if (!parser.connectToWebsite(URL)) {
            errorLog.logError("Crawler aborted crawling " + URL);
            return;
        }

        recursiveCrawl(URL, depth, maxDepth, webLinks, targetLanguage);
    }

    private void recursiveCrawl(String URL, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {
        ArrayList<String> linksOnPage = parser.getLinksOnWebsite();
        WebsiteLink link = new WebsiteLink(URL, extractHeadings(targetLanguage), depth, false);
        webLinks.add(link);
        crawlLinks(linksOnPage, depth + 1, maxDepth, webLinks, targetLanguage);
    }


    private void crawlLinks(ArrayList<String> linksOnPage, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {
        for (String link : linksOnPage) {
            getPageLinks(link, depth, maxDepth, webLinks, targetLanguage);
        }
    }

    private Heading[] extractHeadings(String targetLanguage) {

        ArrayList<String> headingTagsOnPage = parser.getTagOfElementsThatMatchCssQuery("h1, h2, h3, h4, h5, h6");
        ArrayList<String> headingsOnPage = parser.getElementsThatMatchCssQuery("h1, h2, h3, h4, h5, h6");
        Heading[] headings = new Heading[headingTagsOnPage.size()];
        for (int i = 0; i < headings.length; i++) {
            int headingType = extractHeadingType(headingTagsOnPage.get(i)).orElse(unknownHeadingType);
            headings[i] = new Heading(headingType, headingsOnPage.get(i), targetLanguage, Translator.getTranslator());
        }
        return headings;
    }

    private Optional<Integer> extractHeadingType(String tag) {
        try {
            return Optional.of(Integer.parseInt(tag.substring(1, 2)));
        }catch (NumberFormatException e){
            errorLog.logError("Could not extract heading type from "+tag);
        }
        return Optional.empty();
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
                "<br>target language: " + translator.translateTargetCodeToLanguage(targetLanguage).orElse(unknownTargetLanguageRepresentation) + System.lineSeparator() +
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
