
public class Game {
    
    public static void main(String args[]) {
        
        int[] emptyPeg = {0,0};
        Board startBoard = new Board(5, emptyPeg);
        //PegSolitaireSolver solver = new PegSolitaireSolver(startBoard, 5);
        //Node answer = solver.breadthFirstSearch();

        startBoard.printBoard();

    
    }
}
