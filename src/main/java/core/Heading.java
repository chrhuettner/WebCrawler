package core;


import translator.Translator;
import java.util.Optional;

public class Heading {

    private int type;
    private String text;
    private String sourceLanguage;
    private String translatedText;
    private String targetLanguage;
    private Translator translator;

    private static String unknownLanguageRepresentation = "UNKNOWN LANGUAGE";

    private static String unknownHeadingRepresentation = "UNKNOWN TRANSLATED HEADING";

    public Heading(int type, String text, String targetLanguage, Translator translator) {
        this.type = type;
        this.text = text;
        this.targetLanguage = targetLanguage;
        this.translator = translator;
    }

    private void doTranslation() {
        Optional<String> translationResult = translator.translateLineToLanguage(text, null, targetLanguage);
        translatedText = translationResult.orElse(unknownHeadingRepresentation);

        Optional<String> sourceLanguageOfLastTranslation = translator.getSourceLanguageOfLastTranslation();
        this.sourceLanguage = sourceLanguageOfLastTranslation.orElse(unknownLanguageRepresentation);
    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String getTranslatedHeading() {
        if (translatedText == null) {
            doTranslation();
        }
        return translatedText;
    }

    public String getSourceLanguage() {
        if (sourceLanguage == null) {
            doTranslation();
        }
        Optional<String> languageTranslation = translator.translateSourceCodeToLanguage(sourceLanguage);

        return languageTranslation.orElse(unknownLanguageRepresentation);
    }

    public String getRepresentation(int depth) {
        String indentations = "";
        for (int i = 0; i < type; i++) {
            indentations += "#";
        }

        String translation = getTranslatedHeading();
        if (translation.isBlank()) {
            return "";
        }

        String lines = "";
        for (int i = 0; i < depth; i++) {
            lines += "-";
        }

        return indentations + " " + lines + ">" + getTranslatedHeading();
    }
}
