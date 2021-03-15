import java.util.Queue;

public class Node {

    public Board currentState;
    public Queue<Board> path;
    public Board next;

    public Node(Board state, Queue<Board> path){
        currentState = state;
        this.path = path;
        next = null;   
    }
    
}
