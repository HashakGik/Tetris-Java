package Tetris;

/**
 * Tetromino data structure. Each tetromino is composed of four blocks. One of them acts as a pivot and the other three can rotate around it.
 */
class Tetromino {
    public enum Type_t {O, J, L, I, S, Z, T};
    
    private Point[][] piece;
    private int rotation;
    private Type_t type;
    
    public Type_t getType() {
        return this.type;
    }
    
    /**
     * Constructor. Creates the matrix containing all the rotations for the given tetromino.7
     * 
     * @param t Type of the tetromino.
     */
    public Tetromino(Type_t t) {
        int i, j;
        
        this.type = t;
        
        this.piece = new Point[4][];
        for (i = 0; i < 4; i++)
        {
            this.piece[i] = new Point[4];
            for (j = 0; j < 4; j++)
                this.piece[i][j] = new Point();
        }
        this.piece[0][0].x = this.piece[0][0].y = this.piece[1][0].x = this.piece[1][0].y =
                this.piece[2][0].x = this.piece[2][0].y = this.piece[3][0].x = this.piece[3][0].y = 0; // One of the four blocks is always at (0,0).
        
        switch (t)
        {
            case O: // The four rotations yield the same result.
                this.piece[0][1].x = this.piece[1][1].x = this.piece[2][1].x = this.piece[3][1].x = 1;
                this.piece[0][1].y = this.piece[1][1].y = this.piece[2][1].y = this.piece[3][1].y = 0;
                this.piece[0][2].x = this.piece[1][2].x = this.piece[2][2].x = this.piece[3][2].x = 0;
                this.piece[0][2].y = this.piece[1][2].y = this.piece[2][2].y = this.piece[3][2].y = 1;
                this.piece[0][3].x = this.piece[1][3].x = this.piece[2][3].x = this.piece[3][3].x = 1;
                this.piece[0][3].y = this.piece[1][3].y = this.piece[2][3].y = this.piece[3][3].y = 1;
                break;
            case I: // Rotations 0 and 2, and rotations 1 and 3 are equal.
                this.piece[0][1].x = this.piece[2][1].x = 1;
                this.piece[0][1].y = this.piece[2][1].y = 0;
                this.piece[0][2].x = this.piece[2][2].x = -1;
                this.piece[0][2].y = this.piece[2][2].y = 0;
                this.piece[0][3].x = this.piece[2][3].x = -2;
                this.piece[0][3].y = this.piece[2][3].y = 0;
                this.piece[1][1].x = this.piece[3][1].x = 0;
                this.piece[1][1].y = this.piece[3][1].y = 1;
                this.piece[1][2].x = this.piece[3][2].x = 0;
                this.piece[1][2].y = this.piece[3][2].y = -1;
                this.piece[1][3].x = this.piece[3][3].x = 0;
                this.piece[1][3].y = this.piece[3][3].y = -2;
                break;
            case S:
                this.piece[0][1].x = this.piece[2][1].x = -1;
                this.piece[0][1].y = this.piece[2][1].y = 1;
                this.piece[0][2].x = this.piece[2][2].x = 1;
                this.piece[0][2].y = this.piece[2][2].y = 0;
                this.piece[0][3].x = this.piece[2][3].x = 0;
                this.piece[0][3].y = this.piece[2][3].y = 1;
                this.piece[1][1].x = this.piece[3][1].x = 1;
                this.piece[1][1].y = this.piece[3][1].y = 0;
                this.piece[1][2].x = this.piece[3][2].x = 1;
                this.piece[1][2].y = this.piece[3][2].y = 1;
                this.piece[1][3].x = this.piece[3][3].x = 0;
                this.piece[1][3].y = this.piece[3][3].y = -1;
                break;
            case Z:
                this.piece[0][1].x = this.piece[2][1].x = 0;
                this.piece[0][1].y = this.piece[2][1].y = 1;
                this.piece[0][2].x = this.piece[2][2].x = 1;
                this.piece[0][2].y = this.piece[2][2].y = 1;
                this.piece[0][3].x = this.piece[2][3].x = -1;
                this.piece[0][3].y = this.piece[2][3].y = 0;
                this.piece[1][1].x = this.piece[3][1].x = -1;
                this.piece[1][1].y = this.piece[3][1].y = 0;
                this.piece[1][2].x = this.piece[3][2].x = -1;
                this.piece[1][2].y = this.piece[3][2].y = 1;
                this.piece[1][3].x = this.piece[3][3].x = 0;
                this.piece[1][3].y = this.piece[3][3].y = -1;
                break;
            case J: // Every rotation is different.
                this.piece[0][1].x = -1;
                this.piece[0][1].y = 0;
                this.piece[0][2].x = 1;
                this.piece[0][2].y = 0;
                this.piece[0][3].x = 1;
                this.piece[0][3].y = 1;
                this.piece[1][1].x = 0;
                this.piece[1][1].y = 1;
                this.piece[1][2].x = 0;
                this.piece[1][2].y = -1;
                this.piece[1][3].x = 1;
                this.piece[1][3].y = -1;
                this.piece[2][1].x = -1;
                this.piece[2][1].y = 0;
                this.piece[2][2].x = 1;
                this.piece[2][2].y = 0;
                this.piece[2][3].x = -1;
                this.piece[2][3].y = -1;
                this.piece[3][1].x = 0;
                this.piece[3][1].y = -1;
                this.piece[3][2].x = 0;
                this.piece[3][2].y = 1;
                this.piece[3][3].x = -1;
                this.piece[3][3].y = 1;
                break;
            case L:
                this.piece[0][1].x = -1;
                this.piece[0][1].y = 0;
                this.piece[0][2].x = 1;
                this.piece[0][2].y = 0;
                this.piece[0][3].x = -1;
                this.piece[0][3].y = 1;
                this.piece[1][1].x = 0;
                this.piece[1][1].y = -1;
                this.piece[1][2].x = 0;
                this.piece[1][2].y = 1;
                this.piece[1][3].x = 1;
                this.piece[1][3].y = 1;
                this.piece[2][1].x = -1;
                this.piece[2][1].y = 0;
                this.piece[2][2].x = 1;
                this.piece[2][2].y = 0;
                this.piece[2][3].x = 1;
                this.piece[2][3].y = -1;
                this.piece[3][1].x = 0;
                this.piece[3][1].y = 1;
                this.piece[3][2].x = 0;
                this.piece[3][2].y = -1;
                this.piece[3][3].x = -1;
                this.piece[3][3].y = -1;
                break;
            case T:
                this.piece[0][1].x = -1;
                this.piece[0][1].y = 0;
                this.piece[0][2].x = 1;
                this.piece[0][2].y = 0;
                this.piece[0][3].x = 0;
                this.piece[0][3].y = 1;
                this.piece[1][1].x = 0;
                this.piece[1][1].y = -1;
                this.piece[1][2].x = 1;
                this.piece[1][2].y = 0;
                this.piece[1][3].x = 0;
                this.piece[1][3].y = 1;
                this.piece[2][1].x = 0;
                this.piece[2][1].y = -1;
                this.piece[2][2].x = 1;
                this.piece[2][2].y = 0;
                this.piece[2][3].x = -1;
                this.piece[2][3].y = 0;
                this.piece[3][1].x = 0;
                this.piece[3][1].y = -1;
                this.piece[3][2].x = 0;
                this.piece[3][2].y = 1;
                this.piece[3][3].x = -1;
                this.piece[3][3].y = 0;
                break;
        }

        this.rotation = 0;        
    }
    
    /**
     * Rotates the tetromino counterclockwise.
     */
    public void rotateLeft() {
        this.rotation = (this.rotation + 1) % 4;
    }
    
    /**
     * Rotates the tetromino clockwise.
     */
    public void rotateRight() {
        this.rotation = (this.rotation + 3) % 4;
    }
    
    /**
     * Gets the current rotation's blocks.
     * @return The four blocks for the current rotation.
     */
    public Point[] get() {
        return this.piece[this.rotation];
    }
    
}
