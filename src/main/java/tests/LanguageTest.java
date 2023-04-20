package tests;

import com.deepl.api.DeepLException;
import core.Language;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import static org.junit.jupiter.api.Assertions.*;

class LanguageTest {

    private Language language;
    private static final double ratio = 0.555555;
    private static final String languageName = "English";
    private static final int ratioModifier = 10;
    private static final String nameModifier = "changed";

    @BeforeEach
    void setup() {
        language = new Language(ratio, languageName);
    }

    @Test
    void testGetRatio() {
        assertEquals(language.getRatio(), ratio);
    }

    @Test
    void testSetRatio() {
        language.setRatio(ratio + ratioModifier);
        assertEquals(language.getRatio(), ratio + ratioModifier);
    }

    @Test
    void testGetName() {
        assertEquals(language.getName(), languageName);
    }

    @Test
    void testSetName() {
        language.setName(languageName + nameModifier);
        assertEquals(language.getName(), languageName + nameModifier);
    }

    @Test
    void testCompareToSmaller() {
        Language languageWithSmallerRatio = new Language(ratio / 2.0, "");
        assertEquals(language.compareTo(languageWithSmallerRatio), -1);
    }

    @Test
    void testCompareToGreater() {
        Language languageWithGreaterRatio = new Language(ratio * 2.0, "");
        assertEquals(language.compareTo(languageWithGreaterRatio), 1);
    }

    @Test
    void testCompareToEqual() {
        Language languageWithEqualRatio = new Language(ratio, "");
        assertEquals(language.compareTo(languageWithEqualRatio), 0);
    }

    @Test
    void testToString() {
        assertEquals(language.toString(), language.getName() + " (" + trimRatioString(language.getRatio(), 2) + "%)");
    }

    @Test
    void testTrimRatio() {
        Method method = null;
        try {
            method = Language.class.getDeclaredMethod("trimRatio", int.class);
            method.setAccessible(true);
            assertEquals(trimRatioString(language.getRatio(), 2), method.invoke(language, 2));
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }

    @Test
    void testTranslateSourceCodeToLanguage() {
        assertEquals(Language.translateSourceCodeToLanguage("en"), "English");
        assertEquals(Language.translateSourceCodeToLanguage("de"), "German");
    }
    @Test
    void testTranslateTargetCodeToLanguage() {
        assertEquals(Language.translateTargetCodeToLanguage("en-GB"), "English (British)");
        assertEquals(Language.translateTargetCodeToLanguage("de"), "German");
    }

    @Test
    void translateCodeToLanguage() {
        assertEquals(Language.translateLanguageToCode("English"), "en-GB");
        assertEquals(Language.translateLanguageToCode("German"), "de");
    }

    private String trimRatioString(double ratio, int decimals) {
        String ratioString = (language.getRatio() * 100 + "");
        int indexOfComma = ratioString.indexOf(".") + 1;

        if (indexOfComma != -1) {
            ratioString = ratioString.substring(0, Math.min(ratioString.length(), indexOfComma + 2));
        }
        return ratioString;
    }
}
