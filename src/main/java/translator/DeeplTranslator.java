package translator;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import io.Log;

import java.util.Optional;

public class DeeplTranslator extends Translator {
    private static com.deepl.api.Translator translator;
    private TextResult lastTranslation;

    static {
        String authKey = "445fe747-0d4c-9c62-c101-002d26140a51:fx";
        translator = new com.deepl.api.Translator(authKey);
    }

    private Log errorLog;

    public DeeplTranslator(){
        this.errorLog = Log.getLog();
    }

    // We have a limit budget of 500.000 characters per month, so be careful with depth when you call this method
    /*public static TextResult translate(String text, String sourceLanguage, String targetLanguage) throws DeepLException, InterruptedException, IllegalArgumentException {
        TextResult result;
        if (text.isEmpty()) {
            return new TextResult("", "en-GB");
        }
        result = translator.translateText(text, sourceLanguage, targetLanguage);
        return result;
    }*/

    /*public static com.deepl.api.Translator getTranslator() {
        return translator;
    }*/

    @Override
    public Optional<String> translateLineToLanguage(String line, String sourceLanguage, String targetLanguage) {

        if (line.isEmpty()) {
            lastTranslation = new TextResult("", "en-GB");
            return Optional.of(lastTranslation.getText());
        }
        try {
            lastTranslation = translator.translateText(line, sourceLanguage, targetLanguage);
            return Optional.of(lastTranslation.getText());
        } catch (DeepLException e) {
            errorLog.logError("Deepl failed to translate "+line+" from "+sourceLanguage+" to "+targetLanguage);

        } catch (InterruptedException e) {
            errorLog.logError("Deepl got Interrupted while translating "+line+" from "+sourceLanguage+" to "+targetLanguage);
        }

        return Optional.empty();
    }

    @Override
    public Optional<String> translateLanguageToCode(String language) {
        try {
            for (com.deepl.api.Language l : translator.getTargetLanguages()) {
                if (l.getName().toLowerCase().startsWith(language.toLowerCase())) {
                    return Optional.of(l.getCode());
                }
            }
        } catch (DeepLException | InterruptedException e) {
            errorLog.logError("Translation Error occurred. Tried to translate "+language+" to Code.");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // This method translates the ISO 3166-1 Language-Code (e.g. 'en-GB', 'pt-BR')(target language) to a language name
    @Override
    public Optional<String> translateTargetCodeToLanguage(String code) {
        try {
            for (com.deepl.api.Language l : translator.getTargetLanguages()) {
                if (l.getCode().toLowerCase().equals(code.toLowerCase())) {
                    return Optional.of(l.getName());
                }
            }
        } catch (DeepLException | InterruptedException e) {
            errorLog.logError("Translation Error occurred. Tried to translate target "+code+" to Language.");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    // This method translates the ISO 639-1 Language-Code (e.g. 'en', 'pt')(source language) to a language name
    @Override
    public Optional<String> translateSourceCodeToLanguage(String code) {
        try {
            for (com.deepl.api.Language l : translator.getSourceLanguages()) {
                if (l.getCode().toLowerCase().equals(code.toLowerCase())) {
                    return Optional.of(l.getName());
                }
            }
        } catch (DeepLException | InterruptedException e) {
            errorLog.logError("Translation Error occurred. Tried to translate source "+code+" to Language.");
            e.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public Optional<String> getSourceLanguageOfLastTranslation() {
        if(lastTranslation == null) {
            return Optional.empty();
        }
        return Optional.of(lastTranslation.getDetectedSourceLanguage());
    }
}
