import java.util.Scanner;
import java.io.Serializable;
import java.util.InputMismatchException;

/**
 * YourStrategy class stores provided an automatic or computer HumanStrategy.
 * 
 * It challenges the game in a using an atomatic strategy that you
 * defined yourself
 * 
 * this is the completed bversion of this code .
 * 
 * @author Hilario  Mavungo
 * @version 1
 */
public class HumanUserStrategy implements MoveStrategy, Serializable {
    private static final long serialVersionUID = 1L;// Ensures compatibility for 
    //deserialization
    
    private transient Scanner reader; // Exclude scanner from serializatiom
    
    /**
     * HumanUserStrategy constructor to reinitialize Scanner 
     * after it has been excluded from serialization.
     */
    public HumanUserStrategy() {
        reader = new Scanner(System.in);//reinitialize the scanner
    }
    
    /**
     * method to define HumanStrategy completley
     */
    @Override
    public int nextMove() {
        try {
            // Ensure Scanner is not null before using it
            if (reader == null) {
                reinitializeScanner();
            }
            System.out.print("How many piles do you want to remove? (1 or 2): ");
            int move = reader.nextInt();
            return move;
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter number 1 or 2.");
            reader.nextLine(); // Clear invalid input
            return nextMove(); // Ask again
        } catch (Exception e) {
            System.out.println("Something went wrong: " + e.getMessage());
            return 1; // default move
        }
    }
    
    /**
     * Reinitializes the Scanner object for standard input.
     * This is useful after loading a saved game to restore input capability.
     */
    public void reinitializeScanner() {
        this.reader = new Scanner(System.in);
    }
}
