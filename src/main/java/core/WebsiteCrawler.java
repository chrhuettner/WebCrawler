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


    public WebsiteCrawler(String url) {
        this.url = url;
        links = new HashSet<>();

    }

    public String getUrl() {
        return url;
    }

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
            Document document = Jsoup.connect(URL).get();
            WebsiteLink link = new WebsiteLink(URL, extractHeadings(document,targetLanguage), depth, false);
            webLinks.add(link);
            Elements linksOnPage = document.select("a[href]");
            crawlLinks(linksOnPage, depth + 1, maxDepth, webLinks, targetLanguage);
        } catch (IOException e) {
            handleBrokenWebsite(URL, depth, webLinks);
          //  System.err.println("For '" + URL + "': " + e.getMessage());
        }
    }

    private void crawlLinks(Elements linksOnPage, int depth, int maxDepth, List<WebsiteLink> webLinks, String targetLanguage) {

        for (Element page : linksOnPage) {
            getPageLinks(page.attr("abs:href"), depth, maxDepth, webLinks, targetLanguage);
        }
    }

    private void handleBrokenWebsite(String URL, int depth, List<WebsiteLink> webLinks) {
        WebsiteLink link = new WebsiteLink(URL, new Heading[]{}, depth, true);
        webLinks.add(link);

    }

    private Heading[] extractHeadings(Document document, String targetLanguage) {
        Elements headingsOnPage = document.select("h1, h2, h3, h4, h5, h6");

        Heading[] headings = new Heading[headingsOnPage.size()];

        for (int i = 0; i < headings.length; i++) {
            headings[i] = new Heading(extractHeadingType(headingsOnPage.get(i).tag().toString()), headingsOnPage.get(i).text(),targetLanguage);

        }
        return headings;
    }

    private int extractHeadingType(String tag) {
        String type = tag.substring(1, 2);

        return Integer.parseInt(type);
    }

    public int insertUniqueHeadingLanguages(List<WebsiteLink> crawledLinks, HashMap<String, Integer> detectedLanguages) {

        int headings = 0;

        for (WebsiteLink website : crawledLinks) {

            website.insertUniqueLanguages(detectedLanguages);

            headings += website.getHeadings().length;
        }
        return headings;
    }

    public ArrayList<Language> getSortedLanguages(HashMap<String, Integer> detectedLanguages, int headingCount) {
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
        ArrayList<Language> sortedLanguages = getSortedLanguages(detectedLanguages, headingCount);
        for (Language language : sortedLanguages) {
            if (languageRepresentation.isEmpty()) {
                languageRepresentation = language.toString();
            } else {
                languageRepresentation += ", " + language.toString();
            }
        }
        return languageRepresentation;
    }

    public String createCrawlRepresentation(int depth, String targetLanguage) {
        List<WebsiteLink> crawledLinks = crawl(depth, targetLanguage);
        String languageRepresentation = getLanguageRepresentation(crawledLinks);
        String representation = "input: <a>" + getUrl() + "</a>" + System.lineSeparator() +
                "<br>depth: " + depth + System.lineSeparator() +
                "<br>source languages: " + languageRepresentation + System.lineSeparator() +
                "<br>target language: " + Language.translateCodeToLanguage(targetLanguage) + System.lineSeparator() +
                "<br>summary:" + System.lineSeparator();
        boolean isInputWebsite = true;
        for (WebsiteLink website : crawledLinks) {
            representation += website.getRepresentation(targetLanguage, !isInputWebsite);
            if (isInputWebsite) {
                isInputWebsite = false;
            }
        }
        return representation;
    }
}
