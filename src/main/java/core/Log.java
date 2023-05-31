package core;

import java.util.ArrayList;

public class Log {

    private static Log instantiatedLog;

    private ArrayList<String> loggedErrors;

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
            for(String error: loggedErrors){
                representation+=error+System.lineSeparator();
            }
            return representation;
        }
    }


}
