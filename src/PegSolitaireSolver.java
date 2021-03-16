package src;

import java.util.ArrayList;

public class PegSolitaireSolver {

    Board initialState;

    PegSolitaireSolver(Board initialState){
        this.initialState = initialState;
    }

    public void breadthFirstSearch(){
        /*
        Breadth first implementation here
        */
    }

    public class Node {

        public Board currentState;
        public Board parent;
    
        public Node(Board state, Board parent){
            currentState = state;
            this.parent = parent;
        }
        
    }

    public void bidirectionalSearch(){
        /*
        Bidirectional search implementation here
        */
        int[] initEmpty = initialState.getInitialEmpty();
        ArrayList<int []> potentialStartPegs = initialState.getWithinReach(initEmpty[0], initEmpty[1]);

        for (int i = 0; i < potentialStartPegs.size(); i++) {
            
        }
    }
    
}
