package Tetris;

import javax.swing.*;

/**
 * ILevelUp implementation. It controls the playing speed.
 * @author Luca
 */
public class LevelUp implements ILevelUp {
    private Timer t;
    
    /**
     * Constructor. Stores a Timer reference.
     * @param t The timer which will be modified during a level up.
     */
    public LevelUp(Timer t) {
        this.t = t;
    }
    
    /**
     * Sets the timer's delay according to the new level.
     * @param level The level which has been just reached.
     */
    public void exec(int level) {
        this.t.setDelay((level < 20)? 1000 / level: 50);
    }
    
}
