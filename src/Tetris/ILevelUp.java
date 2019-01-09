package Tetris;

/**
 * Level up interface. Implements the Command Pattern.
 */
public interface ILevelUp {
    /**
     * Does something when a level up is triggered.
     * @param level The level which has just been reached.
     */
    public void exec(int level);
}
