import java.util.ArrayList;

public class PegSolitaireSolver {

    Board initialState;
    int boardDepth;

    PegSolitaireSolver(Board initialState, int depth){
        this.initialState = initialState;
        boardDepth = depth;
    }

    public void breadthFirstSearch(){
        ArrayList <Board> path = new ArrayList<>();
        path.add(initialState);
        Node queueHead = new Node(initialState, path);
        Node queueEnd = queueHead;
        
        while (queueHead != null){

            if (queueHead.currentState.getNumPegs() == 1){
                //reached goal state
            }

            //get possible next states
            
            //cycle through all pegs to find possible moves
            for (int i = 0; i < boardDepth; i++){
                for (int j = 0; j <= i; j++){
                    ArrayList <int []> withinReach = new ArrayList<>();
                    //for each peg, add states for all possible moves to queue
                    queueHead.currentState.setWithinReach(withinReach, i, j);
                    for (int k = 0; k < withinReach.size(); k++){
                        int row = withinReach.get(k)[0];
                        int col = withinReach.get(k)[1];
                        if (queueHead.currentState.canMove(i, j, row, col)){
                            //add new state to queue
                            Board newState = new Board(queueHead.currentState);
                            newState.move(i, j, row, col);
                            ArrayList <Board> newPath = new ArrayList<>(queueHead.path);
                            newPath.add(newState);
                            Node newNode = new Node(newState, newPath);
                            queueEnd.next = newNode;
                            queueEnd = newNode;
                        }
                    }
                }
            }

            //pop off head
            queueHead = queueHead.next;
        }
    }

    public void bidirectionalSearch(){
        /*
        Bidirectional search implementation here
        */
    }


    
}
