package it.unibo.makeanicecream.api;

public interface Game {

    void start(int levelNumber);

    Level getLevel();

    void pause();

    void resume();

    boolean isGameOver();
}