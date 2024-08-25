package main.java.dev.guedes.utilsproject.io;

import main.java.dev.guedes.utilsproject.exception.CSVFileException;

/**
 * Represents a function that accepts one argument (string array) and produces a result.
 *
 * @param <R> The type of the result of the function
 */
@FunctionalInterface
public interface CSVFunction<R> {

    R apply(String[] fields) throws CSVFileException;

}
