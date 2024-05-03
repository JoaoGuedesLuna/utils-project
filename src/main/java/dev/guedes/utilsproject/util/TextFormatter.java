package main.java.dev.guedes.utilsproject.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Utility class that contains attributes for string formatting, such as color, bold, underline,
 * and methods for formatting, for example, centering a text.
 *
 * @author João Guedes
 */
public class TextFormatter {

    public static final String RESET = "\u001B[0m";

    public static final String BLACK_FOREGROUND = "\u001B[30m";
    public static final String RED_FOREGROUND = "\u001B[31m";
    public static final String GREEN_FOREGROUND = "\u001B[32m";
    public static final String YELLOW_FOREGROUND = "\u001B[33m";
    public static final String BLUE_FOREGROUND = "\u001B[34m";
    public static final String MAGENTA_FOREGROUND = "\u001B[35m";
    public static final String CYAN_FOREGROUND = "\u001B[36m";
    public static final String WHITE_FOREGROUND = "\u001B[37m";

    public static final String BLACK_BACKGROUND = "\u001B[40m";
    public static final String RED_BACKGROUND = "\u001B[41m";
    public static final String GREEN_BACKGROUND = "\u001B[42m";
    public static final String YELLOW_BACKGROUND = "\u001B[43m";
    public static final String BLUE_BACKGROUND = "\u001B[44m";
    public static final String MAGENTA_BACKGROUND = "\u001B[45m";
    public static final String CYAN_BACKGROUND = "\u001B[46m";
    public static final String WHITE_BACKGROUND = "\u001B[47m";

    public static final String BRIGHT_BLACK_FOREGROUND = "\u001B[90m";
    public static final String BRIGHT_RED_FOREGROUND = "\u001B[91m";
    public static final String BRIGHT_GREEN_FOREGROUND = "\u001B[92m";
    public static final String BRIGHT_YELLOW_FOREGROUND = "\u001B[93m";
    public static final String BRIGHT_BLUE_FOREGROUND = "\u001B[94m";
    public static final String BRIGHT_MAGENTA_FOREGROUND = "\u001B[95m";
    public static final String BRIGHT_CYAN_FOREGROUND = "\u001B[96m";
    public static final String BRIGHT_WHITE_FOREGROUND = "\u001B[97m";

    public static final String BRIGHT_BLACK_BACKGROUND = "\u001B[100m";
    public static final String BRIGHT_RED_BACKGROUND = "\u001B[101m";
    public static final String BRIGHT_GREEN_BACKGROUND = "\u001B[102m";
    public static final String BRIGHT_YELLOW_BACKGROUND = "\u001B[103m";
    public static final String BRIGHT_BLUE_BACKGROUND = "\u001B[104m";
    public static final String BRIGHT_MAGENTA_BACKGROUND = "\u001B[105m";
    public static final String BRIGHT_CYAN_BACKGROUND = "\u001B[106m";
    public static final String BRIGHT_WHITE_BACKGROUND = "\u001B[107m";

    public static final String BOLD = "\u001B[1m";
    public static final String FAINT = "\u001B[2m";
    public static final String ITALIC = "\u001B[3m";
    public static final String UNDERLINE = "\u001B[4m";
    public static final String DOUBLE_UNDERLINE = "\u001B[21m";
    public static final String INVERSE = "\u001B[7m";
    public static final String HIDDEN = "\u001B[8m";
    public static final String STRIKETHROUGH = "\u001B[9m";

    /**
     * Returns a text centered based on the number of columns.
     *
     * @param text Text.
     *
     * @param columns Number of columns.
     *
     * @return Returns a text centered based on the number of columns.
     */
    public static String centralizeText(String text, int columns) {
        if (text.length() >= columns) {
            return text;
        }
        final String BLANK_SPACE = " ";
        int blankSpaceNumbers = columns - text.length();
        String spaceAfter = BLANK_SPACE.repeat(blankSpaceNumbers / 2);
        String spaceBefore = BLANK_SPACE.repeat(blankSpaceNumbers - spaceAfter.length());
        return spaceBefore + text + spaceAfter;
    }

    /**
     * Breaking the text into specific lines based on the number of columns.
     *
     * @param text Text.
     *
     * @param columns Number of columns.
     *
     * @return Returns an array of strings. Each position in the array is a line break.
     */
    public static String[] wrapText(String text, int columns) {
        List<String> wrappedText = new ArrayList<>();
        StringBuilder line = new StringBuilder();
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (!line.isEmpty()) {
                if (line.length() + word.length() + 1 <= columns) {
                    line.append(" ");
                }
                else{
                    wrappedText.add(line.toString());
                    line = new StringBuilder();
                }
            }
            line.append(word);
            if (line.length() > columns) {
                String[] wrappedLine = TextFormatter.wrapLine(line.toString(), columns);
                wrappedText.addAll(Arrays.asList(wrappedLine).subList(0, wrappedLine.length - 1));
                line = new StringBuilder();
                line.append(wrappedLine[wrappedLine.length-1]);
            }
        }
        if (!line.isEmpty()) {
            wrappedText.add(line.toString());
        }
        return wrappedText.toArray(new String[0]);
    }

    /**
     * Breaking a word into specific lines based on the number of columns.
     *
     * @param line Text line.
     *
     * @param columns Number of columns.
     *
     * @return Returns an array of strings. Each position in the array is a line break.
     */
    private static String[] wrapLine(String line, int columns) {
        if (columns <= 0) {
            throw new IllegalArgumentException("Columns must be greater than zero.");
        }
        if (line.length() == columns) {
            return new String[] {line};
        }
        List<String> wrappedLines = new ArrayList<>();
        int beginIndex = 0, endIndex;
        while (beginIndex < line.length()) {
            endIndex = Math.min(line.length(), beginIndex + columns);
            wrappedLines.add(line.substring(beginIndex, endIndex));
            beginIndex += columns;
        }
        return wrappedLines.toArray(new String[0]);
    }

}
