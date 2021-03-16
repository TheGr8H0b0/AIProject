
import java.util.ArrayList;

public class Node {

    public Board currentState;
    public ArrayList<Board> path;
    public Node next;

    public Node(Board state, ArrayList<Board> path){
        currentState = state;
        this.path = path;
        next = null;   
    }
    
}
