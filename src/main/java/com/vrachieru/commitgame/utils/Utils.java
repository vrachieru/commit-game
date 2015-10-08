package com.vrachieru.commitgame.utils;

import java.util.Random;

import org.joda.time.DateTime;
import org.joda.time.Period;
import org.joda.time.format.PeriodFormatter;
import org.joda.time.format.PeriodFormatterBuilder;

public class Utils {
  public static void clearConsole() {
    try {
      String os = System.getProperty("os.name");

      if (os.contains("Windows")) {
        System.out.println("\n--------------------------------------------------------------\n");
        Runtime.getRuntime().exec("cls");
      } else {
        System.out.print("\033[H\033[2J");
        Runtime.getRuntime().exec("clear");
      }
    } catch (Exception e) {

    }
  }

  public static void sleep(Integer seconds) {
    try {
      Thread.sleep(seconds * 1000);
    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
    }
  }

  public static int getRandomNumber(int max) {
    Random random = new Random();

    return random.nextInt(max);
  }

  public static double successPercentage(int success, int total) {
    double percent = total > 0 ? (success * 100.0 / total) : 0;

    return Math.round(percent * 100.0) / 100.0;
  }

  public static String timeAgo(int timestamp) {
    int unitCount = 0;

    String separator = " ";

    Period period = new Period(new DateTime(timestamp * 1000L), new DateTime());
    PeriodFormatterBuilder builder = new PeriodFormatterBuilder();

    if (unitCount < 2 && period.getYears() > 0) {
      builder.appendYears().appendSuffix(" year", " years").appendSeparator(separator);
      unitCount++;
    }

    if (unitCount < 2 && period.getMonths() > 0) {
      builder.appendMonths().appendSuffix(" month", " months").appendSeparator(separator);
      unitCount++;
    }

    if (unitCount < 2 && period.getWeeks() > 0) {
      builder.appendWeeks().appendSuffix(" week", " weeks").appendSeparator(separator);
      unitCount++;
    }

    if (unitCount < 2 && period.getHours() > 0) {
      builder.appendHours().appendSuffix(" hour", " hours").appendSeparator(separator);
      unitCount++;
    }

    if (unitCount < 2 && period.getMinutes() > 0) {
      builder.appendMinutes().appendSuffix(" minute", " minutes").appendSeparator(separator);
      unitCount++;
    }

    if (unitCount < 2 && period.getSeconds() > 0) {
      builder.appendSeconds().appendSuffix(" second", " seconds").appendSeparator(separator);
      unitCount++;
    }

    PeriodFormatter formatter = builder.printZeroNever().toFormatter();

    return formatter.print(period) + " ago";
  }
}
