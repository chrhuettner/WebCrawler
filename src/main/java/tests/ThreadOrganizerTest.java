package tests;

import core.ThreadOrganizer;
import io.Log;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ThreadOrganizerTest {

    private ThreadOrganizer[] wrongSetUp(){
        Log.getLog().getLoggedErrors().clear();
        ThreadOrganizer threadOrganizerDepthWrong = new ThreadOrganizer(new String[]{"ssd","dsd"},new int[]{1},new String[]{"ssd","dsd"});
        ThreadOrganizer threadOrganizerUrlsWrong = new ThreadOrganizer(new String[]{"ssd","ds"},new int[]{1,1},new String[]{"ssd"});
        return new ThreadOrganizer[]{threadOrganizerDepthWrong, threadOrganizerUrlsWrong};
    }

    @Test
    void testWrongDepthConstructorCreate() {
        ThreadOrganizer threadOrganizer = wrongSetUp()[0];
        assertEquals("Invalid parameters for Threadmanager. Expected equal size for all arrays.",Log.getLog().getLoggedErrors().get(0));
    }

    @Test
    void testWrongUrlConstructorCreate() {
        ThreadOrganizer threadOrganizer = wrongSetUp()[1];
        assertEquals("Invalid parameters for Threadmanager. Expected equal size for all arrays.",Log.getLog().getLoggedErrors().get(0));
    }

    @Test
    void testWrongDepthCrawlWebsitesInParallel() {
        ThreadOrganizer threadOrganizer = wrongSetUp()[0];
        assertEquals("",threadOrganizer.crawlAllWebsitesInParallel());
    }

    @Test
    void testCorrectConstructorCreate() {
        Log.getLog().getLoggedErrors().clear();
        ThreadOrganizer threadOrganizer = new ThreadOrganizer(new String[]{"https://orf.at","https://orf.at"},new int[]{0,0},new String[]{"Dutch","German"});
        assertTrue(Log.getLog().getLoggedErrors().isEmpty());
    }

}