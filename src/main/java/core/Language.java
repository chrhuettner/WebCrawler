package core;
import io.Log;
import java.util.Locale;

public class Language implements Comparable<Language> {
    private double ratio;
    private String name;
    private static Log errorLog;

    static{
        errorLog = Log.getLog();
    }

    public Language(double ratio, String name) {
        this.ratio = ratio;
        this.name = name;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int compareTo(Language o) {
        if (this.ratio > o.getRatio()) {
            return -1;
        }
        if (this.ratio == o.getRatio()) {
            return 0;
        }

        Locale l = new Locale("en");
        l.getDisplayLanguage();
        return 1;
    }

    @Override
    public String toString() {
        return name + " (" + trimRatio(2) + "%)";
    }

    private String trimRatio(int decimals) {
        double scaledRatio = ratio * 100;
        String representation = "" + scaledRatio;
        if (representation.contains(".")) {
            representation = representation.substring(0, Math.min(representation.length(),
                    representation.indexOf(".") + decimals + 1));
        }
        return representation;
    }

}
