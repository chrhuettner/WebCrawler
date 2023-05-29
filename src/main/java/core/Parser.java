package core;

import java.util.ArrayList;

public interface Parser {

    public abstract ArrayList<String> getElementsThatMatchCssQuery(String query);

    public abstract ArrayList<String> getTagOfElementsThatMatchCssQuery(String attribute);

    public abstract void connectToWebsite(String url);

    public abstract String extractAttribute(String line, String attribute);

}
