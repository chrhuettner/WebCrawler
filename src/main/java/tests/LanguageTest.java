package tests;

import core.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    private Language language;
    private static final double RATIO = 0.555555;
    private static final String LANGUAGE_ENGLISH = "English";
    private static final int RATIO_MODIFIER = 10;
    private static final String NAME_MODIFIER = "changed";

    @BeforeEach
    void setup() {
        language = new Language(RATIO, LANGUAGE_ENGLISH);
    }

    @Test
    void testGetRatio() {
        assertEquals(RATIO, language.getRatio());
    }

    @Test
    void testSetRatio() {
        language.setRatio(RATIO + RATIO_MODIFIER);
        assertEquals(RATIO + RATIO_MODIFIER, language.getRatio());
    }

    @Test
    void testGetName() {
        assertEquals(LANGUAGE_ENGLISH, language.getName());
    }

    @Test
    void testSetName() {
        language.setName(LANGUAGE_ENGLISH + NAME_MODIFIER);
        assertEquals(LANGUAGE_ENGLISH + NAME_MODIFIER, language.getName());
    }

    @Test
    void testCompareToSmaller() {
        Language languageWithSmallerRatio = new Language(RATIO / 2.0, "");
        assertEquals(-1, language.compareTo(languageWithSmallerRatio));
    }

    @Test
    void testCompareToGreater() {
        Language languageWithGreaterRatio = new Language(RATIO * 2.0, "");
        assertEquals(1, language.compareTo(languageWithGreaterRatio));
    }

    @Test
    void testCompareToEqual() {
        Language languageWithEqualRatio = new Language(RATIO, "");
        assertEquals(0, language.compareTo(languageWithEqualRatio));
    }

    @Test
    void testToString() {
        assertEquals(language.getName() + " (" + trimRatioString(language.getRatio(), 2) + "%)", language.toString());
    }

    @Test
    void testTrimRatio() {
        Method method;
        try {
            method = Language.class.getDeclaredMethod("trimRatio", int.class);
            method.setAccessible(true);
            assertEquals(method.invoke(language, 2), trimRatioString(language.getRatio(), 2));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTranslateSourceCodeToLanguage() {
        assertEquals("English", Language.translateSourceCodeToLanguage("en"));
        assertEquals("German", Language.translateSourceCodeToLanguage("de"));
    }

    @Test
    void testTranslateTargetCodeToLanguage() {
        assertEquals("English (British)", Language.translateTargetCodeToLanguage("en-GB"));
        assertEquals("German", Language.translateTargetCodeToLanguage("de"));
    }

    @Test
    void testTranslateCodeToLanguage() {
        assertEquals("en-GB", Language.translateLanguageToCode("English"));
        assertEquals("de", Language.translateLanguageToCode("German"));
    }

    private String trimRatioString(double ratio, int decimals) {
        String ratioString = (ratio * 100 + "");
        int indexOfComma = ratioString.indexOf(".") + 1;
        if (indexOfComma != -1) {
            ratioString = ratioString.substring(0, Math.min(ratioString.length(), indexOfComma + decimals));
        }
        return ratioString;
    }
}
