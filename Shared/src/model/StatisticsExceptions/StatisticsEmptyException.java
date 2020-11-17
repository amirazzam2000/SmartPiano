package model.StatisticsExceptions;

public class StatisticsEmptyException extends Exception{
    public StatisticsEmptyException(){
        super("No songs have been played yet");
    }
}
