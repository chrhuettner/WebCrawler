package parser;

import java.util.ArrayList;

public abstract class Parser {

    public static Parser getParser(){
        return new JsoupParser();
    }

    public abstract ArrayList<String> getElementsThatMatchCssQuery(String query);

    public abstract ArrayList<String> getTagOfElementsThatMatchCssQuery(String attribute);

    public abstract boolean connectToWebsite(String url);

    public abstract ArrayList<String> getLinksOnWebsite();

}
