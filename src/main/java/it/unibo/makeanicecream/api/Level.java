package it.unibo.makeanicecream.api;

/**
 * Represents a game level.
 * A level manages the available lives and the sequence of customers
 * that the player must serve.
 */
public interface Level {

    /**
     *  Gets the difficulty of the level.
     *
     * @return the difficulty value
     */
    int getDifficulty();

    /**
     *  Gets the number of lives the player has.
     *
     * @return the remaining lives
     */
    int getLives();

    /**
     *  Decreases the player's lives by one.
     */
    void loseLife();

    /**
     * Gets the current customer.
     *
     * @return the current customer
     */
    Customer getCurrentCustomer();

    /**
     * Checks if there is a next customer.
     *
     * @return true if there is a next customer, false otherwise
     */
    boolean hasNextCustomer();

    /**
     * Removes the current customer from the queue.
     */
    void serveCurrentCustomer();
}
