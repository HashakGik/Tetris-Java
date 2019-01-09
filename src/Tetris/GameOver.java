package Tetris;

import javax.swing.*;

/**
 * IGameOver implementation. It stops the JFrame's timers and displays a message.
 */
public class GameOver implements IGameOver {
    private Timer t1, t2;
    
    /**
     * Constructor.
     * @param t1 Timer to be stopped.
     * @param t2 Timer to be stopped.
     */
    public GameOver(Timer t1, Timer t2) {
        this.t1 = t1;
        this.t2 = t2;
    }
    
    /**
     * Stops the game and displays a game over message.
     */
    public void exec() {
        this.t1.stop();
        this.t2.stop();
        JOptionPane.showMessageDialog(null, "Game over");
    }
}
