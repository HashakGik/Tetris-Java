package Tetris;

import java.util.BitSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Random;

/**
 * Tetris class. It keeps track of the playing field, the current tetromino and the next one.
 */
public class Tetris {
    protected int w;
    protected int h;
    protected LinkedList<BitSet> field;
    protected Tetromino current, next;
    protected int x, y;
    protected long score;
    protected int level;
    private boolean dropping;
    private int droppingHeight;
    protected int[] statistics;
    protected int lines;
    protected int consecutiveLinesCleared;
    private Random r;
    protected LinkedList<IGameOver> gameOver;
    protected LinkedList<ILevelUp> levelUp;
    
    public int getWidth() {
        return this.w;
    }
    
    public int getHeight() {
        return this.h;
    }
    
    /**
     * Registers game over Command.
     * @param c Game over Command to be added.
     */
    public void attachGameOver(IGameOver c) {
        this.gameOver.add(c);
    }
    
    /**
     * Unregisters a game over Command.
     * @param c Game over Command to be removed.
     */
    public void detachGameOver(IGameOver c) {
        if (this.gameOver.contains(c))
            this.gameOver.remove(c);
    }
    
    /**
     * Registers a level up Command.
     * @param c Level up Command to be added.
     */
    public void attachLevelUp(ILevelUp c) {
        this.levelUp.add(c);
    }
    
    /**
     * Unregisters a level up Command.
     * @param c Level up Command to be removed.
     */
    public void detachLevelUp(ILevelUp c) {
        if (this.levelUp.contains(c))
            this.levelUp.remove(c);
    }
    
    public void setScore(long s) {
        this.score = s;
    }
    
    public long getScore() {
        return this.score;
    }
    
    public void setLevel(int l) {
        if (l > 0)
            this.level = l;
        else
            this.level = 1;
    }
    
    public int getLevel() {
        return this.level;
    }
    
    public void setDropping(boolean d) {
        this.dropping = d;
    }
    
    public boolean getDropping(){
        return this.dropping;
    }
    
    public int[] getStatistics() {
        return this.statistics;
    }
    
    public void setLines(int l) {
        if (l >= 0)
            this.lines = l;
        else
            this.lines = 0;
    }
    
    public int getLines() {
        return this.lines;
    }
    
    public Tetromino.Type_t getCurrent() {
        return this.current.getType();
    }
    
    public Tetromino.Type_t getNext() {
        return this.next.getType();
    }
    
    /**
     * Constructor. Creates an empty playing field and generates the first two tetrominoes.
     * @param w Width of the playing field.
     * @param h Height of the playing field.
     * @param level Initial level.
     */
    public Tetris(int w, int h, int level) {
        int i;
        
        this.w = w;
        this.h = h;
        this.x = w / 2 - 1;
        this.y = h - 1;
        this.field = new LinkedList<BitSet>();
        for (i = 0; i < h; i++)
            this.field.add(new BitSet(w));
        
        this.gameOver = new LinkedList<IGameOver>();
        this.levelUp = new LinkedList<ILevelUp>();
        
        this.statistics = new int[7];
        for (i = 0; i < 7; i++)
            this.statistics[i] = 0;
        
        this.r = new Random();
        this.current = new Tetromino(Tetromino.Type_t.values()[r.nextInt(7)]);
        this.statistics[this.current.getType().ordinal()]++;
        this.next = new Tetromino(Tetromino.Type_t.values()[r.nextInt(7)]);
        this.score = 0;
        this.level = (level > 0)? level: 1;
        this.lines = 0;
        this.consecutiveLinesCleared = 0;
        this.dropping = false;
        this.droppingHeight = 0;
    }
    
    /**
     * Gets a data structure representing the playing field and the current tetromino.
     * @return Playing field where each block is represented by a single bit.
     */
    public LinkedList<BitSet> getImage() {
        LinkedList<BitSet> ret = new LinkedList<>();
        int i, j;
        
        for (i = 0; i < this.h; i++)
            ret.add(new BitSet(this.w));
        
        for (i = 0; i < this.h; i++)
            for (j = 0; j < this.w; j++)
                ret.get(i).set(j, this.field.get(i).get(j));
        
        // Add the four blocks of the current tetromino.
        for (i = 0; i < 4; i++)
            ret.get(this.y - this.current.get()[i].y).set(this.x + this.current.get()[i].x, true);
        
        return ret;
    }
    
