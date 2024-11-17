package main.java.dev.guedes.utilsproject.io;

import java.io.IOException;
import java.util.logging.Logger;

/**
 * Utility class for manipulating the terminal/console, providing methods
 * to clear the console screen.
 *
 * @author João Guedes
 */
public class Console {

    private static final Logger LOGGER = Logger.getLogger(Console.class.getName());

    private Console() {

    }

    /**
     * Clears the console screen by executing the appropriate command
     * based on the operating system.
     */
    public static void clear() {
        try {
            String osName = System.getProperty("os.name").toLowerCase();
            ProcessBuilder processBuilder;

            if (osName.contains("windows")) {
                processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            }
            else {
                processBuilder = new ProcessBuilder("clear");
            }

            Process process = processBuilder.inheritIO().start();
            process.waitFor();

            Console.cls();
        } catch (IOException e) {
           LOGGER.info("Error trying to clear the console: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            LOGGER.info("Interruption while waiting for the process to complete: " + e.getMessage());
        }
    }

    /**
     * Clears the console screen using ANSI escape codes (works in most terminals).
     */
    public static void cls()  {
        System.out.print("\033[H\033[2j");
        System.out.flush();
    }

}
