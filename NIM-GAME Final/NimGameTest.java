

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.io.*;

/**
 * The test class NimGameTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class NimGameTest
{
    /**
     * Default constructor for test class NimGameTest
     */
    public NimGameTest()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @BeforeEach
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @AfterEach
    public void tearDown()
    {
    }

    

    @Test
    public void testDefaultConstructor1()
    {
        Player player1 = new Player("Hilario", new YourStrategy ());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        assertNotNull(nimGame1);
    }

    @Test
    public void testAssignMoveMethod()
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        
        int beforeMove = nimGame1.getMarbleSize();
        
        nimGame1.assignMove(1);
        assertEquals(beforeMove - 1, nimGame1.getMarbleSize());
    }
    
    @Test
    public void testCheckWinnerMethod()
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        nimGame1.assignMove(nimGame1.getMarbleSize());
        assertTrue(nimGame1.checkWinner());
    }
    
    @Test
    public void testlogMethod ()
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        nimGame1.log("Hilario takes 1 marble(s)");
        assertTrue(nimGame1.getLogs().contains("Hilario takes 1 marble(s)"));
    }
    
    @Test 
    public void testLoadGameMethod()throws IOException, ClassNotFoundException
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        nimGame1.assignMove(2); 
        nimGame1.saveGame();    
    
        NimGame gameToLoad = new NimGame(player1, player2);
        gameToLoad.loadGame();    
    
        assertEquals(nimGame1.getMarbleSize(), gameToLoad.getMarbleSize());
        assertEquals(nimGame1.isHumanTurn(), gameToLoad.isHumanTurn());
        assertEquals(nimGame1.getHumanPlayer().getName(), gameToLoad.getHumanPlayer().getName());
        assertEquals(nimGame1.getComputerPlayer().getName(), gameToLoad.getComputerPlayer().getName());
    }
    
    @Test
    public void undoLastMove()
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        int marbles = nimGame1.getMarbleSize();
        
        nimGame1.assignMove(1);
        nimGame1.assignMove(2);
        
        int afterMoves = nimGame1.getMarbleSize();
        assertEquals(marbles - 3, afterMoves);
        nimGame1.undoLastMove();
        
        assertEquals(marbles, nimGame1.getMarbleSize());
        assertTrue(nimGame1.isHumanTurn());
    }
    
    @Test
    public void testResetGameMethod()
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        
        assertTrue(nimGame1.getMarbleSize() >= 5 && nimGame1.getMarbleSize() <= 20);
        assertTrue(nimGame1.getMoveHistory().isEmpty());
        assertTrue(nimGame1.getLogs().isEmpty());
        assertTrue(nimGame1.isHumanTurn());
    }
    
    @Test
    public void testGetHumanPlayer()
    {
        Player player1 = new Player("Hilario", new HumanUserStrategy());
        Player player2 = new Player("computer", new RandomStrategy ());
        NimGame nimGame1 = new NimGame(player1, player2);
        
        Player player = nimGame1.getHumanPlayer();
        
        assertEquals(player1, player);
        assertEquals("Hilario", player.getName());
    }
}



