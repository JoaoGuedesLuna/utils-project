package main.java.dev.guedes.utilsproject.util;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Utility class that provides methods returning system information such as resolution, date, time, language...
 *
 * @author João Guedes
 */
public class SystemInfo {

    /**
     * Returns the current screen resolution (width x height).
     *
     * @return Returns the current screen resolution (width x height).
     */
    public static String getSystemScreenResolution() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return String.format("%d x %d", screenSize.width, screenSize.height);
    }

    /**
     * Returns the current system language.
     *
     * @return Returns the current system language.
     */
    public static String getSystemLanguage() {
        Locale locale = Locale.getDefault();
        return locale.getDisplayLanguage();
    }

    /**
     * Returns the current system time information in the following format: Mon. Oct 10 14:00:03 BRT 2022.
     *
     * @return Returns the current system time information.
     */
    public static String getSystemTime() {
        Date date = new Date();
        return date.toString();
    }

    /**
     * Returns the current system time in milliseconds.
     *
     * @return Returns the current system time in milliseconds.
     */
    public static Long getSystemTimeMillis() {
        return System.currentTimeMillis();
    }

    /**
     * Returns the current system date using methods from the Calendar class. Example: mm-dd-yyyy.
     *
     * @return Returns the current system date using methods from the Calendar class. Example: mm-dd-yyyy.
     */
    public static String getSystemDateCalendar() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.format("%d-%d-%d", month, day, year);
    }

    /**
     * Returns the current system date using methods from the LocalDate class. Example: mm-dd-yyyy.
     *
     * @return Returns the current system date using methods from the LocalDate class. Example: mm-dd-yyyy.
     */
    public static String getSystemDateLocalDate() {
        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return String.format("%d-%d-%d", month, day, year);
    }

    /**
     * Returns the current system date using methods from the YearMonth class. Example: mm-yyyy.
     *
     * @return Returns the current system date using methods from the YearMonth class. Example: mm-yyyy.
     */
    public static String getSystemDateYearMonth() {
        YearMonth yearMonth = YearMonth.now();
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();
        return String.format("%d-%d", month,year);
    }

}
