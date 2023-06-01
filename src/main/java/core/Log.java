package core;

import java.util.ArrayList;

public class Log {

    private static Log instantiatedLog;

    private ArrayList<String> loggedErrors;

    private static String linePrefix = "<br>";

    private static String logPrefix = "<br> --------- ERRORS --------";

    private Log(){
        this.loggedErrors = new ArrayList<>();
    }

    public static Log getLog(){
        if(instantiatedLog == null){
            instantiatedLog = new Log();
        }

        return instantiatedLog;
    }

    public void logError(String message){
        synchronized (loggedErrors) {
            loggedErrors.add(message);
        }
    }

    public String getErrorsAsString(){
        synchronized (loggedErrors){
            String representation = "";
            if(!loggedErrors.isEmpty()) {
                representation+=logPrefix+System.lineSeparator();
                for (String error : loggedErrors) {
                    representation += linePrefix + error + System.lineSeparator();
                }
            }
            return representation;
        }
    }


}
