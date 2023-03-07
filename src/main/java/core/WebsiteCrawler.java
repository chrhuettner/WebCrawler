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

    public List<WebsiteLink> crawl(int maxDepth) {
        return getPageLinks(url, 0, maxDepth, new ArrayList<>());
    }

    private List<WebsiteLink> getPageLinks(String URL, int depth, int maxDepth, List<WebsiteLink> webLinks) {
        if ((!links.contains(URL) && (depth < maxDepth))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                Document document = Jsoup.connect(URL).get();

                WebsiteLink link = new WebsiteLink(URL, extractHeadings(document), depth, false);

                webLinks.add(link);

                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth, maxDepth, webLinks);
                }
            } catch (IOException e) {
                WebsiteLink link = new WebsiteLink(URL, new Heading[]{}, depth, true);
                webLinks.add(link);
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
        return webLinks;
    }

    private Heading[] extractHeadings(Document document) {
        Elements headingsOnPage = document.select("h1, h2, h3, h4, h5, h6");

        Heading[] headings = new Heading[headingsOnPage.size()];

        for (int i = 0; i < headings.length; i++) {
            headings[i] = new Heading(extractHeadingType(headingsOnPage.get(i).tag().toString()), headingsOnPage.get(i).text());

        }
        return headings;
    }

    private int extractHeadingType(String tag) {
        String type = tag.substring(1, 2);

        return Integer.parseInt(type);
    }


    public String getRepresentation(int depth, String targetLanguage) {
        List<WebsiteLink> crawledLinks = crawl(depth);
        HashMap<String, Integer> detectedLanguages = new HashMap<>();
        int headings = 0;

        for (WebsiteLink website : crawledLinks) {

            website.insertUniqueLanguages(detectedLanguages);

            headings += website.getHeadings().length;
        }

        String languageRepresentation = "";

        ArrayList<Language> sortedLanguages = new ArrayList<>();

        for (String language : detectedLanguages.keySet()) {
            double ratio = (double) detectedLanguages.get(language) / (double) headings;

            sortedLanguages.add(new Language(ratio, language));
        }
        Collections.sort(sortedLanguages);

        for (Language language : sortedLanguages) {
            if (languageRepresentation.isEmpty()) {
                languageRepresentation = language.toString();
            } else {
                languageRepresentation += ", " + language.toString();
            }
        }


        String representation = "input: <a>" + getUrl() + "</a>" + System.lineSeparator() +
                "<br>depth: " + depth + System.lineSeparator() +
                "<br>source languages: " + languageRepresentation + System.lineSeparator() +
                "<br>target language: " + Main.translateCodeToLanguage(targetLanguage) + System.lineSeparator() +
                "<br>summary:" + System.lineSeparator();
        boolean isInputWebsite = true;
        for (WebsiteLink website : crawledLinks) {
            representation += website.getRepresentation(targetLanguage,!isInputWebsite);
            if (isInputWebsite) {
                isInputWebsite = false;
            }
        }
        return representation;
    }
}
