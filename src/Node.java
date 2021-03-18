package src;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    public Board currentState;
    public ArrayList<Board> path;
    public Node next;

    public Node(Board state, ArrayList<Board> path){
        currentState = state;
        this.path = path;
        next = null;   
    }

    @Override
    public int compareTo(Node o) {
        return currentState.compareTo(o.currentState);
    }
    
}
