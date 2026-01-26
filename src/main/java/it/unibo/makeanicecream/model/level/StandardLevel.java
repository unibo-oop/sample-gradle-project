package it.unibo.makeanicecream.model.level;

import it.unibo.makeanicecream.api.Customer;
import it.unibo.makeanicecream.api.Level;
import java.util.ArrayDeque;
import java.util.Objects;
import java.util.Queue;

/**
 * Represents a standard game level with a queue of customers.
 */
public final class StandardLevel implements Level {

    private final int difficulty;
    private int lives;
    private final Queue<Customer> customers;

    /**
     * @param difficulty
     *          the level difficulty
     * @param lives
     *          the number of lives
     * @param customers
     *          the customers queue
     */
    public StandardLevel(final int difficulty, final int lives, final Queue<Customer> customers) {
        this.difficulty = difficulty;
        this.lives = lives;
        this.customers = new ArrayDeque<>(Objects.requireNonNull(customers));
    }


    @Override
    public int getDifficulty() {
        return this.difficulty;
    }

    @Override
    public int getLives() {
        return this.lives;
    }

    @Override
    public void loseLife() {
        if (this.lives > 0) {
            this.lives--;
        }
    }

    @Override
    public Customer getCurrentCustomer() {
        return this.customers.peek();
    }

    @Override
    public boolean hasNextCustomer() {
        return !this.customers.isEmpty();
    }

    @Override
    public void serveCurrentCustomer() {
        this.customers.poll();
    }

}
