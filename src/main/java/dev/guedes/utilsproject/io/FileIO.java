package main.java.dev.guedes.utilsproject.io;

import main.java.dev.guedes.utilsproject.exception.CSVFileException;
import java.io.*;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * This class is responsible for reading and writing data in files.
 *
 * @author João Guedes
 */
public class FileIO {

    /**
     * Writes a string content in a file specified by the variable path.
     *
     * @param content String content to write
     *
     * @param filePath File path to write
     *
     * @param append true to add keeping the current content of the file or false to overwrite
     * the current content of the file.
     *
     * @throws IOException Throws an exception if the specified path is invalid.
     */
    public static void write(String content, String filePath, boolean append) throws IOException {
        try (var fileWriter = new FileWriter(filePath, append);
             var bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(content);
        }
    }

    /**
     * Reads a file and return a string.
     *
     * @param filePath File path to read
     *
     * @return String
     *
     * @throws IOException Throws an exception if the specified path is invalid.
     */
    public static String read(String filePath) throws IOException {
        try (var fileReader = new FileReader(filePath);
             var bufferedReader = new BufferedReader(fileReader)) {
            return bufferedReader.lines().collect(Collectors.joining(System.lineSeparator()));
        }
    }

    /**
     * Edits a line in a file according to the content and line number to be edited passed as a parameter
     *
     * @param content String content to write
     *
     * @param lineIndex Line number to be edited
     *
     * @param filePath File path to write
     *
     * @throws IOException Throws an exception if the specified path is invalid.
     */
    public static void edit(String content, int lineIndex, String filePath) throws IOException {
        List<String> lines;

        try (var fileReader = new FileReader(filePath);
             var bufferedReader = new BufferedReader(fileReader)) {
            lines = new ArrayList<>(bufferedReader.lines().toList());
        }

        if (lineIndex-1 < 0 || lineIndex-1 >= lines.size()) {
            throw new IndexOutOfBoundsException("Line index out of bounds for file " + filePath);
        }

        lines.set(lineIndex-1, content);

        try (var fileWriter = new FileWriter(filePath);
             var bufferedWriter = new BufferedWriter(fileWriter)) {
            bufferedWriter.write(lines.stream().collect(Collectors.joining(System.lineSeparator())));
        }
    }

    /**
     * Writes an obj in CSV format to a file specified by the variable path.
     *
     * @param obj Object
     *
     * @param filePath File path to write
     *
     * @param append True to add keeping the current content of the file or false to overwrite
     * the current content of the file.
     *
     * @param <T> Object type
     *
     * @throws CSVFileException Throws an exception if the specified path is invalid.
     */
    public static <T extends CSVConvertible> void writeToCSV(T obj, String filePath, boolean append) throws CSVFileException {
        try (var fileWriter = new FileWriter(filePath, append);
             var bufferedWriter = new BufferedWriter(fileWriter)) {
            File file = new File(filePath);

            if (file.length() == 0 || !append) {
                bufferedWriter.write(obj.csvHeader().concat(System.lineSeparator()));
            } else {
                bufferedWriter.write(System.lineSeparator());
            }

            bufferedWriter.write(obj.toCSV());
        } catch (IOException e) {
            throw new CSVFileException(e);
        }
    }

    /**
     * Writes a list in CSV format to a file specified by the variable path.
     *
     * @param list Object list
     *
     * @param filePath File path to write
     *
     * @param append true to add keeping the current content of the file or false to overwrite
     * the current content of the file.
     *
     * @param <T> Object list type
     *
     * @throws CSVFileException Throws an exception if the specified path is invalid.
     */
    public static <T extends CSVConvertible> void writeToCSV(List<T> list, String filePath, boolean append) throws CSVFileException {
        try (var fileWriter = new FileWriter(filePath, append);
             var bufferedWriter = new BufferedWriter(fileWriter)) {

            if (list.isEmpty()) {
                bufferedWriter.write("");
                return;
            }

            StringBuilder stringBuilder = new StringBuilder();

            stringBuilder.append(list.get(0).csvHeader());

            list.forEach(csvConvertible -> stringBuilder.append(System.lineSeparator().concat(csvConvertible.toCSV())));

            bufferedWriter.write(stringBuilder.toString());
        } catch (IOException e) {
            throw new CSVFileException(e);
        }
    }

    /**
     * Reads a CSV file and return a list of objects.
     *
     * @param parseFunction A function that transforms a CSV-formatted string into an object
     *
     * @param filePath File path to write
     *
     * @return List of objects
     *
     * @param <T> Object list type
     */
    public static <T> List<T> readFromCSV(CSVFunction<T> parseFunction, String filePath) throws CSVFileException {
        List<T> list = new LinkedList<>();

        try (var fileReader = new FileReader(filePath);
             var bufferedReader = new BufferedReader(fileReader)) {

            bufferedReader.readLine();

            for (String line : bufferedReader.lines().toList()) {
                list.add(CSVConverter.toObject(parseFunction, line));
            }

            return list;
        } catch (FileNotFoundException e) {
            return list;
        } catch (IOException e) {
            throw new CSVFileException(e);
        }
    }

}
