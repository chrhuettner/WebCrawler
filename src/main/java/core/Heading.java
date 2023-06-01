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
    private static String UNKNOWN_LANGUAGE_REPRESENTATION = "UNKNOWN LANGUAGE";
    private static String UNKNOWN_HEADING_REPRESENTATION = "UNKNOWN TRANSLATED HEADING";

    public Heading(int type, String text, String targetLanguage, Translator translator) {
        this.type = type;
        this.text = text;
        this.targetLanguage = targetLanguage;
        this.translator = translator;
    }

    private void doTranslation() {
        Optional<String> translationResult = translator.translateLineToLanguage(text, null, targetLanguage);
        translatedText = translationResult.orElse(UNKNOWN_HEADING_REPRESENTATION);

        Optional<String> sourceLanguageOfLastTranslation = translator.getSourceLanguageOfLastTranslation();
        this.sourceLanguage = sourceLanguageOfLastTranslation.orElse(UNKNOWN_LANGUAGE_REPRESENTATION);
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

        return languageTranslation.orElse(UNKNOWN_LANGUAGE_REPRESENTATION);
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
