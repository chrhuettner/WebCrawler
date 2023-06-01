package core;

import io.Log;
import parser.Parser;
import translator.Translator;
import java.util.Optional;

public class CrawlThread extends Thread{
    private String url;
    private String targetLanguage;
    private String result;
    private int depth;
    private ThreadOrganizer manager;
    private Log errorLog;

    public CrawlThread(String url, String targetLanguage, int depth, ThreadOrganizer manager) {
        this.url = url;
        this.targetLanguage = targetLanguage;
        this.depth = depth;
        this.manager = manager;
        this.errorLog = Log.getLog();
    }

    @Override
    public void run() {
        Translator deeplTranslator = Translator.getTranslator();
        WebsiteCrawler w = new WebsiteCrawler(url, Parser.getParser(), deeplTranslator);
        Optional<String> languageCode = deeplTranslator.translateLanguageToCode(targetLanguage);
        if(languageCode.isEmpty()){
            errorLog.logError("Unexpected target Language. Could not translate to "+targetLanguage);
            return;
        }
        result = w.createCrawlRepresentation(depth, languageCode.get());
        manager.incrementFinishedThreads();
    }

    //This method should only be called after the Thread finished
    public String getResult() {
        return result;
    }
}
