package tests;

import translator.DeeplTranslator;
import translator.Translator;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class TranslatorTest {

    private Translator translator;
    @BeforeAll
    void setUpTests(){
        translator = Translator.getTranslator();
    }

    @Test
    void translateLineToLanguage() {
    }

    @Test
    void translateLanguageToCode() {
    }

    @Test
    void translateTargetCodeToLanguage() {
    }

    @Test
    void translateSourceCodeToLanguage() {
    }

    @Test
    void getSourceLanguageOfLastTranslation() {
    }

    @Test
    void testTranslateSourceCodeToLanguage() {
        assertEquals("English", translator.translateSourceCodeToLanguage("en"));
        assertEquals("German", translator.translateSourceCodeToLanguage("de"));
    }

    @Test
    void testTranslateTargetCodeToLanguage() {
        assertEquals("English (British)", translator.translateTargetCodeToLanguage("en-GB"));
        assertEquals("German", translator.translateTargetCodeToLanguage("de"));
    }

    @Test
    void testTranslateLanguageToCode() {
        assertEquals("en-GB", translator.translateLanguageToCode("English"));
        assertEquals("de", translator.translateLanguageToCode("German"));
    }
}