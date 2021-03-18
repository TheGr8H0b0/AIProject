package src;

import java.util.ArrayList;

public class Node implements Comparable<Node> {

    public Board currentState;
    public ArrayList<Board> path;
    public Node next;

    //Initialize the variables
    public Node(Board state, ArrayList<Board> path){
        currentState = state;
        this.path = path;
        next = null;   
    }

    //Overrides comparable for A-Star, but uses the Board's compareTo method.
    @Override
    public int compareTo(Node o) {
        return currentState.compareTo(o.currentState);
    }
    
}
