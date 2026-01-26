package it.unibo.makeanicecream.model;

import it.unibo.makeanicecream.api.Game;
import it.unibo.makeanicecream.api.GameState;
import it.unibo.makeanicecream.api.Level;

public class GameImpl implements Game {

    //private Level currentLevel;
    private GameState state;
    
    @Override
    public void start(final int levelNumber) {
        //this.currentLevel = new LevelImpl();
        this.state = GameState.PLAYING;
    }

    @Override
    public Level getLevel() {
        return this.getLevel();
    }

    @Override
    public void pause() {
        if (this.state == GameState.PLAYING) {
            this.state = GameState.PAUSED;
        }
    }

    @Override
    public void resume() {
        if (this.state == GameState.PAUSED) {
            this.state = GameState.PLAYING;
        }
    }

    @Override
    public boolean isGameOver() {
        return this.state == GameState.GAME_OVER;
    }

}
