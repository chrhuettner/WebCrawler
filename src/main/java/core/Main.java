package core;

import com.deepl.api.DeepLException;

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
        Scanner scanner = new Scanner(System.in);

        System.out.println("URL: (Make sure to insert the WHOLE url)");
        String url = scanner.nextLine();

        System.out.println("Depth: (Greater than 2 takes very long)");
        int depth = scanner.nextInt();
        scanner.nextLine();

        System.out.println("Language: ");
        String targetLanguage = scanner.nextLine();

        System.out.println("Output Path and file name: (Relative. for example: result.md)");
        String targetPath = scanner.nextLine();

        WebsiteCrawler w = new WebsiteCrawler(url);
        core.FileWriter.writeToFile(targetPath, w.createCrawlRepresentation(depth, Language.translateLanguageToCode(targetLanguage)));
    }
}
