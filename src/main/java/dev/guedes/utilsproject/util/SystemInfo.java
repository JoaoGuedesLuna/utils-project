package main.java.dev.guedes.utilsproject.util;

import java.awt.Toolkit;
import java.awt.Dimension;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

/**
 * Classe utilitária que disponibiliza métodos que retornam informações do sistema como resolução,
 * data, hora, idioma...
 *
 * @author João Guedes
 */
public class SystemInfo {

    /**
     * Retorna a resolução atual da tela (width x height).
     *
     * @return Retorna a resolução atual da tela (width x height).
     */
    public static String getSystemScreenResolution() {
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Dimension screenSize = toolkit.getScreenSize();
        return String.format("%d x %d", screenSize.width, screenSize.height);
    }

    /**
     * Retorna o idioma atual do sistema.
     *
     * @return Retorna o idioma atual do sistema.
     */
    public static String getSystemLanguage() {
        Locale locale = Locale.getDefault();
        return locale.getDisplayLanguage();
    }

    /**
     * Retorna as informações da hora atual do sistema no seguinte formato: Ex: Mon. Oct 10 14:00:03 BRT 2022
     *
     * @return Retorna as informações da hora atual do sistema.
     */
    public static String getSystemTime() {
        Date date = new Date();
        return date.toString();
    }

    /**
     * Retorna a data atual do sistema usando métodos da classe Calendar. Ex: dd/mm/yyyy.
     *
     * @return Retorna a data atual do sistema usando métodos da classe Calendar. Ex: dd/mm/yyyy.
     */
    public static String getSystemDateCalendar() {
        Calendar calendar = Calendar.getInstance();
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH) + 1;
        int year = calendar.get(Calendar.YEAR);
        return String.format("%d/%d/%d", day, month, year);
    }

    /**
     * Retorna a data atual do sistema usando métodos da classe LocalDate. Ex: dd/mm/yyyy.
     *
     * @return Retorna a data atual do sistema usando métodos da classe LocalDate. Ex: dd/mm/yyyy.
     */
    public static String getSystemDateLocalDate() {
        LocalDate localDate = LocalDate.now();
        int day = localDate.getDayOfMonth();
        int month = localDate.getMonthValue();
        int year = localDate.getYear();
        return String.format("%d/%d/%d", day, month, year);
    }

    /**
     * Retorna a data atual do sistema usando métodos da classe YearMonth. Ex. mm/yyyy.
     *
     * @return Retorna a data atual do sistema usando métodos da classe YearMonth. Ex. mm/yyyy.
     */
    public static String getSystemDateYearMonth() {
        YearMonth yearMonth = YearMonth.now();
        int month = yearMonth.getMonthValue();
        int year = yearMonth.getYear();
        return String.format("%d/%d", month,year);
    }


}