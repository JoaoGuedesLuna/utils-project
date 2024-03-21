package main.java.dev.guedes.utilsproject.util;

/**
 * Classe utilitária que disponibiliza uma função para verificar o tempo gasto na execução de um bloco
 * de código.
 *
 * @author João Guedes
 */
public class ExecutionTimeMeter {

    /**
     * Retorna um long correspondente ao tempo gasto em milissegundos na execução de um bloco de código.
     *
     * @param runnable Runnable que contém o bloco de código que será executado.
     *
     * @return Retorna um long correspondente ao tempo gasto em milissegundos na execução de um bloco
     * de código.
     */
    public static Long measureTime(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
