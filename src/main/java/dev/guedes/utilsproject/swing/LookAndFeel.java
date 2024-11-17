package main.java.dev.guedes.utilsproject.swing;

import javax.swing.*;

/**
 * Utility class for setting the look and feel of Swing UI components.
 *
 * @author João Guedes
 */
public class LookAndFeel {

    // Enum to define available LookAndFeel types
    public enum Type {

        METAL("javax.swing.plaf.metal.MetalLookAndFeel"),
        NIMBUS("com.sun.java.swing.plaf.nimbus.NimbusLookAndFeel"),
        WINDOWS("com.sun.java.swing.plaf.windows.WindowsLookAndFeel"),
        WINDOWS_CLASSIC("com.sun.java.swing.plaf.windows.WindowsClassicLookAndFeel"),
        MOTIF("com.sun.java.swing.plaf.motif.MotifLookAndFeel");

        private final String className;

        Type(String className) {
            this.className = className;
        }

        public String getClassName() {
            return this.className;
        }

    }

    /**
     * Sets the LookAndFeel of the application based on the provided Type.
     *
     * @param lookAndFeelType The LookAndFeel type to be applied.
     */
    public static void setLookAndFeel(Type lookAndFeelType) {
        try {
            UIManager.setLookAndFeel(lookAndFeelType.getClassName());
        }
        catch (Exception ignore) {}
    }

}
