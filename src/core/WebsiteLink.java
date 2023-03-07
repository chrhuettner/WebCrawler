package core;

public class WebsiteLink {
    private String url;
    private Heading[] headings;
    private int depth;
    private boolean isBroken;

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
}
