package com.vrachieru.commitgame.utils;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class Utils
{
    /**
     * Get random number
     * 
     * @param max the high end of the interval
     * @return random integer between 0 and @max
     */
    public static int getRandomNumber(int max)
    {
        Random random = new Random();

        /* return 3; // Chosen by fair dice roll. */
        return random.nextInt(max);
    }
    
    /**
     * Get success percentage
     * 
     * @param success favorable cases
     * @param total total cases
     * @return success percentage rounded to 2 decimal places
     */
    public static double successPercentage(int success, int total) {
        double percent = total > 0 ? (success * 100.0/total) : 0;
        
        return Math.round(percent * 100.0) / 100.0;
    }

    /**
     * Get time ago
     * 
     * @param timestamp number of seconds elapsed from epoch
     * @return 
     */
    public static String timeAgo(int timestamp)
    {
        int unitCount = 0;

        Period period = new Period(new DateTime(timestamp * 1000L), new DateTime());
        PeriodFormatterBuilder builder = new PeriodFormatterBuilder();

        if (unitCount < 2 && period.getYears() > 0) {
            builder.appendYears().appendSuffix(" year", " years").appendSeparator(" ");
            unitCount++;
        }

        if (unitCount < 2 && period.getMonths() > 0) {
            builder.appendMonths().appendSuffix(" month", " months").appendSeparator(" ");
            unitCount++;
        }

        if (unitCount < 2 && period.getWeeks() > 0) {
            builder.appendWeeks().appendSuffix(" week", " weeks").appendSeparator(" ");
            unitCount++;
        }

        if (unitCount < 2 && period.getHours() > 0) {
            builder.appendHours().appendSuffix(" hour", " hours").appendSeparator(" ");
            unitCount++;
        }

        if (unitCount < 2 && period.getMinutes() > 0) {
            builder.appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(" ");
            unitCount++;
        }

        if (unitCount < 2 && period.getSeconds() > 0) {
            builder.appendSeconds().appendSuffix(" second", " seconds").appendSeparator(" ");
            unitCount++;
        }

        PeriodFormatter formatter = builder.printZeroNever().toFormatter();

        return formatter.print(period) + " ago";
    }
}
