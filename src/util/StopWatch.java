/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 * This class is the stop watch class. It can store elapsed time and show the result as hours, minutes, and seconds.
 * @author Knacky
 */
public class StopWatch {

    private long start;
    private long stop;

    public void start() {
        start = System.currentTimeMillis(); // start timing
    }

    public void stop() {
        stop = System.currentTimeMillis(); // stop timing
    }

    public long elapsedTimeMillis() {
        return stop - start;
    }

    public String toString() {
        long timeInSeconds = (long) this.elapsedTimeMillis() / 1000;
        int hours, minutes, seconds;
        hours = (int) (timeInSeconds / 3600);
        timeInSeconds = timeInSeconds - (hours * 3600);
        minutes = (int) (timeInSeconds / 60);
        timeInSeconds = timeInSeconds - (minutes * 60);
        seconds = (int) timeInSeconds;

        return "Time Used: " + hours + " hour(s) " + minutes + " minute(s) " + seconds + " second(s) OR " +"elapsedTimeMillis: " + Long.toString(this.elapsedTimeMillis());
    }
}

