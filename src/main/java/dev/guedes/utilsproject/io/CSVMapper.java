package main.java.dev.guedes.utilsproject.io;

import main.java.dev.guedes.utilsproject.exception.CSVFileIOException;

/**
 * Represents a function that accepts a string array (fields) as an argument and produces a result of type R.
 * This functional interface is used for operations related to processing CSV data where each field is a string
 * and the result is a transformation or processing of that data.
 *
 * @param <R> The type of the result produced by the function (e.g., an object or value derived from CSV fields).
 *
 * @author João Guedes
 */
@FunctionalInterface
public interface CSVMapper<R> {

    /**
     * Maps a row of CSV data (represented as an array of fields) to a result of type R.
     * This method may throw a {@link CSVFileIOException} if there is an error processing the CSV data.
     *
     * @param fields An array of strings representing the columns of a CSV row. Each string corresponds to a field in the row.
     *
     * @return The result of mapping the CSV row, of type R (e.g., a transformed object or value).
     *
     * @throws CSVFileIOException If there is an error during CSV data processing.
     */
    R map(String[] fields) throws CSVFileIOException;

}
