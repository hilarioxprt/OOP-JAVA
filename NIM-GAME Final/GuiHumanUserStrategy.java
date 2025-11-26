import java.io.Serializable;

/**
 * YourStrategy class stores provided an automatic or computer HumanStrategy.
 * 
 * It challenges the game in a using an atomatic strategy that you
 * defined yourself
 * 
 * this is the completed bversion of this code .
 * 
 * @author Hilario  Mavungo
 * @version 0.1
 */
public class GuiHumanUserStrategy implements MoveStrategy, Serializable {
    private static final long serialVersionUID = 1L;// Ensures compatibility for 
    //deserialization
    
    /**
     * method to define HumanStrategy completley
     */
    @Override
    public int nextMove() {
      throw new UnsupportedOperationException("Human move should be triggered via GUI buttons.");  
    }
}
