package main.java.dev.guedes.utilsproject.util;

import java.io.IOException;

/**
 * Classe utilitária que disponibiliza métodos para manipulação do terminal.
 *
 * @author João Guedes
 */
public class Console {

    /**
     * Limpa a tela do console.
     *
     * @throws IOException Lança uma exceção se um erro de I/O acorrer.
     *
     * @throws InterruptedException Lança uma exceção se uma thread atual for interrompida por outra
     * thread enquanto estiver esperando.
     */
    public static void clear() throws IOException, InterruptedException {
        String os_name = System.getProperty("os.name");
        if (os_name.contains("Windows")) {
            ProcessBuilder processBuilder = new ProcessBuilder("cmd", "/c", "cls");
            processBuilder.inheritIO().start().waitFor();
        }
        else {
            Runtime.getRuntime().exec("clear");
        }
        Console.cls();
    }

    /**
     * Limpa a tela do console com código ANSI.
     */
    public static void cls()  {
        System.out.print("\033[H\033[2j");
        System.out.flush();
    }

}