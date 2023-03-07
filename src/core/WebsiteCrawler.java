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

    public void crawl(int maxDepth) {
        getPageLinks(url, 0, maxDepth);
    }

    private void getPageLinks(String URL, int depth, int maxDepth) {
        if ((!links.contains(URL) && (depth < maxDepth))) {
            System.out.println(">> Depth: " + depth + " [" + URL + "]");
            try {
                links.add(URL);

                Document document = Jsoup.connect(URL).get();

                WebsiteLink link = new WebsiteLink(URL, extractHeadings(document), depth, false);

                Elements linksOnPage = document.select("a[href]");

                depth++;
                for (Element page : linksOnPage) {
                    getPageLinks(page.attr("abs:href"), depth, maxDepth);
                }
            } catch (IOException e) {
                System.err.println("For '" + URL + "': " + e.getMessage());
            }
        }
    }

    private Heading[] extractHeadings(Document document){
        Elements headingsOnPage = document.select("h1, h2, h3, h4, h5, h6");

        Heading[] headings = new Heading[headingsOnPage.size()];

        for(int i = 0; i<headings.length;i++){
            headings[i] = new Heading(extractHeadingType(headingsOnPage.get(i).tag().toString()), headingsOnPage.get(i).text());
            System.out.println(headings[i]);
        }
        return headings;
    }

    private int extractHeadingType(String tag){
        String type = tag.substring(1,2);

        return Integer.parseInt(type);
    }
}
