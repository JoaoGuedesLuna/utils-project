package main.java.dev.guedes.utilsproject.util;

/**
 * Utility class to measure the execution time of a given task (Runnable).
 *
 * @author João Guedes
 */
public class ExecutionTimeMeter {

    private ExecutionTimeMeter() {

    }

    /**
     * Measures the time taken to execute a Runnable task.
     *
     * @param task  The task to measure.
     *
     * @return The execution time in milliseconds.
     */
    public static Long measureExecutionTime(Runnable task) {
        long start = System.currentTimeMillis();
        task.run();
        long end = System.currentTimeMillis();
        return end - start;
    }

}
