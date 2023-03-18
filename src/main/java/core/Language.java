package core;

public class Language implements Comparable<Language>{
    private double ratio;
    private String name;

    public Language(double ratio, String name) {
        this.ratio = ratio;
        this.name = name;
    }

    public double getRatio() {
        return ratio;
    }

    public void setRatio(double ratio) {
        this.ratio = ratio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int compareTo(Language o) {
        if(this.ratio > o.getRatio()){
            return -1;
        }
        if(this.ratio == o.getRatio()){
            return 0;
        }
        return 1;
    }

    @Override
    public String toString() {
        return name+" ("+trimRatio(2)+"%)";
    }

    private String trimRatio(int decimals){
        double scaledRatio = ratio * 100;
        String representation = ""+scaledRatio;
        if(representation.contains(".")){
            representation = representation.substring(0, Math.min(representation.length(),
                    representation.indexOf(".")+decimals+1));
        }
        return representation;
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
        return "";
    }
}
