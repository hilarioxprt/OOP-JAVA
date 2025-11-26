import java.io.Serializable;

/**
 * Represents a player in the Nim game, either human or computer.
 * Supports assigning a strategy for computer-controlled moves.
 */
public class Player implements Serializable {
    private static final long serialVersion = 1L;
    
    private String name;
    private MoveStrategy strategy;
    
    /**
     * Constructor for a human player (no strategy).
     *
     * @param name the name of the player
     */
    public Player(String name) {
        this.name = name;
        this.strategy = null; // No strategy means it's a human player
    }
    
    /**
     * Constructor for a computer player with a defined strategy.
     *
     * @param name the name of the player
     * @param strategy the strategy used to make moves
     */
    public Player(String name, MoveStrategy strategy) {
        this.name = name;
        this.strategy = strategy;
    }
    
    /**
     * Returns the next move from this player.
     * For a computer player, this uses the assigned strategy.
     *
     * @param currentPileSize the current number of marbles in the pile
     * @return the number of marbles to remove
     */
    public int getMove(int currentPileSize) {
        return strategy.nextMove();
    }
    
    /**
     * gets the palyer name
     */
    public String getName() {
        return name;
    }
    
    /**
     * gets the move strategy
     */
    public MoveStrategy getStrategy() {
        return strategy;
    }
    
    /**
     * sets the player startegy
     */
    public void setStrategy(MoveStrategy strategy) {
        this.strategy = strategy;
    }
    
}
