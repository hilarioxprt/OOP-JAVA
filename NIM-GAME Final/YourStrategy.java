import java.io.Serializable;
import java.util.Random;

/**
 * YourStrategy class stores provided an automatic or computer Startegy.
 * 
 * It challenges the game in a using an atomatic strategy that you
 * defined yourself
 * 
 * this is the completed bversion of this code .
 * 
 * @author Hilario  Mavungo
 * @version 0.1
 */
public class YourStrategy implements MoveStrategy, Serializable {
    private static final long serialVersionUID = 1L;

    private Random random;
    private MoveStrategy strategy; 

    /**
     * YourStrategy constructor to call Random strategy that will be used
     * as comparison basis to create YourStrategy
     */
    public YourStrategy() {
        this.random = new Random();
        this.strategy = new RandomStrategy(); 
    }
    
    /**
     * nextMove defines automatic moves for for the game.
     */
    @Override
    public int nextMove() {
        // Example condition: Switch to RandomStrategy if move is divisible by 3
        int myMove = random.nextInt(2) + 1; // Generate a move between 1-2

        if (myMove % 2 == 0) {
            return strategy.nextMove(); // Delegate to RandomStrategy
        }

        return myMove;
    }
}