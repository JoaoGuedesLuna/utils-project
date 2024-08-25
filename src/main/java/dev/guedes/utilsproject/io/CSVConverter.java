package main.java.dev.guedes.utilsproject.io;

import main.java.dev.guedes.utilsproject.exception.CSVFileException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * This class provides methods to convert an object into a CSV string or vice versa.
 *
 * @author João Guedes
 */
public class CSVConverter {

    /**
     * Converts a list of objects into a string in CSV format.
     *
     * @param fields List of objects
     *
     * @return A string in CSV format.
     */
    public static String toCSV(Object... fields) {
        return String.join(",", Arrays.stream(fields)
                .map(CSVConverter::convertFieldToCSVString)
                .toArray(String[]::new));
    }

    /**
     * Converts a field to a String representation appropriate for CSV.
     *
     * @param field the field to be converted
     *
     * @return the String representation of the field, escaped to CSV
     */
    private static String convertFieldToCSVString(Object field) {
        if (field == null) return "";

        return CSVConverter.escape(field.toString());
    }

    /**
     * Converts a line of CSV-formatted string into an object.
     *
     * @param parseFunction Implementation of the function that will actually convert the
     * string into the object passed as a parameter. The function takes a vector of strings,
     * where each position corresponds to a field in the CSV-formatted line.
     *
     * @param csvLine CSV-formatted string
     *
     * @return An object.
     *
     * @param <R> Type of the returned object.
     */
    public static  <R> R toObject(CSVFunction<R> parseFunction, String csvLine) throws CSVFileException {
        String[] fields = CSVConverter.unescapeLine(csvLine);
        return parseFunction.apply(fields);
    }

    /**
     * Method that escapes double quotes in a string and surrounds it with double quotes if it contains commas.
     *
     * @param field Field
     *
     * @return String escaped.
     */
    private static String escape(String field) {
        String auxField = field.replace("\"", "\"\"");

        if (field.contains(",")) return "\"" + auxField + "\"";

        return auxField;
    }

    /**
     * Method that unescapes a string and removes the surrounding quotes if it contains commas.
     *
     * @param field Field
     *
     * @return String unescaped
     */
    private static String unescape(String field) {
        if (field.startsWith("\"") && field.endsWith("\"") && field.contains(",")) {
            field = field.substring(1, field.length() - 1);
        }

        return field.replace("\"\"", "\"");
    }

    /**
     * Unescapes an entire CSV line and returns an array of strings corresponding to the fields.
     *
     * @param csvLine CSV line
     *
     * @return Array of strings corresponding to the fields of the CSV line.
     */
    public static String[] unescapeLine(String csvLine) {
        List<String> fields = new ArrayList<>();
        StringBuilder currentField = new StringBuilder();
        boolean insideQuotes = false;

        for (char c : csvLine.toCharArray()) {
            if (c == '\"') {
                if (currentField.isEmpty()) {
                    insideQuotes = true;
                } else {
                    insideQuotes = !insideQuotes;
                }
                currentField.append(c);
            } else if (c == ',' && !insideQuotes) {
                fields.add(unescape(currentField.toString()));
                currentField.setLength(0);
            } else {
                currentField.append(c);
            }
        }

        if (!currentField.isEmpty() || csvLine.endsWith(",")) {
            fields.add(unescape(currentField.toString()));
        }

        return fields.toArray(String[]::new);
    }

}