    /**
     * Check if the current tetromino collides with either another block or the playing field's boundaries.
     * @return True if there is a collision.
     */
    private boolean checkCollision() {
        boolean ret = false;
        int i;
        
        for (i = 0; i < 4; i++)
            if (this.x + this.current.get()[i].x >= 0 && this.x + this.current.get()[i].x < this.w &&
                    this.y - this.current.get()[i].y >= 0 && this.y - this.current.get()[i].y < this.h)
                ret |= false;
            else
                ret |= true;
        
        if (!ret)
            for (i = 0; i < 4; i++)
                ret |= this.field.get(this.y - this.current.get()[i].y).get(this.x + this.current.get()[i].x);
        
        return ret;
    }
    
    /**
     * Rotates the tetromino counterclockwise. If there is a collision, it rolls back to the previous state.
     */
    public void rotateLeft() {
        this.current.rotateLeft();
        if (this.checkCollision())
            this.current.rotateRight();
    }
    
    /**
     * Rotates the tetromino clockwise. If there is a collision, it rolls back to the previous state.
     */
    public void rotateRight() {
        this.current.rotateRight();
        if (this.checkCollision())
            this.current.rotateLeft();
    }
    
    /**
     * Moves the tetromino down one block. If there is a collision it has landed and it must check for lines to be cleared and generate a new tetromino.
     */
    public void moveDown() {
        int i;
        
        this.y--;
        
        if (this.dropping)
            this.droppingHeight++;
        else
            this.droppingHeight = 0;
        
        if (this.checkCollision()) {
            this.y++;
            for (i = 0; i < 4; i++)
                this.field.get(this.y - this.current.get()[i].y).set(this.x + this.current.get()[i].x, true);
            
            
            this.consecutiveLinesCleared = 0;
            for (i = 0; i < this.h; )
                if (this.checkLine(i)) {
                    this.removeLine(i);
                    this.lines++;
                    this.consecutiveLinesCleared++;
                }
                else
                    i++;
            
            if (this.dropping) {
                this.score += this.droppingHeight;
                this.droppingHeight = 0;
            }
            
            
            this.current = this.next;
            this.next = new Tetromino(Tetromino.Type_t.values()[r.nextInt(7)]);
            this.statistics[this.current.getType().ordinal()]++;
            this.y = this.h - 1;
            this.x = this.w / 2 - 1;
            
            switch (this.consecutiveLinesCleared) {
                case 1:
                    this.score += 40 * (this.level + 1);
                    break;
                case 2:
                    this.score += 100 * (this.level + 1);
                    break;
                case 3:
                    this.score += 300 * (this.level + 1);
                    break;
                case 4:
                    this.score += 1200 * (this.level + 1);
                    break;
            }
            
            if (this.lines >= this.level * 10 && this.lines < this.level * 11) {
                this.level++;
                for (Iterator<ILevelUp> iter = this.levelUp.iterator(); iter.hasNext();)
                    iter.next().exec(this.level);
            }
            
        }
    }
    
    /**
     * Moves the tetromino left. If there is a collision, it rolls back to the previous state.
     */
    public void moveLeft() {
        this.x--;
        if (this.checkCollision())
            this.x++;
    }
    
    /**
     * Moves the tetromino right. If there is a collision, it rolls back to the previous state.
     */
    public void moveRight() {
        this.x++;
        if (this.checkCollision())
            this.x--;
    }
    
    /**
     * Updates the game. If there is an initial collision (the current tetromino is stuck) fires the gameover, otherwise moves the tetromino downwards.
     */
    public void update() {
        if (this.checkCollision())
            for (Iterator<IGameOver> iter = this.gameOver.iterator(); iter.hasNext();)
                iter.next().exec();
                
        else
            this.moveDown();
    }
    
    /**
     * Checks if a line is full and needs to be cleared.
     * @param i The line to be checked.
     * @return True if the line is full.
     */
    private boolean checkLine(int i) {
        boolean ret = true;
        int j;
        
        for (j = 0; j < this.w; j++)
            ret &= this.field.get(i).get(j);
        
        return ret;
    }
    
    /**
     * Clears a line from the field.
     * @param i The line to be cleared.
     */
    private void removeLine(int i) {
        this.field.remove(i);
        this.field.add(new BitSet(this.w));
    }
}
