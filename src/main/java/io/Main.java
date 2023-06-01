package io;

import core.ThreadOrganizer;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        promptInput();
    }

    public static void promptInput() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("URL: (Seperated by spaces)");
        String url = scanner.nextLine();

        System.out.println("Depth: (Seperated by spaces)");
        String depth = scanner.nextLine();

        System.out.println("Language: (Seperated by spaces)");
        String targetLanguage = scanner.nextLine();

        System.out.println("Output Path and file name: (Relative. for example: result.md)");
        String targetPath = scanner.nextLine();

        String result = interpretInput(url, depth, targetLanguage);
        String resultWithErrors = result+Log.getLog().getErrorsAsString();
        FileWriter.writeToFile(targetPath, resultWithErrors);
/*
        ThreadOrganizer manager = new ThreadOrganizer(new String[]{"https://orf.at"}, new int[]{1}, new String[]{"German"});

        String representation = manager.crawlAllWebsitesInParallel();
        System.out.println(representation);

        FileWriter.writeToFile("test.md", representation+System.lineSeparator()+ Log.getLog().getErrorsAsString());*/
    }

    public static String interpretInput(String url, String depth, String targetLanguage) {
        String[] websites = url.split(" ");
        String[] depthStringArray = depth.split(" ");
        String[] languages = targetLanguage.split(" ");

        int[] depthArray = stringArrayToIntArray(depthStringArray);

        ThreadOrganizer manager = new ThreadOrganizer(websites, depthArray, languages);

        return manager.crawlAllWebsitesInParallel();
    }

    public static int[] stringArrayToIntArray(String[] array) {
        int[] parsedValues = new int[array.length];
        try {
            for (int i = 0; i < array.length; i++) {
                parsedValues[i] = Integer.parseInt(array[i]);
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        return parsedValues;
    }
}
