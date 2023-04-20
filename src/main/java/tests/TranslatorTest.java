package tests;

import com.deepl.api.DeepLException;
import com.deepl.api.TextResult;
import core.Translator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private TextResult translation;
    private static final String GERMAN_TEXT = "Hallo ich bins";
    private static final String ENGLISH_TEXT = "Hello it's me";
    private static final String SOURCE_LANGUAGE_CODE_GERMAN = "de";
    private static final String TARGET_LANGUAGE_CODE_ENGLISH = "en-GB";

    @Test
    void testTranslateEmpty() throws DeepLException, InterruptedException {
        translation = Translator.translate("", null, "");
        assertEquals("", translation.getText(), "");
        assertEquals(TARGET_LANGUAGE_CODE_ENGLISH, translation.getDetectedSourceLanguage());
    }

    @Test
    void testTranslateNotEmpty() throws DeepLException, InterruptedException {
        translation = Translator.translate(GERMAN_TEXT, SOURCE_LANGUAGE_CODE_GERMAN, TARGET_LANGUAGE_CODE_ENGLISH);
        assertEquals(ENGLISH_TEXT, translation.getText());
        assertEquals(SOURCE_LANGUAGE_CODE_GERMAN, translation.getDetectedSourceLanguage());
    }

    @Test
    void testTranslateWrongLanguage() {
        assertThrows(IllegalArgumentException.class, () -> Translator.translate(ENGLISH_TEXT, "en", "en"));
    }

    @Test
    public void testGetTranslator() {
        com.deepl.api.Translator translator = Translator.getTranslator();
        assertNotNull(translator);
    }
}