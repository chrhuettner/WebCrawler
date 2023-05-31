package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class JsoupParser implements Parser {

    private Document document;
    private Log errorLog;

    public JsoupParser(){
        this.errorLog = Log.getLog();
    }


    @Override
    public ArrayList<String> getElementsThatMatchCssQuery(String query) {
        checkDocument();
        Elements matchingElements = document.select(query);
        ArrayList<String> elementsAsStrings = new ArrayList<>(matchingElements.size());

        for (Element element : matchingElements) {
            elementsAsStrings.add(element.text());
        }

        return elementsAsStrings;
    }

    @Override
    public ArrayList<String> getTagOfElementsThatMatchCssQuery(String attribute) {
        checkDocument();

        Elements matchingElements = document.select(attribute);

        ArrayList<String> elementAttributesAsStrings = new ArrayList<>(matchingElements.size());

        for (Element element : matchingElements) {
            elementAttributesAsStrings.add(element.tag().toString());
        }
        return elementAttributesAsStrings;
    }

    private void checkDocument() {
        if (document == null) {
            errorLog.logError("No Website has been accessed yet. connectToWebsite has to be called first");
            throw new RuntimeException("No Website has been accessed yet. connectToWebsite has to be called first");
        }
    }

    @Override
    public void connectToWebsite(String url) {
        try {
            this.document = Jsoup.connect(url).get();
        } catch (IOException e) {
            errorLog.logError("Invalid URL detected: "+url);
            e.printStackTrace();
        }
    }

    @Override
    public ArrayList<String> getLinksOnWebsite() {
        checkDocument();
        Elements elements = document.select("a[href]");

        ArrayList<String> links = new ArrayList<>(elements.size());

        for(Element linkElement:elements){
            links.add(linkElement.attr("abs:href"));
        }
        return links;
    }
}
