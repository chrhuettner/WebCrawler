package core;


import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import com.deepl.api.Translator;

public class Heading {

    private int type;
    private String text;
    private String language;
    private static  Translator translator;

    static{
        String authKey = "445fe747-0d4c-9c62-c101-002d26140a51:fx";
        translator = new Translator(authKey);

    }

    public static TextResult translate(String text, String sourceLanguage, String targetLanguage){
        TextResult result = null;
        if(text.isEmpty()){
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

    public Heading(int type, String text) {
        this.type = type;
        this.text = text;

        language = translate(text, null, "en-GB").getDetectedSourceLanguage();

    }

    public int getType() {
        return type;
    }

    public String getText() {
        return text;
    }

    public String translateHeading(String targetLanguage) {

        return translate(text, null, targetLanguage).getText();
    }

    public String getLanguage() {
        return Main.translateCodeToLanguage(language);
    }

    public String getRepresentation(String targetLanguage) {
        String indentations = "";
        for(int i = 0; i<type;i++){
            indentations += "#";
        }
        String translation =  translateHeading(targetLanguage);

        if(translation.isBlank()){
            return "";
        }
        return indentations+" -->" + translateHeading(targetLanguage);
    }
}
