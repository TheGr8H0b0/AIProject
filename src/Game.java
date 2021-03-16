
public class Game {
    
    public static void main(String args[]) {
        
        int[] emptyPeg = {0,0};
        Board startBoard = new Board(5, emptyPeg);
        PegSolitaireSolver solver = new PegSolitaireSolver(startBoard, 5);
      //  solver.initialState.printBoard();
        Node answer = solver.breadthFirstSearch();

        answer.currentState.printBoard();
    
        //System.out.println(startBoard.canMove(2, 0, 0, 0));

    }
}
