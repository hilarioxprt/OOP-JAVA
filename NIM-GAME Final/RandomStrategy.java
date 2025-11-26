import java.util.Random;
import java.io.Serializable;

/**
 * A move strategy for the computer that makes random moves (1 or 2 marbles).
 * Implements Serializable to support saving/loading game state.
 */
public class RandomStrategy implements MoveStrategy, Serializable {
    private static final long serialVersionUID = 1L; // Ensures compatibility
    
    private transient Random random = new Random();//Exclude Random from serialization
    
    /**
     * Method to initiaize random
     */
    public RandomStrategy() {
        this.random = new Random();
    }
    
    /**
     * Returns the computer's next move.
     * Randomly chooses 1 or 2 marbles to remove.
     *
     * @return 1 or 2
     */
    @Override
    public int nextMove() {
        return random.nextInt(2) + 1; // Randomly remove 1 or 2 piles
    }
    
    /**
     * method to reinitialize random
     */
     public void reinitializeRandom() {
        this.random = new Random();
    }
}
