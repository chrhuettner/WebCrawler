package core;

public class Heading {

    private int type;
    private String text;

    public Heading(int type, String text) {
        this.type = type;
        this.text = text;
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    @Override
    public String toString() {
        return "<h"+type+"> "+text;
    }
}
