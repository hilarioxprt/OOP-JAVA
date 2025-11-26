import java.awt.*; 
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;

import java.util.*;
import java.util.Random;

import java.io.*;

/**
 * This class builds a Graphical User Interface for the NIM Game it gives all details bout.
 * It manages number of marbles, player turns and gmae state,
 * allowing human players to play with computer atomated player, either using a
 * strategy created by you or ramdonmly slected by the computer.
 * it allow players to remove marbles untill the ,marbles run out, the last player
 * marbles wins the game.This class also allows you to save, load and undo moves.
 * @author (HIlario Mavungo)
 * @version (version 1)
 */
public class GameGUI extends JFrame implements ActionListener
{
    private NimGame game;
    
    private JLabel gameWinner;
    
    private JTextArea marbleLabel;
    private JTextArea marbleSize;
    private JTextArea logArea;
    
    
    private JButton removeButtonOne;
    private JButton removeButtonTwo;
    private JButton newButton;
    private JButton saveButton;
    private JButton loadButton;
    private JButton undoButton;
    
    private JRadioButton easyModeButton;
    private JRadioButton hardModeButton;
    
    private ButtonGroup difficultyGroup;
    
    private Random random;
    
    /**
     * constructor for the game GUI, it builds the frame and 
     * starts the game for the first time
     */
    public GameGUI()   
    {
        super ("The game of 1-2 Nim Assessment");
        
        random = new Random();
        makeFrame();
        startInitialGame();
    }
    
    /**
     * Starts the initial Nim Game
     * sets the computer mode, creates player objects 
     * and update the GUI with the initial game setup.
     */
    private void startInitialGame() {
        try{
            MoveStrategy computerStrategy;
            String selectedMode;
            
            if (easyModeButton.isSelected()) {
                computerStrategy = new YourStrategy();
                logArea.append("Easy mode is is on!\n");
            } else {
                computerStrategy = new RandomStrategy();
                logArea.append("Hard mode is is on!\n");
            }
    
            Player human = new Player("Human", new GuiHumanUserStrategy());
            Player computer = new Player("Computer", computerStrategy);
    
            this.game = new NimGame(human, computer); // Human starts by default
            updateGameGUI();
        }catch(Exception e) {
            logArea.append("there was an error while starting the game.\n");
        }
       
    }

    /**
     * Creates the Graphical User Interface (GUI) for the Nim Game
     * setup all buttons, labels and text for the GUI game
     * create a custom layout for the GUI Nim Game. 
     */
    public void makeFrame()
    {
        //define panel in the frame
        JPanel contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(6, 6, 6, 6));
        contentPane.setLayout(new BorderLayout(4,4));
        
        //difficulty selection
        easyModeButton = new JRadioButton("easy", true); //default game mode
        hardModeButton = new JRadioButton("hard");
        
        //Label for select text 
        JLabel selectLabel = new JLabel("Select : ");
        
        difficultyGroup = new ButtonGroup();
        difficultyGroup.add(easyModeButton);
        difficultyGroup.add(hardModeButton);
        
        //Panel for the radio Buttons
        JPanel difficultyPanel = new JPanel();
        difficultyPanel.setLayout(new FlowLayout());
        difficultyPanel.add(selectLabel);
        difficultyPanel.add(easyModeButton);
        difficultyPanel.add(hardModeButton);
        
        //Game Winner anouncer
        gameWinner = new JLabel();
        gameWinner.setFont(new Font("SansSerif", Font.PLAIN, 14));
        gameWinner.setText(announceWinner());
        
        //game winner Panel
        JPanel gameWinnerPanel = new JPanel();
        gameWinnerPanel.setLayout(new FlowLayout());
        gameWinnerPanel.add(gameWinner);
        
        //marble handler font and additional seettings
        marbleLabel = new JTextArea();
        marbleLabel.setEditable(false);
        marbleLabel.setFont(new Font("SansSerif", Font.PLAIN, 60));
        marbleLabel.setBackground(Color.WHITE);
        marbleLabel.setLineWrap(true);
        marbleLabel.setWrapStyleWord(true);
        marbleLabel.setText(displayMarbles());
        marbleLabel.setBorder(BorderFactory.createLineBorder(Color.BLACK));
        
        //test definition
        marbleSize = new JTextArea();
        marbleSize.setEditable(false);
        marbleSize.setFont(new Font("SansSerif", Font.PLAIN, 18));
        marbleSize.setText(getMarbleCount());
        
        //Panel Settings to for marbleLabel
        JPanel marblePanel = new JPanel();
        marblePanel.add(marbleLabel);
        marblePanel.setLayout(new BoxLayout(marblePanel, BoxLayout.Y_AXIS));
        marblePanel.add(Box.createVerticalStrut(5)); // add space between the two of them.
    
        
        //Panel to hold MarbelSize
        JPanel marbleSizePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        marbleSizePanel.add(marbleSize);
        
        //Panel to hold all north button in a row
        JPanel northButtonPanel = new JPanel();
        northButtonPanel.setLayout(new FlowLayout());
        
