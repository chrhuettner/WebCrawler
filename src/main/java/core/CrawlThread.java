package core;

public class CrawlThread extends Thread{
    private String url;
    private String targetLanguage;
    private String result;
    private int depth;
    private ThreadManager manager;

    public CrawlThread(String url, String targetLanguage, int depth, ThreadManager manager) {
        this.url = url;
        this.targetLanguage = targetLanguage;
        this.depth = depth;
        this.manager = manager;
    }

    @Override
    public void run() {
        WebsiteCrawler w = new WebsiteCrawler(url);
        result = w.createCrawlRepresentation(depth, Language.translateLanguageToCode(targetLanguage));
        manager.incrementFinishedThreads();
    }

    public String getResult() {
        return result;
    }
}
