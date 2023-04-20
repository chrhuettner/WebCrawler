package core;

import java.util.Map;

public class WebsiteLink {
    private String url;
    private Heading[] headings;
    private int depth;
    private boolean isBroken;
    private String representation = "";

    public WebsiteLink(String url, Heading[] headings, int depth, boolean isBroken) {
        this.url = url;
        this.headings = headings;
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

    public void setBroken(boolean broken) {
        isBroken = broken;
    }

    public String getRepresentation(boolean displayLink) {
        if(displayLink) {
            representLink();
        }
       representHeadings();
       representation += System.lineSeparator();
       return representation;
    }

    private void representLink(){
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
    private void representHeadings(){
        for(Heading heading: headings){
            String headingRepresentation =  heading.getRepresentation(depth);
            if(!headingRepresentation.isBlank()) {
                representation += headingRepresentation+System.lineSeparator();
            }
        }
    }

    public void insertUniqueLanguages( Map<String, Integer> languages){
        for(Heading h: headings){
            if(!languages.containsKey(h.getSourceLanguage())){
                languages.put(h.getSourceLanguage(),1);
            }else{
                languages.put(h.getSourceLanguage(), languages.get(h.getSourceLanguage())+1);
            }
        }

    }
}
