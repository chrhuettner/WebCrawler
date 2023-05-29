package core;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;

public class JsoupParser implements Parser {

    Document document;


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
            throw new RuntimeException("No Website has been accessed yet. connectToWebsite has to be called first");
        }
    }

    @Override
    public void connectToWebsite(String url) {
        try {
            this.document = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String extractAttribute(String line, String attribute) {
        int occurrence = line.indexOf(attribute);
        if (occurrence == -1) {
            return null;
        }

        int endIndex = line.indexOf("\"", occurrence + attribute.length() + 2);

        return line.substring(occurrence + 2, endIndex);

    }
}
