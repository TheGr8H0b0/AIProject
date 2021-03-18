package src;

import java.util.ArrayList;

public class Game {
    
    public static void main(String args[]) {
        
        int[] emptyPeg = {0,0};
        ArrayList<Long> times = new ArrayList<>();
        Board startBoard = new Board(5, emptyPeg);
        PegSolitaireSolver solver = new PegSolitaireSolver(startBoard);

        Board sixBoard = new Board(6, emptyPeg);
        PegSolitaireSolver sixSolver = new PegSolitaireSolver(sixBoard);

        long startTime = System.currentTimeMillis();
        Node answer = solver.breadthFirstSearch();
        long endTime = System.currentTimeMillis();
        times.add((endTime - startTime));
        printSolution(answer);
        
        //BFS For 6-sides
        /*
            I ran BFS for 6-sides and we ran out of heap space (Java OutOfMemoryError)
            It took about 75 seconds to run out of heap space
        */


        //DFS For 5-sides
        startTime = System.currentTimeMillis();
        answer = solver.depthFirstSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));
        printSolution(answer);

        //DFS For 6-sides
        startTime = System.currentTimeMillis();
        answer = sixSolver.depthFirstSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));
        printSolution(answer);

        //DFS For 7-sides
        /*
            I ran DFS Search and got no result for over 70 minutes.
        */

        //BiDirectional 5
        startTime = System.currentTimeMillis();
        answer = solver.bidirectionalSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));
        printSolution(answer);

        //BiDirectional 6
        /*
            Also runs out of memory, just like BFS on 6
            Took about 123 seconds, which is longer than BFS 
                - This displays the fact that BFS uses more memory than BiDirectional 
        */


        //AStarSearch
        System.out.println("AStarSearch");
        startTime = System.currentTimeMillis();
        answer = solver.AStarSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));
        printSolution(answer);

        //AStarSearch 6
        System.out.println("AStarSearch");
        startTime = System.currentTimeMillis();
        answer = sixSolver.AStarSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));
        printSolution(answer);

        //AStarSearch 7
        /*
            I ran this algorithm for 27 minutes and no result
        */
        
        for (int i = 0; i < times.size(); i++) {
            System.out.println("Process " + i + " took: " + (times.get(i)/1000.0) + " seconds");
        }
    }

    private static void printSolution(Node answer) {
        System.out.println("Solution: ");
        if (answer != null) {
            answer.currentState.printBoard();
            System.out.println("Path: ");
            for (int i = 0; i < answer.path.size(); i++){
                System.out.println(i + ")");
                answer.path.get(i).printBoard();
            }
        }
    }
}
