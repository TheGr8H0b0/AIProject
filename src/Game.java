import java.util.ArrayList;

public class Game {
    
    public static void main(String args[]) {
        
        int[] emptyPeg = {0,0};
        Board startBoard = new Board(5, emptyPeg);
        PegSolitaireSolver solver = new PegSolitaireSolver(startBoard, 5);
      //  solver.initialState.printBoard();
        Node answer = solver.breadthFirstSearch();

        System.out.println("Solution: ");
        answer.currentState.printBoard();
        System.out.println("Path: ");
        for (int i = 0; i < answer.path.size(); i++){
            System.out.println(i + ")");
            answer.path.get(i).printBoard();
        }

        // Board testBoard = new Board(5, emptyPeg);
        // testBoard.printBoard();
        // testBoard.move(2, 0, 0, 0);
        // testBoard.printBoard();
        // testBoard.move(2, 2, 2, 0);
        // testBoard.printBoard();
        // testBoard.move(0, 0, 2, 2);
        // testBoard.printBoard();
        // testBoard.move(3, 0, 1, 0);
        // testBoard.printBoard();
        // ArrayList <int []> withinReach = testBoard.getWithinReach(3, 2);

        // for (int i = 0; i < withinReach.size(); i++){
        //     System.out.println(withinReach.get(i)[0] + ", " + withinReach.get(i)[1]);
        // }

        

    }
}
