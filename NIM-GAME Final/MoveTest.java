

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * The test class MoveTest.
 *
 * @author  (your name)
 * @version (a version number or a date)
 */
public class MoveTest
{
    /**
     * Default constructor for test class MoveTest
     */
    public MoveTest()
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
    public void testDefaultContructor()
    {
        Move move1 = new Move(2, true);
        assertNotNull(move1);
    }
    
    @Test
    public void testGetMarblesRemovedMethod()
    {
        Move move1 = new Move(2, true);
        assertEquals(2, move1.getMarblesRemoved());
    }
    
    @Test
    public void testWasHumanMethod()
    {
        Move move1 = new Move(2, true);
        assertEquals(true, move1.wasHuman());
    }
}

