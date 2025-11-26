import java.util.Scanner;
import java.util.Random;
import java.io.IOException;

/**
 * This class builds a Text based User Interface for the NIM Game it gives all details bout.
 * It manages number of marbles, player turns and gmae state,
 * allowing human players to play with computer atomated player, either using a
 * strategy created by you or ramdonmly slected by the computer.
 * it allow players to take marbles untill the ,marbles run out, the last player to remove 
 * marbles wins the game.This class also allows you to save, load and undo moves.
 * @author (HIlario Mavungo)
 * @version (version 1)
 */
public class TextBasedUI {
    
    private NimGame game;
    private Scanner reader;
    private Random random;
    
    /**
     * Build the text Based UI for the Nim Game
     * prompts user to select the game mode and
     * randomly decides who starts the mage and begins with a lloop
     */
    public TextBasedUI() {
        reader = new Scanner(System.in);
        
        System.out.println("The Game of 1-2 Nim Assessment!");
        System.out.println("------------------------------");
        

        String gameMode;
        MoveStrategy computerStrategy = null;
        
        while(true) 
        {
            System.out.println("Choose a computer strategy:");
            System.out.println("[R] Random\n" +
                           "[Y] Your Strategy");
                           
            gameMode = reader.nextLine().toUpperCase();
            
        
            if (gameMode.equals("R")) {
                computerStrategy = new RandomStrategy();
                System.out.println("You selected Random Computer strategy.");
                break;
            } else if (gameMode.equals("Y")) {
                computerStrategy = new YourStrategy();
                System.out.println("You selected Your Computer strategy.");
                break;
            }else {
                System.out.println("Invalid option. Please Try again");
            }
        }

        Player player1 = new Player("Human", new HumanUserStrategy());
        Player player2 = new Player("Computer", computerStrategy);
        
        random = new Random();
        boolean isHumanStarting = random.nextBoolean();
        
        if (isHumanStarting){
        this.game = new NimGame(player1, player2);
        }
        else{
           this.game = new NimGame(player2, player1); 
        }
        startGame();
    }
    
    /**
     * starts the the Nim Game 
     */
    private void startGame() {
        System.out.println("\nInitial number of marbles: " + game.getMarbleSize());
        displayMarbles();

        while (!game.checkWinner()) {
            displayMenu();
        }

        announceWinner();
    }
    
    /**
     * Creates a menu for the Nim Game the serves as a guide for the game
     * prompt chices for the user to select
     */
        private void displayMenu() {  
        System.out.println("\nChoose an option: \n"
            + "[M] Make a move\n"
            + "[S] Save game\n"
            + "[L] Load saved game\n"
            + "[U] Undo move\n"
            + "[C] Clear game\n"
            + "[Q] Quit game\n");

        String choice = reader.nextLine().toUpperCase();
        switch (choice) {
            case "M":
                makeMove();
                break;
            case "S":
                try{
                    game.saveGame();
                    System.out.println("Game successfuly saved");
                }catch (IOException e) {
                    System.out.println("Error saving the game: " + e.getMessage());
                }
                    break;
            case "L":
                try{
                    game.loadGame();
                    displayMarbles();
                }catch (IOException | ClassNotFoundException e) {
                    System.out.println("Error loading the game: " + e.getMessage());
                }
                break;
            case "U":
                game.undoLastMove();
                displayMarbles();
                break;
            case "C":
                System.out.println("Your game has reseted to a new game");
                game.resetGame();
                startGame();
                break;
            case "Q":
                System.out.println("Thank you for playing! Exiting game...");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please select again.");
        }
    }

    /**
     * Handle making moves for both Human and the computer
     * check whose turns to play and performs the action and alternate the turn
     * if no one has won the game yet.
     */ 
    private void makeMove() {
        try{
            if (game.isHumanTurn()){
                if (assignMoveFrom(game.getHumanPlayer())){ //human move
                    if(!game.checkWinner()){
                        assignMoveFrom(game.getComputerPlayer()); // computer move
                    }
                }
            } else {
                assignMoveFrom(game.getComputerPlayer());
                if(!game.checkWinner()){
                    assignMoveFrom(game.getHumanPlayer());
                }
            }
        }catch (Exception e) {
            System.out.println("Error during making a move: " + e.getMessage());
        }
    }
        
    /**
     * handles move from either computer or human player in the text Based GUI
     * prompts layer for amove and validates the input by applying move to the game
     * if the move is invalid it will retey untill the moveis valid
     */
    private boolean assignMoveFrom(Player player) {
        System.out.println("\nIt is " + player.getName() + "'s turn to play.");
        int move = 0;
        boolean validMove = false;
        while (!validMove) {
            try {
                move = player.getMove(game.getMarbleSize());
                
                //Validating the move
                if(move < 1 || move > 2) {
                    throw new IllegalArgumentException("Invalid move. You can only take 1 or 2 marbles.");
                }else if (move == 2 && game.getMarbleSize() == 1) {
                    System.out.println("Only 1 marble left. You can only take 1.");
                }else {
                   validMove = true; 
                }
                
            }catch (Exception e){
                System.out.println("Inavlid move by" + player.getName() + ": " +e.getMessage());  
            }
        }
        game.assignMove(move);
        System.out.println(player.getName() + " takes " + move + " marble(s).");
        displayMarbles();
        
        return true;
        
    }
    
    /**
     * dispalays the number of marbles available based on the game interraction
     */
    private void displayMarbles() {
        System.out.println("Current number of marbles: " + game.getMarbleSize());
        for (int i = 0; i < game.getMarbleSize(); i++) {
            System.out.print("@ ");
            if ((i + 1) % 10 == 0) {
                System.out.println();
            }
        }
        System.out.println();
    }
    
    /**
     * Announce the wiiner if the computer or human takes the last marble
     */
    private void announceWinner() {
        String winnerName;
        if (game.isHumanTurn())
            winnerName = game.getComputerPlayer().getName(); //Computer Wins
        else
            winnerName = game.getHumanPlayer().getName(); //Human Wins
        System.out.println("*** " + winnerName + " is the winner! ***");
    }

    /**
     * stats the text based version of the NIM Game
     * it initializes the text BasedUI, which handles the game setup
     */
    public static void main(String[] args) {
        TextBasedUI textUi = new TextBasedUI();
    }
}
