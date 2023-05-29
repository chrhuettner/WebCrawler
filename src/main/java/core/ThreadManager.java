package core;

public class ThreadManager {
    private CrawlThread[] crawlThreads;
    private int finishedThreads;
    private Object threadSynchronizeToken;

    private static final String websiteSeparationString = "<----------------------------->";

    public ThreadManager(String[] urls, int[] depths, String[] targetLanguages) {
        if (!((urls.length == depths.length) && (depths.length == targetLanguages.length))) {
            throw new RuntimeException("Invalid parameters for Threadmanager. Expected equal size for all arrays.");
        }
        threadSynchronizeToken = "";
        this.finishedThreads = 0;
        createCrawlThreads(urls, depths, targetLanguages);

    }

    private void createCrawlThreads(String[] urls, int[] depths, String[] targetLanguages) {
        crawlThreads = new CrawlThread[urls.length];

        for (int i = 0; i < urls.length; i++) {
            crawlThreads[i] = new CrawlThread(urls[i], targetLanguages[i], depths[i], this);
        }
    }

    public String crawlAllWebsitesInParallel() {
        for (int i = 0; i < crawlThreads.length; i++) {
            crawlThreads[i].start();
        }
        waitForThreadsToFinish();
        return mergeCrawlResults();
    }

    private String mergeCrawlResults() {
        String mergedRepresentation = "";
        for (int i = 0; i < crawlThreads.length; i++) {
            mergedRepresentation += crawlThreads[i].getResult();
            mergedRepresentation+= websiteSeparationString+System.lineSeparator();
        }
        return mergedRepresentation;
    }

    private void waitForThreadsToFinish() {
        while (true) {
            synchronized (threadSynchronizeToken) {
                if (finishedThreads == crawlThreads.length) {
                    break;
                }
            }
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void incrementFinishedThreads() {
        synchronized (threadSynchronizeToken) {
            finishedThreads++;
        }
    }
}
