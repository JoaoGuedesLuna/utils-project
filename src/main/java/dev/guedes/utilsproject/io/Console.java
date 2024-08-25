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
     *
     * @throws IOException Throws an exception if an I/O error occurs.
     *
     * @throws InterruptedException Throws an exception if the current thread is interrupted by another
     * thread while waiting.
     */
    public static void clear() throws IOException, InterruptedException {
        String osName = System.getProperty("os.name");
        if (osName.contains("Windows")) {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            processBuilder.inheritIO().start().waitFor();
        }
        else {
            Runtime.getRuntime().exec("clear");
        }
        Console.cls();
    }

    /**
     * Clears the console screen using ANSI codes.
     */
    public static void cls()  {
        System.out.print("\033[H\033[2j");
        System.out.flush();
    }

}
