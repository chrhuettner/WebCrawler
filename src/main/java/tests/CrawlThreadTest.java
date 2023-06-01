package tests;

import core.CrawlThread;
import io.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CrawlThreadTest {

    @Test
    void testRun() {
        Log.getLog().getLoggedErrors().clear();
        CrawlThread crawlThread = new CrawlThread("uujs.at","Dutch2",1,null);
        crawlThread.run();
        assertEquals("Unexpected target Language. Could not translate to Dutch2",Log.getLog().getLoggedErrors().get(0));
    }
}