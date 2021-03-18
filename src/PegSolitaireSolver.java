package src;

import java.util.ArrayList;
import java.util.PriorityQueue;

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

    public Node bidirectionalSearch(){
        ArrayList <Board> path = new ArrayList<>();
        path.add(initialState);

        //List of all our edge nodes from the first half of the search
        ArrayList<Node> edgeNodes = new ArrayList<>();

        //Queue Nodes
        Node queueHead = new Node(initialState, path);
        Node queueEnd = queueHead;

        int halfMovesDown = (initialState.getNumPegs() + 1) / 2;
        int halfMovesUp = (initialState.getNumPegs()) / 2;
        
        //Normal BFS up to halfway
        while (queueHead != null){
            if (queueHead.currentState.getNumPegs() == halfMovesDown){
                edgeNodes.add(queueHead);
            } else {
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
            }
            //pop off head
            queueHead = queueHead.next;
        }

        ArrayList<Node> endStateQueueList = new ArrayList<>(initialState.getNumPegs() + 1);
        //Make Queue Nodes for every end point
        for (int i = 0; i < boardDepth; i++) {
            for (int j = 0; j <= i; j++) {
                ArrayList<Board> tempPath = new ArrayList<>(halfMovesUp);
                Board tempBoard = new Board(new int[] {i, j}, boardDepth);
                tempPath.add(tempBoard);
                Node temp = new Node(tempBoard, tempPath);
                endStateQueueList.add(temp);
            }
        }
        
        for (int x = 0; x < endStateQueueList.size(); x++) {
            queueHead = endStateQueueList.get(x);
            queueEnd = queueHead;
            while (queueHead != null){
                if (queueHead.currentState.getNumPegs() == halfMovesUp){
                    for (int i = 0; i < edgeNodes.size(); i++) {
                        if (queueHead.currentState.compareTo(edgeNodes.get(i).currentState) == 0) {
                            ArrayList<Board> finalPath = new ArrayList<>();
                            finalPath.addAll(edgeNodes.get(i).path);
                            for (int j = queueHead.path.size() - 2; j >= 0; j--) {
                                finalPath.add(queueHead.path.get(j));
                            }
                            Node finalNode = new Node(queueHead.path.get(0), finalPath);
                            return finalNode;
                        }
                    }
                } else {
                    //cycle through all pegs to find possible moves
                    for (int i = 0; i < boardDepth; i++){
                        for (int j = 0; j <= i; j++){
                            if (queueHead.currentState.pegExists(i, j)){
                                ArrayList <int []> withinReach = queueHead.currentState.getWithinReachBackwards(i, j);
                                //for each peg, add states for all possible moves to queue
                                for (int k = 0; k < withinReach.size(); k++){
                                    int row = withinReach.get(k)[0];
                                    int col = withinReach.get(k)[1];
                                    //possible move == new state
                                    //create new state
                                    Board newState = new Board(queueHead.currentState);
                                    newState.moveBackwards(i, j, row, col);
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
                }
                //pop off head
                queueHead = queueHead.next;
            }
        }

        //if we're here, there's no solution
        System.out.println(edgeNodes.size());
        return null;
    } 
    
    public Node AStarSearch() {
        PriorityQueue<Node> searchQueue = new PriorityQueue<Node>();
        ArrayList<Board> path = new ArrayList<>();
        path.add(initialState);
        searchQueue.add(new Node(initialState, path));

        Node currentNode = searchQueue.poll();

		while (currentNode.currentState.getNumPegs() != 1) {
			for (int i = 0; i < boardDepth; i++){
                for (int j = 0; j <= i; j++){
                    if (currentNode.currentState.pegExists(i, j)){
                        ArrayList <int []> withinReach = currentNode.currentState.getWithinReach(i, j);
                        //for each peg, add states for all possible moves to queue
                        for (int k = 0; k < withinReach.size(); k++){
                            int row = withinReach.get(k)[0];
                            int col = withinReach.get(k)[1];
                            
                            Board newState = new Board(currentNode.currentState);
                            newState.move(i, j, row, col);
                            //get path to new state
                            ArrayList <Board> newPath = new ArrayList<>(currentNode.path);
                            newPath.add(newState);
                            Node newNode = new Node(newState, newPath);
                            searchQueue.add(newNode);
                        }
                    }
                }
            }

			currentNode = searchQueue.poll();
		}

		return currentNode;
    }
}
