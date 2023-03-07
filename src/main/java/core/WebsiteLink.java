package core;

import java.util.List;
import java.util.Map;

public class WebsiteLink {
    private String url;
    private Heading[] headings;
    private int depth;
    private boolean isBroken;

    public WebsiteLink(String url, Heading[] heading, int depth, boolean isBroken) {
        this.url = url;
        headings = heading;
        this.depth = depth;
        this.isBroken = isBroken;
    }

    public String getUrl() {
        return url;
    }

    public Heading[] getHeadings() {
        return headings;
    }

    public int getDepth() {
        return depth;
    }

    public boolean isBroken() {
        return isBroken;
    }


    public String getRepresentation(String targetLanguage, boolean displayLink) {
        String representation="";

        if(displayLink) {
            String prefixLines = "-";
            for (int i = 0; i < depth; i++) {
                prefixLines += "-";
            }
            if (isBroken) {
                representation = "<br>" + prefixLines + "> broken link to <a>" + url + "</a>" + System.lineSeparator();
            } else {
                representation = "<br>" + prefixLines + "> link to <a>" + url + "</a>" + System.lineSeparator();
            }
        }
       for(Heading heading: headings){
           String headingRepresentation =  heading.getRepresentation(targetLanguage);
           if(!headingRepresentation.isBlank()) {
               representation += headingRepresentation+System.lineSeparator();
           }
       }
       representation += System.lineSeparator();
       return representation;
    }

    public void insertUniqueLanguages( Map<String, Integer> languages){
        for(Heading h: headings){
            if(!languages.containsKey(h.getLanguage())){
                languages.put(h.getLanguage(),1);
            }else{
                languages.put(h.getLanguage(), languages.get(h.getLanguage())+1);
            }
        }

    }
}
