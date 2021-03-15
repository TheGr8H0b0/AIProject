import java.util.Random;
import java.util.ArrayList;
import java.lang.Math;

public class Board {

    private final int depth = 5;
    private ArrayList<ArrayList<Boolean>> pegs; //true = peg in this location

    Board(){
        pegs = new ArrayList<>();
        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>();
            for (int j = 0; j < i; j++){
                row.add(true);
            }
            pegs.add(row);
        }

        setRandomPegEmpty();
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
        withinReach = new ArrayList<>();
        if (row + 2 < depth){
            //add SW slot
            withinReach.add(new int []{row+2, col});
            if (col + 2 < depth){
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
        //start and end location cannot be the same
        if ((startRow == endRow) && (startCol == endCol)){
            return false;
        }

        int middleRow = getMiddle(startRow, endRow);
        int middleCol = getMiddle(startCol, endCol);

        //peg is not two slots away
        if ((middleRow == -1) || middleCol == -1){
            return false;
        }

        if (pegExists(middleRow, middleCol)){
            return true;
        }

        return false;
    }

}
