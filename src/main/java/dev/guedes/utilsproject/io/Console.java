package main.java.dev.guedes.utilsproject.io;

import java.io.IOException;

/**
 * Utility class that provides methods for terminal manipulation.
 *
 * @author João Guedes
 */
public class Console {

    /**
     * Clears the console screen.
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
            System.err.println("Error trying to clear the console: " + e.getMessage());
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            System.err.println("Interruption while waiting for the process to complete: " + e.getMessage());
        }
    }

    /**
     * Clears the console screen using ANSI codes.
     */
    public static void cls()  {
        System.out.print("\033[H\033[2j");
        System.out.flush();
    }

}
