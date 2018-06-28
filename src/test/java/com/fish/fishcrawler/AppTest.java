package com.fish.fishcrawler;

import static org.junit.Assert.assertTrue;
import org.apache.tika.langdetect.OptimaizeLangDetector;
import org.apache.tika.language.detect.LanguageDetector;
import org.apache.tika.language.detect.LanguageResult;
import org.junit.Test;

import java.io.IOException;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }
    @Test
    public void TikaLanguageDetectorTest() throws IOException
    {
        LanguageDetector detector = new OptimaizeLangDetector().loadModels();
        LanguageResult languageResult = detector.detect("Alla människor är födda fria och lika i värde och rättigheter.");
        System.out.println(languageResult.getLanguage());
    }
}
