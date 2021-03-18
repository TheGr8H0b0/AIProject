package src;

import java.sql.Date;
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
        System.out.println("Solution: ");
        answer.currentState.printBoard();
        System.out.println("Path: ");
        for (int i = 0; i < answer.path.size(); i++){
            System.out.println(i + ")");
            answer.path.get(i).printBoard();
        }
        
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

        System.out.println("Solution: ");
        if (answer != null) {
            answer.currentState.printBoard();
            System.out.println("Path: ");
            for (int i = 0; i < answer.path.size(); i++){
                System.out.println(i + ")");
                answer.path.get(i).printBoard();
            }
        }

        //DFS For 6-sides
        startTime = System.currentTimeMillis();
        answer = sixSolver.depthFirstSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));

        System.out.println("Solution: ");
        if (answer != null) {
            answer.currentState.printBoard();
            System.out.println("Path: ");
            for (int i = 0; i < answer.path.size(); i++){
                System.out.println(i + ")");
                answer.path.get(i).printBoard();
            }
        }

        //DFS For 7-sides
        /*
            I ran DFS Search and got no result for over 30 minutes.
            I will try again to get a result when I have more time to run the test.
        */
        Board sevenBoard = new Board(7, emptyPeg);
        PegSolitaireSolver sevenSolver = new PegSolitaireSolver(sevenBoard);
        startTime = System.currentTimeMillis();
        answer = sevenSolver.depthFirstSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));

        System.out.println("Solution: ");
        if (answer != null) {
            answer.currentState.printBoard();
            System.out.println("Path: ");
            for (int i = 0; i < answer.path.size(); i++){
                System.out.println(i + ")");
                answer.path.get(i).printBoard();
            }
        }

        //BiDirectional 5
        startTime = System.currentTimeMillis();
        answer = solver.bidirectionalSearch();
        endTime = System.currentTimeMillis();
        times.add((endTime - startTime));

        System.out.println("Solution for BiDirectional: ");
        if (answer != null) {
            answer.currentState.printBoard();
            System.out.println("Path: ");
            for (int i = 0; i < answer.path.size(); i++){
                System.out.println(i + ")");
                answer.path.get(i).printBoard();
            }
        }

        //BiDirectional 6
        /*
            Also runs out of memory, just like BFS on 6
            Took about 123 seconds, which is longer than BFS 
                - This displays the fact that BFS uses more memory than BiDirectional 
        */

        
        for (int i = 0; i < times.size(); i++) {
            System.out.println("Process " + i + " took: " + (times.get(i)/1000.0) + " seconds");
        }
    }
}
