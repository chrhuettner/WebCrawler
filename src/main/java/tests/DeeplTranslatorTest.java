package tests;

import io.Log;
import org.junit.jupiter.api.BeforeEach;
import translator.Translator;
import org.junit.jupiter.api.Test;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.*;

class DeeplTranslatorTest {

    private String translation;
    private static final String GERMAN_TEXT = "Hallo ich bins";
    private static final String ENGLISH_TEXT = "Hello it's me";
    private static final String SOURCE_LANGUAGE_CODE_GERMAN = "de";
    private static final String TARGET_LANGUAGE_CODE_ENGLISH = "en-GB";
    private static final String NULL_REPRESENTATION = "NULL";
    private static Translator translator;

    @BeforeEach
    void setUpTests(){
        translator = Translator.getTranslator();
        Log.getLog().getLoggedErrors().clear();
    }

    @Test
    void testTranslateEmpty() {
        assertTrue(translator.translateLineToLanguage("test", "test", "test").isEmpty());
    }

    @Test
    void testTranslateNotEmpty() {
        Optional<String> translateLineResult = translator.translateLineToLanguage(GERMAN_TEXT, SOURCE_LANGUAGE_CODE_GERMAN, TARGET_LANGUAGE_CODE_ENGLISH);

        translation = translateLineResult.orElse(NULL_REPRESENTATION);

        assertEquals(ENGLISH_TEXT, translation);
        assertEquals(SOURCE_LANGUAGE_CODE_GERMAN, translator.getSourceLanguageOfLastTranslation().get());
    }

    @Test
    void testTranslateWrongLanguage() {
        assertThrows(IllegalArgumentException.class, () -> translator.translateLineToLanguage(ENGLISH_TEXT, "en", "en"));
    }

    @Test
    void testGetSourceLanguageOfLastTranslation(){
        assertTrue(translator.getSourceLanguageOfLastTranslation().isEmpty());
    }

    @Test
    void testTranslateSourceCodeToLanguage(){
        assertTrue(translator.translateSourceCodeToLanguage("test").isEmpty());
    }

    @Test
    void testTranslateTargetCodeToLanguage(){
        assertTrue(translator.translateTargetCodeToLanguage("test").isEmpty());
    }

}