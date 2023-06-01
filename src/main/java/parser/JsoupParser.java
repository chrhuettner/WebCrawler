package parser;

import io.Log;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class JsoupParser extends Parser {

    private Document document;
    private Log errorLog;

    protected JsoupParser() {
        this.errorLog = Log.getLog();
    }


    @Override
    public ArrayList<String> getElementsThatMatchCssQuery(String query) {
        ArrayList<String> elementsAsStrings = new ArrayList<>();
        if (checkDocument()) {
            Elements matchingElements = document.select(query);
            for (Element element : matchingElements) {
                elementsAsStrings.add(element.text());
            }
        }
        return elementsAsStrings;
    }

    @Override
    public ArrayList<String> getTagOfElementsThatMatchCssQuery(String attribute) {
        ArrayList<String> elementAttributesAsStrings = new ArrayList<>();
        if (checkDocument()) {
            Elements matchingElements = document.select(attribute);
            for (Element element : matchingElements) {
                elementAttributesAsStrings.add(element.tag().toString());
            }
        }
        return elementAttributesAsStrings;
    }

    private boolean checkDocument() {
        if (document == null) {
            errorLog.logError("No Website has been accessed yet. connectToWebsite has to be called first");
            return false;
        }
        return true;
    }

    @Override
    public boolean connectToWebsite(String url) {
        try {
            this.document = Jsoup.connect(url).get();
            return true;
        } catch (IOException e) {
            errorLog.logError("Parser couldn't connect to " + url);
        } catch (IllegalArgumentException e) {
            errorLog.logError("Parser detected invalid URL: " + url);
        }
        return false;
    }

    @Override
    public ArrayList<String> getLinksOnWebsite() {
        ArrayList<String> links = new ArrayList<>();
        if (checkDocument()) {
            Elements elements = document.select("a[href]");
            for (Element linkElement : elements) {
                links.add(linkElement.attr("abs:href"));
            }
        }
        return links;
    }
}