        newButton = new JButton("New Game");
        saveButton = new JButton("Save");
        loadButton = new JButton("Load");
        undoButton = new JButton("Undo");
        
        Dimension buttonSize = new Dimension(100,30);
        newButton.setPreferredSize(buttonSize);
        saveButton.setPreferredSize(buttonSize);
        loadButton.setPreferredSize(buttonSize);
        undoButton.setPreferredSize(buttonSize);
        
        //Panel to hold all east Button
        JPanel westButtonPanel = new JPanel();
        westButtonPanel.setLayout(new GridLayout(2, 1, 5, 5));//2 rows and 1 colum vertically
        
        removeButtonOne = new JButton("remove 1");
        removeButtonTwo = new JButton("remove 2");
        
        //add button to different Borders of the Panel
        westButtonPanel.add(removeButtonOne);
        westButtonPanel.add(removeButtonTwo);
        
        logArea = new JTextArea(6,30); //6 lines tall, 30 chars wide
        logArea.setEditable(false);
        logArea.setLineWrap(true);
        logArea.setWrapStyleWord(true);
        
        JScrollPane scrollPanel = new JScrollPane(logArea);
        
        
        //Panel for new game, save, load and undo button
        JPanel controlButtonPanel = new JPanel();
        controlButtonPanel.setLayout(new FlowLayout());
        controlButtonPanel.add(newButton);
        controlButtonPanel.add(saveButton);
        controlButtonPanel.add(loadButton);
        controlButtonPanel.add(undoButton);
        
