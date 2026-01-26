package it.unibo.makeanicecream.model.level;

import it.unibo.makeanicecream.api.Customer;
import it.unibo.makeanicecream.api.Level;
import java.util.Queue;

/**
 * Factory class for creating Level instances.
 * Levels are created based on the specified difficulty
 * and a queue of customers.
 */
public final class LevelFactory {

    private static final int MIN_DIFFICULTY = 1;
    private static final int MAX_DIFFICULTY = 5;

    private static final int MAX_CUSTOMERS = 15;
    private static final int BASE_CUSTOMERS = 7;
    private static final int STEP_CUSTOMERS = 2;

    private static final int LIVES = 3;

    private LevelFactory() {
    }

    /**
     *
     * Creates a level based on the given difficulty.
     * @param difficulty the difficulty level (1-5)
     * @param customers the customers queue
     *
     * @return a new Level instance
     */
    public static Level createLevel(final int difficulty, final Queue<Customer> customers) {

        final int limitedDifficulty = Math.min(Math.max(difficulty, MIN_DIFFICULTY),MAX_DIFFICULTY);

        final int numberOfCustomers = Math.min(MAX_CUSTOMERS, BASE_CUSTOMERS + STEP_CUSTOMERS*(limitedDifficulty - 1));

        if (customers.size() != numberOfCustomers) {
         throw new IllegalArgumentException(
                "Expected " + numberOfCustomers
                    + " customers, got " + customers.size()
            );
        }
        return new StandardLevel(
            limitedDifficulty,
            LIVES,
            customers
        );
    }
 }
