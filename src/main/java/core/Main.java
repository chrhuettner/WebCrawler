package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        promptInput();
    }

    public static void promptInput() {
        System.out.println("URL: (Make sure to insert the WHOLE url)");
        Scanner scanner = new Scanner(System.in);

        String url = scanner.nextLine();

        System.out.println("Depth: (Greater than 2 takes very long)");
        int depth = scanner.nextInt();
        scanner.nextLine();
        System.out.println("Language: ");
        String targetLanguage = scanner.nextLine();

        System.out.println("Output Path and file name: (Relative. for example: result.md)");
        String targetPath = scanner.nextLine();

        WebsiteCrawler w = new WebsiteCrawler(url);
        writeToFile(targetPath, w.createCrawlRepresentation(depth, Language.translateLanguageToCode(targetLanguage)));
        // System.out.println(Translator.getLimitString());
        // WebsiteCrawler w = new WebsiteCrawler("https://orf.at");
        //writeToFile("out2.md",w.createCrawlRepresentation(2, Language.translateLanguageToCode("English")));
    }

    public static void writeToFile(String path, String text) {
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            bw.write(text);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
