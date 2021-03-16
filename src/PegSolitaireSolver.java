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

        private Board currentState;
        private Board parent;
    
        public Node(Board state, Board parent){
            currentState = state;
            this.parent = parent;
        }

        public Board getState() {
            return currentState;
        }

        public Board getParent() {
            return parent;
        }
        
    }

    public void bidirectionalSearch(){
        /*
        Bidirectional search implementation here
        */
        int[] initEmpty = initialState.getInitialEmpty();
        ArrayList<int []> potentialStartPegs = initialState.getWithinReach(initEmpty[0], initEmpty[1]);
        Node head = new Node(initialState, null);

        for (int i = 0; i < potentialStartPegs.size(); i++) {

        }
    }

    public Node bidirectionalSearchRecursive(Node head) {
        int numPegs = head.getState().getNumPegs();
        int depth = head.getState().getDepth();

        if (numPegs == 1) {
            return head;
        }

        //Build the list of potential moves from the current state
        ArrayList<int []> pegJumpList = new ArrayList<int []>();
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j <= i; j++) {
                ArrayList<int []> tempJumpList = initialState.getWithinReach(i, j);
                for (int k  = 0; k < tempJumpList.size(); k++) {
                    pegJumpList.add(tempJumpList.get(k));
                }
            }
        }

        return new Node(null, null);
    }
    
}
