package main.java.dev.guedes.utilsproject.io;

/**
 * Defines an interface for objects that can be converted to a string in CSV format.
 *
 * @author João Guedes
 */
public interface CSVConvertible {

    /**
     * Convert the class that implements the interface with its attributes into a string in CSV format.
     *
     * @return A string in CSV format.
     */
    String toCSV();

    /**
     * Returns the CSV header of the class that implements the interface.
     *
     * @return The CSV header of the class that implements the interface
     */
    String csvHeader();

}
