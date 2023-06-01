package core;

import io.Log;

public class ThreadOrganizer {
    private CrawlThread[] crawlThreads;
    private int finishedThreads;
    private Object threadSynchronizeToken;
    private Log errorLog;

    public ThreadOrganizer(String[] urls, int[] depths, String[] targetLanguages) {
        if (!((urls.length == depths.length) && (depths.length == targetLanguages.length))) {
            throw new RuntimeException("Invalid parameters for Threadmanager. Expected equal size for all arrays.");
        }
        threadSynchronizeToken = "";
        this.finishedThreads = 0;
        createCrawlThreads(urls, depths, targetLanguages);

        errorLog = Log.getLog();
    }

    private void createCrawlThreads(String[] urls, int[] depths, String[] targetLanguages) {
        crawlThreads = new CrawlThread[urls.length];

        for (int i = 0; i < urls.length; i++) {
            crawlThreads[i] = new CrawlThread(urls[i], targetLanguages[i], depths[i], this);
        }
    }

    //Keep in mind that threads can only be started once!
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
            mergedRepresentation += crawlThreads[i].getResult()+System.lineSeparator();
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
                errorLog.logError("ThreadManager got interrupted while waiting for Worker Threads to finish");
            }
        }
    }

    public void incrementFinishedThreads() {
        synchronized (threadSynchronizeToken) {
            finishedThreads++;
        }
    }
}
