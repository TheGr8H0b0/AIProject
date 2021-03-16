
import java.util.ArrayList;

public class PegSolitaireSolver {

    Board initialState;
    int boardDepth;

    PegSolitaireSolver(Board initialState, int depth){
        this.initialState = initialState;
        boardDepth = depth;
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

    public class biNode {

        private Board currentState;
        private Board parent;
    
        public biNode(Board state, Board parent){
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

    public biNode bidirectionalSearchRecursive(biNode head) {
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

        return new biNode(null, null);
    }
    
}
