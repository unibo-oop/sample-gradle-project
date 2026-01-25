package it.unibo.makeanicecream.api;

/**
 *
 */
public interface Game {

    /**
     * starts the game.
     */
    void start(int levelNumber);

    /**
     * 
     */
    Level getLevel();

    /**
     * 
     */
    void pause();

    /**
     * 
     */
    void resume();

    /**
     * 
     */
    boolean isGameOver();
}
