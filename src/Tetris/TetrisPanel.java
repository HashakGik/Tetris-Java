package Tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.BitSet;
import java.util.LinkedList;

/**
 * Custom JPanel derived class. It overrides the default paint behavior, creates and manages the Tetris instance (the model is NOT decoupled from its representation).
 */
public class TetrisPanel extends JPanel {
    private Timer timer1, timer2;
    private Tetris tetris;
    private IGameOver go;
    private ILevelUp lu;
    private Image a, b;
    private JLabel stat;
    private boolean movingLeft, movingRight, movingDown;
    
    public void setMovingLeft(boolean b) {
        this.movingLeft = b;
    }
    
    public void setMovingRight(boolean b) {
        this.movingRight = b;
    }
    public void setMovingDown(boolean b) {
        this.movingDown = b;
    }
    
    public void setDropping(boolean b) {
        this.tetris.setDropping(b);
    }
    
    public void rotateLeft() {
        this.tetris.rotateLeft();
    }
    
    public void rotateRight() {
        this.tetris.rotateRight();
    }
    
    /**
     * Constructor. Creates the Tetris instance and the timers, then attaches the GameOver and LevelUp Commands.
     * @param stat JLabel which will print the game statistics.
     */
    public TetrisPanel(JLabel stat) {
        super();
        
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.stat = stat;
        this.a = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("A.png"));
        this.b = Toolkit.getDefaultToolkit().getImage(this.getClass().getResource("B.png"));
        
        this.tetris = new Tetris(10, 20, 3); // Initial level: 3.
        
        // Game timer: determines the playing speed.
        this.timer1 = new Timer(1000 / 3, new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    tetris.update();
                    stat.setText("Score:  " + tetris.getScore() + " Lines: " + tetris.getLines() + " Lv: " + tetris.getLevel() + " Next: " + tetris.getNext().toString());
                    repaint();
                }
            });
        
        // Input timer: determines the frequency of repeated inputs.
        this.timer2 = new Timer(100, new ActionListener() {
           public void actionPerformed(ActionEvent e) {
               if (movingLeft) {
                   tetris.moveLeft();
                   repaint();
               }
               if (movingRight) {
                   tetris.moveRight();
                   repaint();
               }
               if (movingDown) {
                   tetris.moveDown();
                   repaint();
               }
           } 
        });
        
            timer1.start();
            timer2.start();
            this.go = new GameOver(this.timer1, this.timer2);
            this.lu = new LevelUp(this.timer1);
            this.tetris.attachGameOver(this.go);
            this.tetris.attachLevelUp(this.lu);
            this.movingLeft = false;
            this.movingRight = false;
            this.movingDown = false;
    }
    
    /**
     * Paints the JPanel. Overrides the default behavior by drawing an image for each occupied block of the playing field.
     * @param g The Graphics instance associated to this component.
     */
    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        LinkedList<BitSet> field = this.tetris.getImage();
        int w = this.tetris.getWidth();
        int h = this.tetris.getHeight();
        
        for (int i = 0; i < w; i++)
            for (int j = 0; j < h; j++)
                if (field.get(j).get(i))
                    g.drawImage(a, 20 * i, 20 * (h - 1 - j), this);
    }
}
