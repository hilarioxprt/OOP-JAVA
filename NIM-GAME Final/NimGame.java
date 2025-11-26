import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;
import java.io.*;
import java.util.Stack;
import java.util.ArrayList;

/**
 * Nim Game class creates entire Nim Game backbone, it provies sources of fucntionality
 * for both GUI and textBasedUI to executre the game accordingly. this class is responsble
 * to handle the marble size human tun, move history, undo, save, reset, functions. 
 * it is one of the most important classed for the Nim Game.
 *@author (HIlario Mavungo)
 *@version (version 1)
 */
public class NimGame implements Serializable {
    private static final long serialVersionUID = 1L; //Ensures compatibility for 
    //deserialization
    
    private transient Scanner scanner; // Exclude scanner from serializatiom
    private transient Random random; // Exclude random from serializatiom
    
    private Player humanPlayer;
    private Player computerPlayer;
    
    private Stack<Move> moveHistory;
    private ArrayList<String> logs; 
    
    private int marbleSize;
    private int lastMarbleSize;//private variables to store the last mrbl value to allow undo method
    private boolean isHumanTurn;
    private boolean lastIsHumanTurn;
    
    /**
     * Constructs a new NimGame with two players (human and computer).
     * Initializes a random number of marbles between 5 and 20,
     * sets up the turn order, and prepares game history and logging.
     *
     * @param humanPlayer the human player
     * @param computerPlayer the computer player
     */
    public NimGame(Player humanPlayer, Player computerPlayer) {
        this.random = new Random(); //Intialize the random
        
        //Generates marbles number between 5 and 20
        this.marbleSize = ThreadLocalRandom.current().nextInt(5, 21); 
        this.humanPlayer = humanPlayer;
        this.computerPlayer = computerPlayer;
        this.isHumanTurn = true;
        this.moveHistory = new Stack<>();
        this.logs = new ArrayList<>();
        
        this.scanner = new Scanner(System.in);//initialize scanner
    }
    
    /**
     * Assigns a move to the current player, updates the marble count,
     * switches the turn, and stores the move in history for undo functionality.
     *
     * @param move the number of marbles to remove (should be 1 or 2)
     */
    public void assignMove(int move) {
        moveHistory.push(new Move(move, isHumanTurn));
        marbleSize -= move;
        isHumanTurn = !isHumanTurn;
    }
    
    /**
     * check the winner if no more marbles left and the 
     * 
     * @param human/computer win
     */
    public boolean checkWinner() {
        return marbleSize <= 0;
    }
    
    /**
     * Adds a message to the game's log history.
     * This can be used to track game events such as moves or game status updates.
     *
     * @param message the message to be logged
     */
    public void log(String message)
    {
        logs.add(message);
    }
    
    
    public ArrayList<String> getLogs(){
        return logs;
    }
    
    /**
     * Returns the list of game log messages.
     * Useful for displaying game history in the UI.
     *
     * @return an ArrayList of log messages
     */
    public void clearLogs(){
        logs.clear();
    }
    
    /**
     * method to save the current stage of a gme into a file
     * implemented with exceptions to handle any possible errors
     */
    public void saveGame() throws IOException {
        //log("game saved");//log just before saving
        
        try (FileOutputStream fileOut = new FileOutputStream("save_game.dat");
            ObjectOutputStream objectOut = new ObjectOutputStream(fileOut)){
            
            objectOut.writeObject(this); //save the current game state
            this.reinitializeTransientFields();// Reinitialize transient fields
        }
    }
    
    public void loadGame() throws IOException, ClassNotFoundException{
        File saveFile = new File ("save_game.dat");
        
        if (!saveFile.exists()) {
            throw new FileNotFoundException("No saved game found.");
        }
        try (FileInputStream fileIn = new FileInputStream(saveFile);
             ObjectInputStream objectIn = new ObjectInputStream(fileIn)) {

            // Read the saved game object
            NimGame loadedGame = (NimGame) objectIn.readObject();
        
            // Restore the game state
            this.marbleSize = loadedGame.marbleSize;
            this.humanPlayer = loadedGame.humanPlayer;
            this.computerPlayer = loadedGame.computerPlayer;
            this.isHumanTurn = loadedGame.isHumanTurn;
        
            // Reinitialize transient fields
            if (this.computerPlayer.getStrategy() instanceof RandomStrategy){
                ((RandomStrategy) this.computerPlayer.getStrategy()).reinitializeRandom();
            }
        }
    }
    
    public void reinitializeTransientFields() 
    {
        this.scanner = new Scanner(System.in);
        this.random = new Random();
    }
    
    /**
     * Loads a previously saved game state from a file named "save_game.dat".
     * Restores all essential game fields from the saved NimGame object.
     *
     * @throws IOException if there is an issue reading the file
     * @throws ClassNotFoundException if the saved object cannot be cast to NimGame
     */
    public void undoLastMove() {
        if (moveHistory.isEmpty()){
            throw new IllegalStateException("No move to undo");
        }
        
        //undo last move
        Move lastMove = moveHistory.remove(moveHistory.size() - 1);
        marbleSize += lastMove.getMarblesRemoved();
        isHumanTurn = lastMove.wasHuman();
        
        //if it was comouter's move also undo the human's move before that
        if(!isHumanTurn && !moveHistory.isEmpty()){
            Move secondLast = moveHistory.remove(moveHistory.size() -1);
            marbleSize+= secondLast.getMarblesRemoved();
            isHumanTurn = secondLast.wasHuman();
        }
    }
    
    /**
     * Resets the game to a new state with a random number of marbles.
     * Clears the move history and logs, resets the turn to the human player,
     * and reinitializes any transient fields (e.g., random generator or scanner).
     */
    public void resetGame() {
        this.marbleSize = ThreadLocalRandom.current().nextInt(5, 21); 
        this.moveHistory.clear();
        this.logs.clear();
        this.isHumanTurn = true; 
        reinitializeTransientFields();
    }
    
    /**
     * Gets the human player of the game
     *
     * @return the human player object
     */
    public Player getHumanPlayer() {
        return humanPlayer;
    }
    
    /**
     * return the computer player
     * 
     * @return the human player object
     */
    public Player getComputerPlayer(){
        return computerPlayer;
    }
    
    /**
     * return the marblesize
     */
    public int getMarbleSize() {
        return marbleSize;
    }
    
    /**
     * check if the player is human or not.
     */
    public boolean isHumanTurn() {
        return isHumanTurn;
    }
    
    public Stack<Move> getMoveHistory()
    {
        return moveHistory;   
    }

}
