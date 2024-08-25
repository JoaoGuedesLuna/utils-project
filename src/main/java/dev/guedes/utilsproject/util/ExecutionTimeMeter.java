package main.java.dev.guedes.utilsproject.util;

/**
 * Utility class that provides a function to check the time spent executing a block of code.
 *
 * @author João Guedes
 */
public class ExecutionTimeMeter {

    /**
     * Returns a long corresponding to the time spent in milliseconds executing a block of code.
     *
     * @param runnable Runnable containing the block of code to be executed.
     *
     * @return Returns a long corresponding to the time spent in milliseconds executing a block of code.
     */
    public static Long measureTime(Runnable runnable) {
        long startTime = System.currentTimeMillis();
        runnable.run();
        long endTime = System.currentTimeMillis();
        return endTime - startTime;
    }

}
