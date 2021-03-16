
import java.util.Random;
import java.util.ArrayList;

public class Board {

    private int depth;
    private ArrayList<ArrayList<Boolean>> pegs; //true = peg in this location
    private int numPegsOnBoard;
    private int[] initialEmpty;

    Board(int size){
        this.depth = size;
        pegs = new ArrayList<>();
        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>();
            for (int j = 0; j < i; j++){
                row.add(true);
            }
            pegs.add(row);
        }

        this.initialEmpty = setRandomPegEmpty();
        numPegsOnBoard = -1;
        for (int i = 0; i < depth; i++){
            numPegsOnBoard += (1+i);
        }
    }

    Board(int size, int [] emptyPeg){
        this.depth = size;
        this.initialEmpty = emptyPeg;
        pegs = new ArrayList<>();
        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>();
            for (int j = 0; j < i; j++){
                row.add(true);
            }
            pegs.add(row);
        }

        pegs.get(emptyPeg[0]).set(emptyPeg[1], false);
        numPegsOnBoard = -1;
        for (int i = 0; i < depth; i++){
            numPegsOnBoard += (1+i);
        }
    }

    Board(Board old){
        this.depth = old.depth;
        this.pegs = new ArrayList<>(old.pegs);
        this.numPegsOnBoard = old.numPegsOnBoard;
    }

    public int getNumPegs(){
        return numPegsOnBoard;
    }

    public int getDepth() {
        return depth;
    }

    public int[] getInitialEmpty() {
        return initialEmpty;
    }

    //sets a random peg
    private int [] setRandomPegEmpty(){
        Random rand = new Random();
        int rowNum = rand.nextInt(depth);
        int colNum = rand.nextInt(rowNum);
        pegs.get(rowNum).set(colNum, false);
        return new int[]{rowNum, colNum};
    }

    private boolean pegExists(int row, int col){
        try {
            return pegs.get(row).get(col);
        } catch(IndexOutOfBoundsException e) {
            return false;
        }
    }

    public ArrayList<int []> getWithinReach(int row, int col){
        ArrayList<int []> withinReach = new ArrayList<>();
        //Check SW Slot
        if (canMove(row, col, row + 2, col)) {
            withinReach.add(new int[] {row + 2, col});
        }

        //Check SE Slot
        if (canMove(row, col, row + 2, col + 2)) {
            withinReach.add(new int[] {row + 2, col + 2});
        }

        //Check NW Slot
        if (canMove(row, col, row - 2, col)) {
            withinReach.add(new int[] {row - 2, col});
        }

        //Check NE Slot
        if (canMove(row, col, row - 2, col)) {
            withinReach.add(new int[] {row - 2, col - 2});
        }

        //Check E Slot
        if (canMove(row, col, row, col + 2)) {
            withinReach.add(new int[] {row, col + 2});
        }

        //Check W Slot
        if (canMove(row, col, row, col - 2)) {
            withinReach.add(new int[] {row, col - 2});
        }
        return withinReach;
    }

    //can be used for getting row and column
    private int getMiddle(int start, int end){
        if ((Math.abs(start - end) != 2) || (start == end)){
            return -1;
        }
        if (start > end){
            return start-1;
        }
        else if (start < end){
            return start+1;
        }
        else{
            return start;
        }
    }

    public boolean canMove(int startRow, int startCol, int endRow, int endCol){
        //there needs to be a peg in the starting position
        if (pegExists(startRow, startCol) == false){
            return false;
        }
        //there cannot be a peg in the ending position
        if (pegExists(endRow, endCol)){
            return false;
        }
        //Start and end location must be different
        if (startRow == endRow && startCol == endCol) {
            return false;
        }
        //start and end location must be greater 2 units away in any of the 6 movement directions
        if (!(Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 0)
            || !(Math.abs(startRow - endRow) == 2 && Math.abs(startCol - endCol) == 2)
            || !(Math.abs(startRow - endRow) == 0 && Math.abs(startCol - endCol) == 2)) {
            return false;
        }

        int middleRow = getMiddle(startRow, endRow);
        int middleCol = getMiddle(startCol, endCol);

        //peg is not two slots away
        if ((middleRow == -1) || middleCol == -1){
            return false;
        }

        if (pegExists(middleRow, middleCol)) {
            return true;
        }

        return false;
    }

    public void move(int startRow, int startCol, int endRow, int endCol){
        if (canMove(startRow, startCol, endRow, endCol)){
            int middleRow = getMiddle(startRow, endRow);
            int middleCol = getMiddle(startCol, endCol);
            pegs.get(startRow).set(startCol, false);
            pegs.get(middleRow).set(middleCol, false);
            pegs.get(endRow).set(endCol, true);
            numPegsOnBoard--;
        }
    }
}
