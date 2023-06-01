package tests;

import translator.Translator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private static Translator translator;

    @BeforeAll
    static void setUpTests(){
        translator = Translator.getTranslator();
    }

    @Test
    void testTranslateSourceCodeToLanguage() {
        assertEquals("English", translator.translateSourceCodeToLanguage("en").get());
        assertEquals("German", translator.translateSourceCodeToLanguage("de").get());
    }

    @Test
    void testTranslateTargetCodeToLanguage() {
        assertEquals("English (British)", translator.translateTargetCodeToLanguage("en-GB").get());
        assertEquals("German", translator.translateTargetCodeToLanguage("de").get());
    }

    @Test
    void testTranslateLanguageToCode() {
        assertEquals("en-GB", translator.translateLanguageToCode("English").get());
        assertEquals("de", translator.translateLanguageToCode("German").get());
    }
}