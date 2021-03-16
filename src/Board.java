import java.util.Random;
import java.util.ArrayList;

public class Board {

    private int depth;
    private ArrayList<ArrayList<Boolean>> pegs; //true = peg in this location
    private int numPegsOnBoard;

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

        setRandomPegEmpty();
        numPegsOnBoard = -1;
        for (int i = 0; i < depth; i++){
            numPegsOnBoard += (1+i);
        }
    }

    Board(int size, int [] emptyPeg){
        this.depth = size;
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

    //sets a random peg
    private void setRandomPegEmpty(){
        Random rand = new Random();
        int rowNum = rand.nextInt(depth);
        int colNum = rand.nextInt(rowNum);
        pegs.get(rowNum).set(colNum, false);
    }

    private boolean pegExists(int row, int col){
        return pegs.get(row).get(col);
    }

    public void setWithinReach(ArrayList<int []> withinReach, int row, int col){
        if (row + 2 < depth){
            //add SW slot
            withinReach.add(new int []{row+2, col});
            if (col + 2 <= row + 2){
                //add SE slot
                withinReach.add(new int[]{row+2, col+2});
            }
        }
        if (row - 2 >= 0){
            //add NE slot
            withinReach.add(new int []{row-2, col});
            if (col - 2 >= 0){
                //add NW slot
                withinReach.add(new int[]{row-2, col-2});
            }
        }
        if (col - 2 >= 0) {
            withinReach.add(new int[] {row, col-2});
        }
        if (col + 2 <= row) {
            withinReach.add(new int[] {row, col+2});
        }
    }

    //can be used for getting row and column
    private int getMiddle(int start, int end){
        if ((Math.abs(start - end) != 2) && (start != end)){
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
        //start and end location must be greater than 1 unit away
        if ((startRow + 1 <= endRow || startRow - 1 >= endRow) 
            || (startCol + 1 <= endCol || startCol -1 >= endCol)){
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
