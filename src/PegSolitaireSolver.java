package src;

import java.util.ArrayList;

public class PegSolitaireSolver {

    Board initialState;
    int boardDepth;

    PegSolitaireSolver(Board initialState){
        this.initialState = initialState;
        boardDepth = initialState.getDepth();
    }

    public Node breadthFirstSearch(){
        ArrayList <Board> path = new ArrayList<>();
        path.add(initialState);
        Node queueHead = new Node(initialState, path);
        Node queueEnd = queueHead;
        
        while (queueHead != null){

            if (queueHead.currentState.getNumPegs() == 1){
                return queueHead;
            }
        
            //get possible next states

            //cycle through all pegs to find possible moves
            for (int i = 0; i < boardDepth; i++){
                for (int j = 0; j <= i; j++){
                    if (queueHead.currentState.pegExists(i, j)){
                        ArrayList <int []> withinReach = queueHead.currentState.getWithinReach(i, j);
                        //for each peg, add states for all possible moves to queue
                        for (int k = 0; k < withinReach.size(); k++){
                            int row = withinReach.get(k)[0];
                            int col = withinReach.get(k)[1];
                            //possible move == new state
                            //create new state
                            Board newState = new Board(queueHead.currentState);
                            newState.move(i, j, row, col);
                            //get path to new state
                            ArrayList <Board> newPath = new ArrayList<>(queueHead.path);
                            newPath.add(newState);
                            Node newNode = new Node(newState, newPath);
                            //add new state to queue
                            queueEnd.next = newNode;
                            queueEnd = newNode;
                        }
                    }
                    
                }
            }

            //pop off head
            queueHead = queueHead.next;
        }

        //if we're here, there's no solution
        return null;
    }

    public Node depthFirstSearch(){
        ArrayList <Board> path = new ArrayList<>();
        path.add(initialState);

        //if we're here, there's no solution
        return depthFirstSearchRecursive(new Node(initialState, path));
    }

    private Node depthFirstSearchRecursive(Node state) {
        if (state.currentState.getNumPegs() == 1) {
            return state;
        }

        for (int i = 0; i < boardDepth; i++){
            for (int j = 0; j <= i; j++) {
                if (state.currentState.pegExists(i, j)) {
                    ArrayList <int []> withinReach = state.currentState.getWithinReach(i, j);
                    //for each peg, add states for all possible moves to queue
                    for (int k = 0; k < withinReach.size(); k++){ 
                        ArrayList <Board> newPath = new ArrayList<>(state.path);
                        int row = withinReach.get(k)[0];
                        int col = withinReach.get(k)[1];
                        //possible move == new state
                        //create new state
                        Board newState = new Board(state.currentState);
                        newState.move(i, j, row, col);
                        //get path to new state
                        newPath.add(newState);
                        Node newNode = new Node(newState, newPath);

                        //Check out the 
                        Node result = depthFirstSearchRecursive(newNode);
                        if (result != null  && result.currentState.getNumPegs() == 1) {
                            return result;
                        }
                    }
                }
            }
        }

        return null;
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
        int numPegs = head.currentState.getNumPegs();
        int depth = head.currentState.getDepth();

        if (numPegs == 1) {
            return head;
        }

        //Build the list of potential moves from the current state
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j <= i; j++) {
                if (head.currentState.pegExists(i, j)){
                    ArrayList<int []> tempJumpList = initialState.getWithinReach(i, j);
                    for (int k  = 0; k < tempJumpList.size(); k++) {
                        int row = tempJumpList.get(k)[0];
                        int col = tempJumpList.get(k)[1];
                        //possible move == new state
                        //create new state
                        Board newState = new Board(head.currentState);
                        newState.move(i, j, row, col);
                        Node newNode = new Node(newState, head.path);
                    }
                }
            }
        }

        return new Node(null, null);
    }
    
}
