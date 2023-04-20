package tests;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import core.Translator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private TextResult translation;
    @Test
    void testTranslateEmpty() throws DeepLException, InterruptedException {
        translation = Translator.translate("", null, "");
        assertEquals("", translation.getText(), "");
        assertEquals("en-GB", translation.getDetectedSourceLanguage());
    }
    @Test
    void testTranslateNotEmpty() throws DeepLException, InterruptedException {
        translation = Translator.translate("Hallo ich bins", "de", "en-GB");
        assertEquals("Hello it's me",translation.getText());
        assertEquals("de",translation.getDetectedSourceLanguage());
    }
    @Test
    void testTranslateWrongLanguage() {
        assertThrows(IllegalArgumentException.class, () -> Translator.translate("Hallo", "en", "en"));
    }

    @Test
    public void testGetTranslator() {
        com.deepl.api.Translator translator = Translator.getTranslator();
        assertNotNull(translator);
    }
}