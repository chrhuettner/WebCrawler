package core;

import com.deepl.api.DeepLException;

import java.util.Locale;

public class Language implements Comparable<Language> {
    private double ratio;
    private String name;

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

    public static String translateLanguageToCode(String language) {
        try {
            for (com.deepl.api.Language l : Translator.getTranslator().getTargetLanguages()) {
                if (l.getName().toLowerCase().startsWith(language.toLowerCase())) {
                    return l.getCode();
                }
            }
        } catch (DeepLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // This method translates the ISO 3166-1 Language-Code (e.g. 'en-GB', 'pt-BR')(target language) to a language name
    public static String translateTargetCodeToLanguage(String code) {
        try {
            for (com.deepl.api.Language l : Translator.getTranslator().getTargetLanguages()) {
                if (l.getCode().toLowerCase().equals(code.toLowerCase())) {
                    return l.getName();
                }
            }
        } catch (DeepLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }

    // This method translates the ISO 639-1 Language-Code (e.g. 'en', 'pt')(source language) to a language name
    public static String translateSourceCodeToLanguage(String code) {
        try {
            for (com.deepl.api.Language l : Translator.getTranslator().getSourceLanguages()) {
                if (l.getCode().toLowerCase().equals(code.toLowerCase())) {
                    return l.getName();
                }
            }
        } catch (DeepLException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
