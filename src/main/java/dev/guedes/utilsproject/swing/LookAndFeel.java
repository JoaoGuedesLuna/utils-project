package main.java.dev.guedes.utilsproject.swing;

import javax.swing.*;

/**
 * Class for configuring the look and feel of the application.
 *
 * @author João Guedes
 */
public class LookAndFeel {

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
     * Defines the look and feel of the application.
     */
    public static void setLookAndFeel(Type lookAndFeelType) {
        try {
            UIManager.setLookAndFeel(lookAndFeelType.getClassName());
        }
        catch (Exception ignore) {}
    }

}
