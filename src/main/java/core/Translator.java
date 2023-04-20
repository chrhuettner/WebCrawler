package core;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;

public class Translator {
    private static com.deepl.api.Translator translator;

    static {
        String authKey = "445fe747-0d4c-9c62-c101-002d26140a51:fx";
        translator = new com.deepl.api.Translator(authKey);
    }

    // We have a limit budget of 500.000 characters per month, so be careful with depth when you call this method
    public static TextResult translate(String text, String sourceLanguage, String targetLanguage) throws DeepLException, InterruptedException, IllegalArgumentException {
        TextResult result;
        if (text.isEmpty()) {
            return new TextResult("", "en-GB");
        }
        result = translator.translateText(text, sourceLanguage, targetLanguage);
        return result;
    }

    public static com.deepl.api.Translator getTranslator() {
        return translator;
    }
}
