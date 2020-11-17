package model;


/**
 *This class is used to store the statistics and contains a date, number of songs played, total number of minutes.
 *
 * @author Sonia Leal
 */
public class Statistics {
    private static int totalSongs;
    private static double totalMin;
    private final String date;
    private final int numSongs;
    private final float numMinutes;

    public Statistics(String date, int numSongs, float numMinutes) {
        this.date = date;
        this.numSongs = numSongs;
        this.numMinutes = numMinutes;
    }

    public String getDate() {
        return date;
    }

    public int getNumSongs() {
        return numSongs;
    }

    public float getNumMinutes() {
        return numMinutes;
    }

    public static void setTotalSongs(int totalSongs) {
        Statistics.totalSongs = totalSongs;
    }

    public static int getTotalSongs() {
        return totalSongs;
    }

    public static double getTotalMin() {
        return totalMin;
    }

    public static void setTotalMin(double totalMin) {
        Statistics.totalMin = totalMin;
    }
}

