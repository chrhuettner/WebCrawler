package core;

import java.util.ArrayList;

public interface Parser {

    public abstract ArrayList<String> getElementsThatMatchCssQuery(String query);

    public abstract ArrayList<String> getTagOfElementsThatMatchCssQuery(String attribute);

    public abstract boolean connectToWebsite(String url);

    public abstract ArrayList<String> getLinksOnWebsite();

}
