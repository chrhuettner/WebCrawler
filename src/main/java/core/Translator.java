package core;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;

public class Translator {
    private static com.deepl.api.Translator translator;


    static {
        String authKey = "445fe747-0d4c-9c62-c101-002d26140a51:fx";
        translator = new com.deepl.api.Translator(authKey);

    }

    public static TextResult translate(String text, String sourceLanguage, String targetLanguage) {

        TextResult result = null;
        if (text.isEmpty()) {
            return new TextResult("", "en-GB");
        }
        try {
            result = translator.translateText(text, sourceLanguage, targetLanguage);
        } catch (DeepLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return result;
    }

    public static String getLimitString() {
        String limit = "";
        try {
            limit = translator.getUsage().toString();
        } catch (DeepLException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return limit;
    }

    public static com.deepl.api.Translator getTranslator() {
        return translator;
    }
}
