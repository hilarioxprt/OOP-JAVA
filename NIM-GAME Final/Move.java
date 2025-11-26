import java.io.Serializable;

/**
 * Represents a single move in the Nim game.
 * Stores how many marbles were removed and whether the move was made by a human.
 * Useful for undo functionality and game history tracking.
 *
 * @author (Hilario Mavungo)
 * @version (a version 1)
 */
public class Move implements Serializable
{
    private static final long serialVersionUID = 1L;
    
    private int marblesRemoved;
    private boolean wasHuman;
    
    /**
     * Constructs a Move object with the given number of marbles removed
     * and the player type (human or computer).
     *
     * @param marblesRemoved the number of marbles taken
     * @param wasHuman true if the move was by the human player
     */
    public Move(int marblesRemoved, boolean wasHuman){
        this.marblesRemoved = marblesRemoved;
        this.wasHuman = wasHuman;
    }
    
    /**
     * Returns the number of marbles removed in this move.
     *
     * @return number of marbles removed
     */
    public int getMarblesRemoved(){
        return marblesRemoved;
    }
    
    /**
     * Returns whether the move was made by the human player.
     *
     * @return true if the move was by a human, false if by the computer
     */
    public boolean wasHuman(){
        return wasHuman;
    }
}
