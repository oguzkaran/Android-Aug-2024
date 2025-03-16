package org.csystem.util.datetime.random;

import java.time.LocalDate;
import java.util.Random;

/**
 * Utility class for generating random dates.
 */
public final class DateTimeUtil {
    private DateTimeUtil()
    {}

    private static final int [] DAYS = {0, 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31};

    /**
     * Checks if a given year is a leap year.
     *
     * @param year the year to check
     * @return true if the year is a leap year, false otherwise
     */
    public static boolean isLeapYear(int year)
    {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * Generates a random date for the current year.
     *
     * @param r the Random instance to use for generating the date
     * @return a random LocalDate for the current year
     */
    public static LocalDate randomDate(Random r)
    {
        return randomDate(r, LocalDate.now().getYear());
    }

    /**
     * Generates a random date for a specified year.
     *
     * @param r the Random instance to use for generating the date
     * @param year the year for which to generate the date
     * @return a random LocalDate for the specified year
     */
    public static LocalDate randomDate(Random r, int year)
    {
        return randomDate(r, year, year);
    }

    /**
     * Generates a random date within a specified range of years.
     *
     * @param r the Random instance to use for generating the date
     * @param minYear the minimum year (inclusive)
     * @param maxYear the maximum year (inclusive)
     * @return a random LocalDate within the specified range of years
     */
    public static LocalDate randomDate(Random r, int minYear, int maxYear)
    {
        int year = r.nextInt(maxYear - minYear + 1) + minYear;
        int month = r.nextInt(12) + 1;
        int day = r.nextInt(month == 2 && isLeapYear(year) ? 29 : DAYS[month]) + 1;

        return LocalDate.of(year, month, day);
    }

    //...
}