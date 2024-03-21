package main.java.dev.guedes.utilsproject.util;

/**
 * Classe utilitária que contém atributos para formatação de strings, como cor, negrito, sublinhado e métodos
 * para formatação. Por exemplo, centralizar um texto.
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
     * Retorna um texto centralizado com base no número de colunas.
     *
     * @param text Texto.
     *
     * @param columns Número de colunas.
     *
     * @return Retorna um texto centralizado com base no número de colunas.
     */
    public static String centralizeText(String text, int columns) {
        String formattedText;
        int spaceBefore = (columns - text.length()) / 2;
        int spaceAfter = columns - (spaceBefore + text.length());
        formattedText = String.format("%" + (spaceBefore + text.length()) + "s", text);
        formattedText = String.format("%-" + (formattedText.length() + spaceAfter) + "s", formattedText);
        return formattedText;
    }

    /**
     * Quebrar o texto em linhas específicas com base no número de colunas.
     *
     * @param text Texto.
     *
     * @param columns Número de colunas.
     *
     * @return Retorna um array de string. Cada posição do array é uma quebra de linhas.
     */
    public static String[] wrapText(String text, int columns) {
        StringBuilder formattedText = new StringBuilder();
        String[] lines;
        StringBuilder line = new StringBuilder();
        String[] words = text.split("\\s+");
        for (String word : words) {
            if (!line.isEmpty()) {
                if (line.length() + word.length() + 1 <= columns) {
                    line.append(" ");
                }
                else{
                    formattedText.append(line).append("\n");
                    line = new StringBuilder();
                }
            }
            line.append(word);
            lines = TextFormatter.wrapLine(line.toString(), columns);
            for (int i = 0; i < lines.length-1; i++) {
                formattedText.append(lines[i]).append('\n');
            }
            line = new StringBuilder();
            line.append(lines[lines.length-1]);
        }
        formattedText.append(line);
        return formattedText.toString().split("\n");

    }

    /**
     * Quebrar uma palavra em linhas específicas com base no número de colunas.
     *
     * @param line Linha de texto
     *
     * @param columns Número de colunas.
     *
     * @return Retorna um array de string. Cada posição do array é uma quebra de linhas.
     */
    private static String[] wrapLine(String line, int columns) {
        if (columns <= 0) {
            throw new IllegalArgumentException("Columns must be greater than zero.");
        }
        if (line.length() == columns) {
            return line.split("\n");
        }
        StringBuilder formattedText = new StringBuilder();
        int endIndex;
        for (int beginIndex = 0; beginIndex < line.length(); beginIndex += columns) {
            endIndex = Math.min(line.length(), beginIndex + columns);
            formattedText.append(line, beginIndex, endIndex).append("\n");
        }
        return formattedText.toString().split("\n");
    }

}
