import java.io.Serializable;

abstract interface MoveStrategy extends Serializable{
    
    int nextMove();
}
