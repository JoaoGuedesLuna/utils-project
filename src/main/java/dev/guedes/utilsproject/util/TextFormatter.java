package main.java.dev.guedes.utilsproject.util;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Utility class to format text with different styles, foreground and background colors.
 * Provides methods to apply color and style formatting, as well as text alignment and wrapping.
 * This class uses ANSI escape sequences to achieve the desired formatting.
 *
 * @author João Guedes
 */
public class TextFormatter {

    private static final Map<String, String> FOREGROUND_COLORS = new HashMap<>();
    static {
        FOREGROUND_COLORS.put("black", "\u001B[30m");
        FOREGROUND_COLORS.put("red", "\u001B[31m");
        FOREGROUND_COLORS.put("green", "\u001B[32m");
        FOREGROUND_COLORS.put("yellow", "\u001B[33m");
        FOREGROUND_COLORS.put("blue", "\u001B[34m");
        FOREGROUND_COLORS.put("magenta", "\u001B[35m");
        FOREGROUND_COLORS.put("cyan", "\u001B[36m");
        FOREGROUND_COLORS.put("white", "\u001B[37m");

        FOREGROUND_COLORS.put("bright_black", "\u001B[90m");
        FOREGROUND_COLORS.put("bright_red", "\u001B[91m");
        FOREGROUND_COLORS.put("bright_green", "\u001B[92m");
        FOREGROUND_COLORS.put("bright_yellow", "\u001B[93m");
        FOREGROUND_COLORS.put("bright_blue", "\u001B[94m");
        FOREGROUND_COLORS.put("bright_magenta", "\u001B[95m");
        FOREGROUND_COLORS.put("bright_cyan", "\u001B[96m");
        FOREGROUND_COLORS.put("bright_white", "\u001B[97m");
    }

    private static final Map<String, String> BACKGROUND_COLORS = new HashMap<>();
    static {
        BACKGROUND_COLORS.put("black", "\u001B[40m");
        BACKGROUND_COLORS.put("red", "\u001B[41m");
        BACKGROUND_COLORS.put("green", "\u001B[42m");
        BACKGROUND_COLORS.put("yellow", "\u001B[43m");
        BACKGROUND_COLORS.put("blue", "\u001B[44m");
        BACKGROUND_COLORS.put("magenta", "\u001B[45m");
        BACKGROUND_COLORS.put("cyan", "\u001B[46m");
        BACKGROUND_COLORS.put("white", "\u001B[47m");

        BACKGROUND_COLORS.put("bright_black", "\u001B[100m");
        BACKGROUND_COLORS.put("bright_red", "\u001B[101m");
        BACKGROUND_COLORS.put("bright_green", "\u001B[102m");
        BACKGROUND_COLORS.put("bright_yellow", "\u001B[103m");
        BACKGROUND_COLORS.put("bright_blue", "\u001B[104m");
        BACKGROUND_COLORS.put("bright_magenta", "\u001B[105m");
        BACKGROUND_COLORS.put("bright_cyan", "\u001B[106m");
        BACKGROUND_COLORS.put("bright_white", "\u001B[107m");
    }

    private static final Map<String, String> STYLES = new HashMap<>();
    static {
        STYLES.put("bold", "\u001B[1m");
        STYLES.put("faint", "\u001B[2m");
        STYLES.put("italic", "\u001B[3m");
        STYLES.put("underline", "\u001B[4m");
        STYLES.put("double_underline", "\u001B[21m");
        STYLES.put("inverse", "\u001B[7m");
        STYLES.put("hidden", "\u001B[8m");
        STYLES.put("strikethrough", "\u001B[9m");
    }

    private static final String RESET = "\u001B[0m";

    private TextFormatter() {

    }

    /**
     * Formats the given text with the specified foreground color, background color, and styles.
     *
     * @param text The text to format.
     *
     * @param foregroundColor The desired foreground color (e.g., "red", "blue").
     *
     * @param backgroundColor The desired background color (e.g., "yellow", "green").
     *
     * @param styles Array of styles to apply (e.g., "bold", "italic").
     *
     * @return The formatted text with applied styles, colors, and reset code.
     */
    public static String format(String text, String foregroundColor, String backgroundColor, String... styles) {
        checkNotNull(text);

        String styleAnsiCodes = getStyleAnsiCodes(styles);
        String foregroundColorAnsiCode = (foregroundColor == null) ? "" : FOREGROUND_COLORS.getOrDefault(foregroundColor.toLowerCase(), "");
        String backgroundColorAnsiCode = (backgroundColor == null) ? "" : BACKGROUND_COLORS.getOrDefault(backgroundColor.toLowerCase(), "");

        return styleAnsiCodes + foregroundColorAnsiCode + backgroundColorAnsiCode + text + RESET;
    }

