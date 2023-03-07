package core;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Locale;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
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
        writeToFile(targetPath,w.getRepresentation(depth, translateLanguageToCode(targetLanguage)));
    }

    public static void writeToFile(String path, String text){
        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(new File(path)));
            bw.write(text);
            bw.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static String translateLanguageToCode(String language){
        switch(language){
            case "Bulgarian":
                return "BG";
            case "Czech":
                return "CS";
            case "Danish":
                return "DA";
            case "German":
                return "DE";
            case "Greek":
                return "EL";
            case "English":
                return "EN-GB";
            case "Spanish":
                return "ES";
            case "Estonian":
                return "ET";
            case "Finnish":
                return "FI";
            case "French":
                return "FR";
            case "Hungarian":
                return "HU";
            case "Indonesian":
                return "ID";
            case "Italian":
                return "IT";
            case "Japanese":
                return "JA";
            case "Korean":
                return "KO";
            case "Lithuanian":
                return "LT";
            case "Latvian":
                return "LV";
            case "Norwegian":
                return "NB";
            case "Dutch":
                return "NL";
            case "Polish":
                return "PL";
            case "Portuguese":
                return "PT-PT";
            case "Romanian":
                return "RO";
            case "Russian":
                return "RU";
            case "Slovak":
                return "SK";
            case "Slovenian":
                return "SL";
            case "Swedish":
                return "SV";
            case "Turkish":
                return "TR";
            case "Ukrainian":
                return "UK";
            case "Chinese":
                return "ZH";

        }
        System.out.println("Invalid language");
        System.exit(0);
        return "";
    }

    public static String translateCodeToLanguage(String code){
        code = code.toUpperCase();
        switch(code){
            case "BG":
                return "Bulgarian";
            case "CS":
                return "Czech";
            case "DA":
                return "Danish";
            case "DE":
                return "German";
            case "EL":
                return "Greek";
            case "EN":
                return "English";
            case "EN-GB":
                return "English";
            case "EN-US":
                return "English";
            case "ES":
                return "Spanish";
            case "ET":
                return "Estonian";
            case "FI":
                return "Finnish";
            case "FR":
                return "French";
            case "HU":
                return "Hungarian";
            case "ID":
                return "Indonesian";
            case "IT":
                return "Italian";
            case "JA":
                return "Japanese";
            case "KO":
                return "Korean";
            case "LT":
                return "Lithuanian";
            case "LV":
                return "Latvian";
            case "NB":
                return "Norwegian";
            case "NL":
                return "Dutch";
            case "PL":
                return "Polish";
            case "PT":
                return "Portuguese";
            case "PT-PT":
                return "Portuguese";
            case "PT-BR":
                return "Portuguese";
            case "RO":
                return "Romanian";
            case "RU":
                return "Russian";
            case "SK":
                return "Slovak";
            case "SL":
                return "Slovenian";
            case "SV":
                return "Swedish";
            case "TR":
                return "Turkish";
            case "UK":
                return "Ukrainian";
            case "ZH":
                return "Chinese";

        }
        System.out.println("Invalid Code "+code);
        System.exit(0);
        return "";
    }
}
