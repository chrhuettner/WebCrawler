package core;


import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;

public class Heading {

    private int type;
    private String text;
    private String sourceLanguage;
    private String translatedText;
    private String targetLanguage;

    public Heading(int type, String text, String targetLanguage) {
        this.type = type;
        this.text = text;
        this.targetLanguage = targetLanguage;

    }

    private void doTranslation() {
        TextResult translation = Translator.translate(text, null, targetLanguage);
        this.sourceLanguage = translation.getDetectedSourceLanguage();
        this.translatedText = translation.getText();
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
        return Language.translateCodeToLanguage(sourceLanguage);
    }

    public String getRepresentation() {
        String indentations = "";
        for (int i = 0; i < type; i++) {
            indentations += "#";
        }
        String translation = getTranslatedHeading();

        if (translation.isBlank()) {
            return "";
        }
        return indentations + " -->" + getTranslatedHeading();
    }
}
