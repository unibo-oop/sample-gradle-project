package it.unibo.makeanicecream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Entry point for the Make an Ice Cream test application.
 * Prints a simple greeting using the configured logger.
 */
public final class Main {

    /**
     * Logger used to output messages instead of System.out.
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

    /**
     * Private constructor to prevent instantiation.
     */
    private Main() {
        // This constructor is intentionally empty.
    }

    /**
     * Launches the application.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(final String[] args) {
        // Using the logger to print to console avoids System.out and passes Checkstyle/PMD.
        LOGGER.info("Hello, World!");
    }
}
