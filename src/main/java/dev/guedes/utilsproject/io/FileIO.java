package main.java.dev.guedes.utilsproject.io;

import main.java.dev.guedes.utilsproject.exception.CSVFileIOException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class provides utility methods for reading from and writing to files.
 * It supports various file operations, including writing text, editing specific lines, and working with CSV files.
 * It also includes methods for reading and writing CSV content using custom CSVConvertible objects.
 *
 * @author João Guedes
 */
public class FileIO {

    private FileIO() {

    }

    /**
     * Writes the specified content to a file at the given path. If the file already exists,
     * the content is written based on the specified append mode.
     *
     * @param content The content to be written to the file.
     *
     * @param filePath The file path where the content will be written.
     *
     * @param append If true, the content will be appended to the file; if false, the file will be overwritten with the new content.
     *
     * @throws IOException If an I/O error occurs while writing to the file.
     */
    public static void write(String content, String filePath, boolean append) throws IOException {
        try (var writer = new BufferedWriter(new FileWriter(filePath, append))) {
            writer.write(content);
        }
    }

    /**
     * Reads the content of a file from the specified path and returns it as a single String.
     * The content is read line by line and joined with the system's line separator.
     *
     * @param filePath The file path from which to read the content.
     *
     * @return A String containing the entire content of the file.
     *
     * @throws IOException If an I/O error occurs while reading the file.
     */
    public static String read(String filePath) throws IOException {
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            return reader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    /**
     * Replaces the content of a specific line in a file.
     *
     * @param newContent The new content to write to the specified line.
     *
     * @param lineNumber The line number to be edited (1-based index).
     *
     * @param filePath   The path of the file to be modified.
     *
     * @throws IOException If an error occurs while reading or writing to the file.
     *
     * @throws IndexOutOfBoundsException If the specified line number is invalid.
     */
    public static void edit(String newContent, int lineNumber, String filePath) throws IOException {
        List<String> lines;

        try (var reader = new BufferedReader(new FileReader(filePath))) {
            lines = reader.lines().collect(Collectors.toList());
        }

        if (lineNumber < 1 || lineNumber > lines.size()) {
            throw new IndexOutOfBoundsException("Invalid line number: " + lineNumber + " for file: " + filePath);
        }

        lines.set(lineNumber - 1, newContent);

        String updatedContent = String.join(System.lineSeparator(), lines);
        write(updatedContent, filePath, false);
    }

    /**
     * Writes the CSV representation of an object to a specified file.
     * If the file already exists and the append flag is true, the content will be appended to the file;
     * otherwise, the file will be overwritten. If the file is empty (or append is false), the CSV header
     * will be written first, followed by the CSV data for the object.
     *
     * @param object The object to write in CSV format. The object must implement the CSVConvertible interface, which provides methods to generate the CSV header and data.
     *
     * @param filePath The path to the file where the CSV content will be written.
     *
     * @param append If true, the new content will be appended to the file; if false, the file will be overwritten.
     *
     * @param <T> The type of the object, which must extend CSVConvertible.
     *
     * @throws CSVFileIOException If an error occurs while writing to the file, such as an invalid path or other I/O issues.
     */
    public static <T extends CSVConvertible> void writeCSV(T object, String filePath, boolean append) throws CSVFileIOException {
        if (object == null) {
            throw new IllegalArgumentException("CSV object cannot be null.");
        }

        try (var writer = new BufferedWriter(new FileWriter(filePath, append))) {

            if (!append || new File(filePath).length() == 0) {
                writer.write(object.csvHeader());
            }

            writer.newLine();
            writer.write(object.toCSV());
        } catch (IOException e) {
            throw new CSVFileIOException("Error writing to CSV file: " + filePath, e);
        }
    }

    /**
     * Writes a list of objects to a file in CSV format.
     * This method converts the list of objects into CSV format and writes it to the file
     * specified by the filePath. If the append parameter is true, the new data will be
     * appended to the file; otherwise, the existing content will be overwritten.
     *
     * @param objects A list of objects that need to be written to the CSV file. Each object in the list must implement the CSVConvertible interface to provide a CSV representation.
     *
     * @param filePath The path of the file where the CSV data will be written. If the file does not exist, it will be created.
     *
     * @param append A boolean flag indicating whether to append to the existing file content (true) or overwrite it (false).
     *
     * @param <T> The type of objects in the list. The type must be a subtype of CSVConvertible.
     *
     * @throws IllegalArgumentException if the provided list of objects is null.
     *
     * @throws CSVFileIOException if an error occurs while writing to the file, such as invalid file path or I/O issues.
     */
    public static <T extends CSVConvertible> void writeCSV(List<T> objects, String filePath, boolean append) throws CSVFileIOException {
        if (objects == null) {
            throw new IllegalArgumentException("The list of CSV objects cannot be null.");
        }

        try (var writer = new BufferedWriter(new FileWriter(filePath, append))) {

            if (objects.isEmpty()) {
                writer.write("");
                return;
            }

            var csvContent = new StringBuilder();

            if (!append || new File(filePath).length() == 0) {
                csvContent.append(objects.get(0).csvHeader());
            }

            csvContent.append('\n');

            String data = objects.stream()
                    .map(CSVConvertible::toCSV)
                    .collect(Collectors.joining(System.lineSeparator()));
            csvContent.append(data);

            writer.write(csvContent.toString());
        } catch (IOException e) {
            throw new CSVFileIOException("Error writing to CSV file: " + filePath, e);
        }
    }

    /**
     * Reads a CSV file and converts each row into an object using the provided CSVMapper function.
     * This method reads a CSV file, skips a specified number of rows at the beginning (if applicable),
     * and maps each remaining line to an object using the provided mapper function. It uses the given
     * delimiter to parse each line. The result is a list of objects of type T.
     *
     * @param mapper A function that maps a CSV-formatted string into an object of type T. This function is used to transform each CSV row into an object.
     *
     * @param delimiter The character that separates fields in the CSV file (e.g., comma or semicolon). This delimiter is used to split each CSV row into individual fields.
     *
     * @param filePath The path to the CSV file that will be read. The file should be accessible and in a valid format.
     *
     * @param skipRows The number of rows to skip at the beginning of the file. This is useful for skipping header rows or unwanted initial data.
     *
     * @param <T> The type of objects that will be returned in the resulting list. Each object represents a row in the CSV file, transformed by the mapper function.
     *
     * @return A list of objects of type T, each representing a row in the CSV file. If there are no valid rows to process, an empty list is returned.
     *
     * @throws CSVFileIOException If an I/O error occurs during reading the file.
     */
    public static <T> List<T> readCSV(CSVMapper<T> mapper, char delimiter, String filePath, int skipRows) throws CSVFileIOException {
        try (var reader = new BufferedReader(new FileReader(filePath))) {
            List<T> objects = new ArrayList<>();

            for (int i = 0; i < skipRows; i++) {
                reader.readLine();
            }

            for (String line : reader.lines().toList()) {
                objects.add(CSVConverter.toObject(mapper, delimiter, line));
            }

            return objects;
        } catch (IOException e) {
            throw new CSVFileIOException("Error reading the CSV file: " + filePath, e);
        }
    }

}
