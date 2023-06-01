package tests;

import com.deepl.api.DeepLException;
import translator.DeeplTranslator;
import translator.Translator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

class DeeplTranslatorTest {

    private String translation;
    private static final String GERMAN_TEXT = "Hallo ich bins";
    private static final String ENGLISH_TEXT = "Hello it's me";
    private static final String SOURCE_LANGUAGE_CODE_GERMAN = "de";
    private static final String TARGET_LANGUAGE_CODE_ENGLISH = "en-GB";
    private static final String nullRepresentation = "NULL";
    private Translator translator;

    @BeforeAll
    void setUpTests(){
        translator = Translator.getTranslator();
    }

    @Test
    void testTranslateEmpty() throws DeepLException, InterruptedException {
        Optional<String> translateLineToLanguage = translator.translateLineToLanguage("", null, "");

        translation = translateLineToLanguage.orElse(nullRepresentation);

        assertEquals("", translation, "");
        assertEquals(TARGET_LANGUAGE_CODE_ENGLISH, translator.getSourceLanguageOfLastTranslation());
    }

    @Test
    void testTranslateNotEmpty() throws DeepLException, InterruptedException {
        Optional<String> translateLineResult = translator.translateLineToLanguage(GERMAN_TEXT, SOURCE_LANGUAGE_CODE_GERMAN, TARGET_LANGUAGE_CODE_ENGLISH);

        translation = translateLineResult.orElse(nullRepresentation);

        assertEquals(ENGLISH_TEXT, translation);
        assertEquals(SOURCE_LANGUAGE_CODE_GERMAN, translator.getSourceLanguageOfLastTranslation());
    }

    @Test
    void testTranslateWrongLanguage() {
        assertThrows(IllegalArgumentException.class, () -> translator.translateLineToLanguage(ENGLISH_TEXT, "en", "en"));
    }

}