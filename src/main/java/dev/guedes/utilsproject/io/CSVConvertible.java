package main.java.dev.guedes.utilsproject.io;

/**
 * Defines an interface for objects that can be converted to a string in CSV format.
 * Any class that implements this interface must provide methods to represent
 * its attributes as a CSV string and provide a CSV header.
 *
 * @author João Guedes
 */
public interface CSVConvertible {

    /**
     * Converts the object that implements this interface to a string in CSV format.
     * This method should map the object's attributes into a CSV-formatted string.
     *
     * @return A string in CSV format representing the object's attributes.
     */
    String toCSV();

    /**
     * Returns the CSV header associated with the class that implements this interface.
     * This header typically consists of the column names that represent the object's attributes.
     *
     * @return A string representing the CSV header (column names).
     */
    String csvHeader();

}
