package main.java.dev.guedes.utilsproject.exception;

/**
 * Custom exception that handles CSV file processing, whether for writing or reading.
 *
 * @author João Guedes
 */
public class CSVFileException extends Exception {

    public CSVFileException(String message, Throwable cause) {
        super(message, cause);
    }

    public CSVFileException(String message) {
        super(message);
    }

    public CSVFileException(Throwable cause) {
        super(cause);
    }

    public CSVFileException() {

    }

}
