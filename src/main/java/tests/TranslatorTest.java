package tests;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import core.Translator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private TextResult translation;
    private static final String germanText = "Hallo ich bins";
    private static final String englishText = "Hello it's me";
    private static final String sourceLanguage = "de";
    private static final String targetLanguage = "en-GB";

    @Test
    void testTranslateEmpty() throws DeepLException, InterruptedException {
        translation = Translator.translate("", null, "");
        assertEquals("", translation.getText(), "");
        assertEquals(targetLanguage, translation.getDetectedSourceLanguage());
    }

    @Test
    void testTranslateNotEmpty() throws DeepLException, InterruptedException {
        translation = Translator.translate(germanText, sourceLanguage, targetLanguage);
        assertEquals(englishText, translation.getText());
        assertEquals(sourceLanguage, translation.getDetectedSourceLanguage());
    }

    @Test
    void testTranslateWrongLanguage() {
        assertThrows(IllegalArgumentException.class, () -> Translator.translate(englishText, "en", "en"));
    }

    @Test
    public void testGetTranslator() {
        com.deepl.api.Translator translator = Translator.getTranslator();
        assertNotNull(translator);
    }
}