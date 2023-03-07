package core;

public class Main {

    public static void main(String[] args) {
        WebsiteCrawler w = new WebsiteCrawler("https://en.wikipedia.org/");
        w.crawl(2);
    }
}
