package translator;

import java.util.Optional;

public abstract class Translator {

    public static Translator getTranslator(){
        return new DeeplTranslator();
    }

    public abstract Optional<String> translateLineToLanguage(String line, String sourceLanguage, String targetLanguage);

    public abstract Optional<String> translateLanguageToCode(String language);

    public abstract Optional<String> translateTargetCodeToLanguage(String code);

    public abstract Optional<String> translateSourceCodeToLanguage(String code);

    public abstract Optional<String> getSourceLanguageOfLastTranslation();

}