        //Combine both difficulty Pannel and Control Button Pannel into one panel
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BoxLayout(northPanel, BoxLayout.Y_AXIS));
        northPanel.add(difficultyPanel);
        northPanel.add(gameWinnerPanel);
        northPanel.add(controlButtonPanel);
        
        //Panel for the mableSize
        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BoxLayout(centerPanel, BoxLayout.Y_AXIS));
        centerPanel.add(marblePanel);
        centerPanel.add(marbleSizePanel);
        
        //add components to the main content pane
        contentPane.add(northPanel, BorderLayout.NORTH);
        contentPane.add(westButtonPanel, BorderLayout.WEST);
        contentPane.add(centerPanel, BorderLayout.CENTER);
        contentPane.add(scrollPanel, BorderLayout.SOUTH);
        
        
        //add action effect to each button click
        newButton.addActionListener(this);
        saveButton.addActionListener(this);
        loadButton.addActionListener(this);
        undoButton.addActionListener(this);
        removeButtonOne.addActionListener(this);
        removeButtonTwo.addActionListener(this);
        
        setContentPane(contentPane);
        setSize(700, 450);
        setVisible(true);
    }
    
    /**
     * Creates an action for all button click with real time feedback
     * for the Nim Game GUI
     */
    @Override
    public void actionPerformed(ActionEvent evt)
    {
        Object source = evt.getSource();
        
        if (source == newButton){
            MoveStrategy computerStrategy;
            String selectedMode;

            if (easyModeButton.isSelected()) {
                computerStrategy = new RandomStrategy();
                selectedMode = "Easy";
            } else {
                computerStrategy = new YourStrategy();
                selectedMode = "Hard";
            }

            Player human = new Player("Human", new GuiHumanUserStrategy());
            Player computer = new Player("Computer", computerStrategy);

            this.game = new NimGame(human, computer); // Human always start
            
            //clear the game log
            logArea.setText("");
            logArea.append(selectedMode + " mode is on!\n");
            updateGameGUI();
            
            if(!game.isHumanTurn() && !game.checkWinner()){
                makeComputerMove();
                updateGameGUI();
                if(game.checkWinner()){
                    gameWinner.setText(announceWinner());
                }
            }
            
        }else if (source == removeButtonOne) {
            if (game.checkWinner()){
                JOptionPane.showMessageDialog(this, "Gama is over! No more moves allowed.");
                return;
            }
                
            if (game.isHumanTurn()){
                makeHumanMove(1);
            }
                
        } else if (source == removeButtonTwo) {
            if (game.checkWinner()){
                JOptionPane.showMessageDialog(this, "Gama is over! No more moves allowed.");
                return;
            }
                
            if (game.isHumanTurn()){
                makeHumanMove(2);
            }
            
        } else if (source == saveButton) {
            int confirm = JOptionPane.showConfirmDialog(this, "DO  you want to save the game?", 
            "confirm Save", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION){
                try{
                    game.saveGame();
                    
                    //game,clearLogs();
                    game.log("game saved");
                    
                    //update logs display
                    updateLogArea();
            
                    //Clear GUI once the game is saved
                    marbleLabel.setText("");
                    marbleSize.setText("");
            
                    removeButtonOne.setEnabled(true);
                    removeButtonTwo.setEnabled(true);
                }catch (Exception e){
                    e.printStackTrace();//helpfull debugging delete after
                    JOptionPane.showMessageDialog(this, "error: game not saved");
                }
            }
            
        } else if (source == loadButton) {
            try{
                game.loadGame();
                JOptionPane.showMessageDialog(this, "Game loaded.");
                
                // Restore GUI from loaded game
                marbleLabel.setText(displayMarbles());
                marbleSize.setText(getMarbleCount());
                gameWinner.setText(announceWinner());
                updateLogArea();
                
                //enable removeButtons
                removeButtonOne.setEnabled(true);
                removeButtonTwo.setEnabled(true);
            }catch (FileNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error loading game.\n" + e.getMessage());
            }catch (IOException | ClassNotFoundException e) {
                JOptionPane.showMessageDialog(this, "Error loading game.\n" + e.getMessage());
            }
            
        } else if (source == undoButton) {
            try{
                if (game.checkWinner()){
                    JOptionPane.showMessageDialog(this, "game has ended, cannot undo move.");
                    return;
                }
                game.undoLastMove();
                updateGameGUI();
                logArea.append("move undone.\n");
                
            }catch (Exception e) {
                JOptionPane.showMessageDialog(this, "cannot undo move.");
            }
        }
    }
    
    
    private String displayMarbles() {
        StringBuilder sb = new StringBuilder();
        if (game == null){
            return ""; // the game has not yet started
        }
        for (int i = 0; i < game.getMarbleSize(); i++) {
            sb.append("\u25CF");
            if ((i + 1) % 10 == 0) {
            }
        } 
        return sb.toString(); 
    }
    
    /**
     * returns the number of marble plies and returns 
     * and if there is no game active and someone wone the game
     */
    private String getMarbleCount()
    {
        StringBuilder sb = new StringBuilder();
        if (game == null){
           sb.append("Number of piles: 0\n"); 
        }else{
        sb.append("Number of piles: ").append(game.getMarbleSize()).append("\n");
        }
        return sb.toString();
    }
    
    /**
     * controls the move by the current player and update the GUI accordingly
     * create a control strategy to ensure the human(user) 
     * does not move more than avilable ,arbles
     */
    private void assignMoveForm(int move) {
        try{
            if(move > game.getMarbleSize()){
                move = game.getMarbleSize(); //this is to prevent removing 
                //more than available
            }
            
            String playerName;
            if (game.isHumanTurn()) {
                playerName = game.getHumanPlayer().getName();
            } else {
                playerName = game.getComputerPlayer().getName();
            }
            
            game.assignMove(move);
            
            //update GUI text areas and log area
            logArea.append(playerName + " removed " + move + " marble(s)\n");
            marbleLabel.setText(displayMarbles());
            marbleSize.setText(getMarbleCount());
            updateRemoveButtons();
            logArea.setCaretPosition(logArea.getDocument().getLength());
        }catch (Exception e){
            logArea.append("Move could not be made.\n");
        }
    }
    
    
    /**
     * handles the Human Move and updates the game accordingly
     * ensures the move is valid triggers compyter move if necessray.
     */
    private void makeHumanMove(int move){
        if(!game.isHumanTurn() || game.checkWinner()){
            return;
        }
        assignMoveForm(move);
        updateGameGUI();
        
        if (game.checkWinner()){
            gameWinner.setText(announceWinner());
            return;
        }
        
        if(!game.isHumanTurn()){ //now its computer time
            makeComputerMove();  //trigger computer move automaticaly if required.
            updateGameGUI();
            
            if (game.checkWinner()){
                    gameWinner.setText(announceWinner());
            }
        }
    }
    
    /**
     * update the game with the current game state.
     */
    private void updateGameGUI()
    {
        marbleLabel.setText(displayMarbles());
        marbleSize.setText(getMarbleCount()); 
        gameWinner.setText(announceWinner());
    }
    
    /**
     * update the game logs with current game logs
     * but it cleans the previous logs first
     */
    private void updateLogArea() {
        if (logArea == null || game == null) return;
    
        logArea.setText(""); // clear it first
    
        for (String log : game.getLogs()) {
            logArea.append(log + "\n");
        }
    }
    
    /**
     * enables computer move when is the coouter time to play 
     * and return an error if the computer fails to move.
     */
    private void makeComputerMove(){
        try{
            Player computer = game.getComputerPlayer();
            int move = computer.getMove(game.getMarbleSize());
            assignMoveForm(move);
        }catch (Exception e){
            logArea.append("computer falied to move.\n");
        }
    }
    
    /**
     * announce the game winner if the computer or human wins the game
     */
    private String announceWinner() {
        if (game == null){
            return "winner: none"; //game has not yet started
        }
        
        if (game.checkWinner()){
            String winnerName;
            if (game.isHumanTurn()){
                winnerName = game.getComputerPlayer().getName(); //Computer Wins
            }else{
                winnerName = game.getHumanPlayer().getName(); //Human Wins
            }
            return "Winner: " + winnerName;
        }else{
            return "Winner: none";
        }
    }
    
    /**
     * update the remove buttons based on the current game state.
     * disbales the button 2 if only one marble left
     */
    private void updateRemoveButtons() {
        int marbles = game.getMarbleSize();
        boolean isHumanTurn = game.isHumanTurn();

        removeButtonOne.setEnabled(true); // Always enabled

        if (isHumanTurn && marbles == 1) {
            removeButtonTwo.setEnabled(false);
        } else {
            removeButtonTwo.setEnabled(true);
        }
    }   
} 
 