    /**
     * Centers the given text within the specified number of columns.
     *
     * @param text the text to center.
     *
     * @param columns the total number of columns to center the text within.
     *
     * @return a string with the text centered within the specified number of columns.
     */
    public static String center(String text, int columns) {
        checkNotNull(text);
        checkPositiveColumns(columns);

        int totalSpaces = columns - text.length();
        if (totalSpaces <= 0) return  text;

        int rightPadding = totalSpaces / 2;
        int leftPadding = totalSpaces - rightPadding;

        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }

    /**
     * Breaks the given text into multiple lines based on the specified maximum number of columns.
     * The text will be wrapped such that no line exceeds the given column width, and words will not
     * be split across lines.
     *
     * @param text The text to wrap. It should not be null or empty.
     *
     * @param columns The maximum number of columns per line. This value must be positive.
     *
     * @return An array of strings, where each string represents a wrapped line of text.
     *         The lines will not exceed the specified number of columns.
     *
     * @throws NullPointerException If the input text is null.
     *
     * @throws IllegalArgumentException If the number of columns is less than or equal to zero.
     */
    public static String[] wrap(String text, int columns) {
        checkNotNull(text);
        checkPositiveColumns(columns);

        String[] segments = splitIntoWordsAndWhitespaces(text);
        List<String> wrappedText = new ArrayList<>();
        StringBuilder currentLine = new StringBuilder();

        for (String segment : segments) {
            if (!currentLine.isEmpty() && !segment.isBlank() && (currentLine.length() + segment.length() > columns)) {
                wrappedText.add(currentLine.toString());
                currentLine.setLength(0);
            }

            currentLine.append(segment);

            if (currentLine.length() > columns) {
                String[] wrappedLine = wrapLine(currentLine.toString(), columns);
                wrappedText.addAll(Arrays.asList(wrappedLine).subList(0, wrappedLine.length - 1));
                currentLine.setLength(0);
                currentLine.append(wrappedLine[wrappedLine.length - 1]);
            }
        }

        if (!currentLine.isEmpty()) {
            wrappedText.add(currentLine.toString());
        }

        return wrappedText.toArray(new String[0]);
    }

    /**
     * Retrieves the ANSI escape sequences for the given text styles.
     * The method iterates over the provided styles array and appends the corresponding
     * ANSI codes for each style (e.g., "bold", "italic", etc.) to the result string.
     * If a style is not recognized, it is ignored.
     *
     * @param styles An array of style names (e.g., "bold", "italic").
     *
     * @return A string containing the concatenated ANSI escape sequences for the requested styles.
     */
    private static String getStyleAnsiCodes(String[] styles) {
        StringBuilder styleAnsiCodes = new StringBuilder();
        if (styles != null) {
            for (String style : styles) {
                styleAnsiCodes.append(STYLES.getOrDefault(style.toLowerCase(), ""));
            }
        }
        return styleAnsiCodes.toString();
    }

    /**
     * Splits the given input string into words and whitespace segments.
     * It separates words and whitespace sequences, preserving their order.
     *
     * @param text the string to be split into words and whitespace.
     *              
     * @return an array of strings, each representing either a word or a sequence of whitespace.
     */
    private static String[] splitIntoWordsAndWhitespaces(String text) {
        Pattern pattern = Pattern.compile("\\S+|\\s+");
        Matcher matcher = pattern.matcher(text);

        List<String> segments = new ArrayList<>();

        while (matcher.find()) {
            segments.add(matcher.group());
        }

        return segments.toArray(new String[0]);
    }

    /**
     * Wraps a line of text into multiple lines based on the specified column width.
     *
     * @param line the text to wrap.
     *
     * @param columns the number of columns per line.
     *
     * @return an array of strings representing the wrapped text.
     */
    private static String[] wrapLine(String line, int columns) {
        List<String> wrappedLines = new ArrayList<>();

        for (int i = 0; i < line.length(); i += columns) {
            int end = Math.min(line.length(), i + columns);
            wrappedLines.add(line.substring(i, end));
        }

        return wrappedLines.toArray(new String[0]);
    }

    /**
     * Checks if the provided text is null.
     * Throws an exception if it is.
     *
     * @param text the string to check.
     *
     * @throws NullPointerException if text is null.
     */
    private static void checkNotNull(String text) {
        if (text == null) {
            throw new NullPointerException("Text cannot be null.");
        }
    }

    /**
     * Checks if the number of columns is positive.
     * Throws an exception if columns is zero or negative.
     *
     * @param columns the number of columns to check.
     *
     * @throws IllegalArgumentException if columns is zero or negative.
     */
    private static void checkPositiveColumns(int columns) {
        if (columns <= 0) {
            throw new IllegalArgumentException("Columns must be greater than zero.");
        }
    }

}
