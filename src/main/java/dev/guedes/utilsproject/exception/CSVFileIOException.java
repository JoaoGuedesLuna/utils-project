package main.java.dev.guedes.utilsproject.exception;

/**
 * Custom exception for handling CSV file processing errors.
 * Can be used for both reading and writing operations on CSV files.
 *
 * @author João Guedes
 */
public class CSVFileIOException extends Exception {

    public CSVFileIOException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSVFileIOException(String message) {
        super(message);
    }

    public CSVFileIOException(Throwable cause) {
        super(cause);
    }

    public CSVFileIOException() {

    }

}
