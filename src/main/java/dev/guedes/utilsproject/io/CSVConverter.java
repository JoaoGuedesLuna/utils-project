package main.java.dev.guedes.utilsproject.io;

import main.java.dev.guedes.utilsproject.exception.CSVFileIOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides methods to convert an object into a CSV string and to parse a CSV string back into an object.
 * It handles the conversion, escaping, and unescaping of fields according to CSV formatting rules.
 *
 * <p>It also allows customization through the use of a {@link CSVMapper} for converting CSV data into custom objects.</p>
 *
 * @author João Guedes
 */
public class CSVConverter {

    private CSVConverter() {

    }

    /**
     * Converts a list of fields into a CSV-formatted string, using the specified delimiter.
     * Each field is converted into its CSV-safe string representation, and the resulting strings
     * are joined together with the given delimiter.
     *
     * @param delimiter The delimiter to be used between the fields (e.g., comma for CSV).
     *
     * @param fields The fields (strings) to be converted into a CSV string.
     *
     * @return A string in CSV format, with fields separated by the specified delimiter.
     */
    public static String toCSV(char delimiter, String... fields) {
        return Arrays.stream(fields)
                .map(field -> convertToCSVString(delimiter, field))
                .collect(Collectors.joining(Character.toString(delimiter)));
    }

    /**
     * Converts a CSV-formatted string into an object by mapping each field in the string
     * to the corresponding field in the object using the provided `CSVMapper`.
     * The method assumes that the CSV line has been properly formatted and escaped.
     *
     * @param mapper The function that will convert the CSV fields into the object. It maps the fields (in the form of a string array) to an object of type R.
     *
     * @param delimiter The delimiter used in the CSV format (e.g., comma for CSV).
     *
     * @param csvLine The CSV-formatted line to be converted into an object.
     *
     * @return The object created from the CSV data.
     *
     * @param <R> The type of the object to be returned.
     *
     * @throws CSVFileIOException If there is an error during the parsing of the CSV line.
     */
    public static <R> R toObject(CSVMapper<R> mapper, char delimiter, String csvLine) throws CSVFileIOException {
        String[] fields = CSVConverter.unescapeLine(delimiter, csvLine);
        return mapper.map(fields);
    }

    /**
     * Converts a field (string) into its appropriate CSV-safe string representation.
     * The string is escaped according to CSV rules, including handling the specified delimiter.
     *
     * @param delimiter The delimiter (e.g., comma for CSV) used to determine if the field needs to be enclosed in quotes.
     *
     * @param field The field (string) to be converted into a CSV-safe string.
     *
     * @return The CSV-safe string representation of the field.
     */
    private static String convertToCSVString(char delimiter, String field) {
        if (field == null) {
            return "";
        }
        return CSVConverter.escape(delimiter, field);
    }

    /**
     * Escapes a string for CSV format by replacing double quotes with double double-quotes,
     * and surrounding the string with double quotes if it contains the specified delimiter.
     *
     * @param delimiter The delimiter to check for (e.g., a comma for CSV).
     *
     * @param field The field to escape.
     *
     * @return The escaped string.
     */
    private static String escape(char delimiter, String field) {
        String escapedField = field.replace("\"", "\"\"");

        if (field.contains(Character.toString(delimiter))) {
            escapedField =  "\"" + escapedField + "\"";
        }

        return escapedField;
    }

    /**
     * Unescapes a string for CSV format by removing surrounding quotes (if present) and
     * replacing doubled quotes with a single quote. The surrounding quotes are only removed
     * if the string contains the specified delimiter.
     *
     * @param field The field to unescape.
     *
     * @param delimiter The delimiter to check for (e.g., a comma for CSV).
     *
     * @return The unescaped string.
     */
    private static String unescape(char delimiter, String field) {
        String unescapedField = field;

        if (field.startsWith("\"") && field.endsWith("\"") && field.contains(Character.toString(delimiter))) {
            unescapedField = field.substring(1, field.length() - 1);
        }

        unescapedField = unescapedField.replace("\"\"", "\"");

        return unescapedField;
    }

    /**
     * Unescapes an entire CSV line, splitting it into an array of fields.
     * It processes each character in the CSV line, correctly handling quoted fields and delimiters,
     * and returns an array of strings representing the individual fields.
     *
     * <p>This method ensures that any fields containing the delimiter (e.g., commas in CSV)
     * are properly handled by considering surrounding quotes and escaping rules.</p>
     *
     * @param delimiter The delimiter used to separate fields in the CSV line (e.g., comma for CSV).
     *
     * @param csvLine The CSV line to unescape, which is assumed to follow CSV formatting rules.
     *
     * @return An array of strings, each representing a field from the CSV line, with special characters (like quotes) properly handled.
     */
    private static String[] unescapeLine(char delimiter, String csvLine) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : csvLine.toCharArray()) {
            if (c == '\"') {
                insideQuotes = !insideQuotes;
                currentField.append(c);
            } else if (c == delimiter && !insideQuotes) {
                fields.add(unescape(delimiter, currentField.toString()));
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        if (!currentField.isEmpty() || csvLine.endsWith(Character.toString(delimiter))) {
            fields.add(unescape(delimiter, currentField.toString()));
        }

        return fields.toArray(String[]::new);
    }

}
