

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class PlayerTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class PlayerTest
{
    /**
     * Default constructor for test class PlayerTest
     */
    public PlayerTest()
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
    public void testDefaultPlayerConstructor1()
    {
        Player player1 = new Player("Hilario");
        assertNotNull( player1);
    }

    @Test
    public void testPlayerConstructor1()
    {
        Player player1 = new Player("Hilario", new YourStrategy());
        assertNotNull(player1);
    }

    @Test
    public void testGetNameMethod()
    {
        Player player1 = new Player("Hilario");
        assertEquals("Hilario", player1.getName());
    }
    
    @Test
    public void testGetMoveMethod()
    {
    }

    @Test
    public void testGetStrategyMethod()
    {
        Player player1 = new Player("Hilario", new HumanUserStrategy());
        assertTrue(player1.getStrategy() instanceof HumanUserStrategy);
    }

    @Test
    public void testSetStrategyMethod()
    {
        Player player1 = new Player("Hilario");
        player1.setStrategy(new RandomStrategy ());
        assertTrue(player1.getStrategy() instanceof RandomStrategy);
    }
}








