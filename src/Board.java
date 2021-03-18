package src;

import java.util.Random;
import java.util.ArrayList;

public class Board implements Comparable<Board> {

    private int depth;
    private ArrayList<ArrayList<Boolean>> pegs; //true = peg in this location
    private int numPegsOnBoard;
    private int[] initialEmpty;

    Board(int size){
        this.depth = size;
        pegs = new ArrayList<>();
        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>(depth);
            for (int j = 0; j <= i; j++){
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
        depth = size;
        initialEmpty = emptyPeg;
        pegs = new ArrayList<>();
        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>(depth);
            for (int j = 0; j <= i; j++){
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
        this.numPegsOnBoard = old.numPegsOnBoard;
        this.initialEmpty = old.initialEmpty;

        this.pegs = new ArrayList<>();
        //copy over pegs
        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>(i+1);
            for (int j = 0; j <= i; j++){
                row.add(old.pegExists(i, j));
            }
            pegs.add(row);
        }
    }

    Board(int [] pegSetters, int size) {
        this.depth = size;
        this.numPegsOnBoard = 1;
        this.pegs = new ArrayList<>(size);

        for (int i = 0; i < depth; i++){
            ArrayList <Boolean> row = new ArrayList<>();
            for (int j = 0; j <= i; j++){
                row.add(false);
            }
            pegs.add(row);
        }

        pegs.get(pegSetters[0]).set(pegSetters[1], true);
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

    public boolean pegExists(int row, int col){
        try {
            return pegs.get(row).get(col);
        } catch(IndexOutOfBoundsException e) {
            return false;
        }
    }

    //returns only pegs you can move to
    public ArrayList<int []> getWithinReach(int row, int col){
        ArrayList<int []> withinReach = new ArrayList<>(6);
        //Check NW Slot
        if (canMove(row, col, row - 2, col-2)) {
            withinReach.add(new int[] {row - 2, col - 2});
        }

        //Check NE Slot
        if (canMove(row, col, row - 2, col)) {
            withinReach.add(new int[] {row - 2, col});
        }

        //Check W Slot
        if (canMove(row, col, row, col - 2)) {
            withinReach.add(new int[] {row, col - 2});
        }

        //Check E Slot
        if (canMove(row, col, row, col + 2)) {
            withinReach.add(new int[] {row, col + 2});
        }
        
        //Check SW Slot
        if (canMove(row, col, row + 2, col)) {
            withinReach.add(new int[] {row + 2, col});
        }

        //Check SE Slot
        if (canMove(row, col, row + 2, col + 2)) {
            withinReach.add(new int[] {row + 2, col + 2});
        }

        return withinReach;
    }

    //can be used for getting row and column
    private int getMiddle(int start, int end){
        if (Math.abs(start - end) != 2 && (start != end)){
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

    //returns only pegs you can move to
    public ArrayList<int []> getWithinReachBackwards(int row, int col){
        ArrayList<int []> withinReach = new ArrayList<>(6);
        //Check NW Slot
        if (canMoveBackwards(row, col, row - 2, col-2)) {
            withinReach.add(new int[] {row - 2, col - 2});
        }

        //Check NE Slot
        if (canMoveBackwards(row, col, row - 2, col)) {
            withinReach.add(new int[] {row - 2, col});
        }

        //Check W Slot
        if (canMoveBackwards(row, col, row, col - 2)) {
            withinReach.add(new int[] {row, col - 2});
        }

        //Check E Slot
        if (canMoveBackwards(row, col, row, col + 2)) {
            withinReach.add(new int[] {row, col + 2});
        }
        
        //Check SW Slot
        if (canMoveBackwards(row, col, row + 2, col)) {
            withinReach.add(new int[] {row + 2, col});
        }

        //Check SE Slot
        if (canMoveBackwards(row, col, row + 2, col + 2)) {
            withinReach.add(new int[] {row + 2, col + 2});
        }

        return withinReach;
    }

    public boolean canMove(int startRow, int startCol, int endRow, int endCol){
        //out of bounds
        if ((endRow < 0) || (endRow >= depth) || (endCol < 0) || (endCol > endRow)){
            return false;
        }
        
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

    public boolean canMoveBackwards(int startRow, int startCol, int endRow, int endCol){
        //out of bounds
        if ((endRow < 0) || (endRow >= depth) || (endCol < 0) || (endCol > endRow)){
            return false;
        }
        
        //there needs to be a peg in the starting position
        if (!pegExists(startRow, startCol)){
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

        int middleRow = getMiddle(startRow, endRow);
        int middleCol = getMiddle(startCol, endCol);

        //peg is not two slots away
        if ((middleRow == -1) || middleCol == -1){
            return false;
        }

        //There is NO peg in the middle, so we can perform a reverse move
        if (!pegExists(middleRow, middleCol)) {
            return true;
        }

        return false;
    }

    public void moveBackwards(int startRow, int startCol, int endRow, int endCol){
        if (canMoveBackwards(startRow, startCol, endRow, endCol)){
            int middleRow = getMiddle(startRow, endRow);
            int middleCol = getMiddle(startCol, endCol);
            pegs.get(startRow).set(startCol, false);
            pegs.get(middleRow).set(middleCol, true);
            pegs.get(endRow).set(endCol, true);
            numPegsOnBoard++;
        }
    }

    public ArrayList<ArrayList<Double>> getWeights() {
        ArrayList<ArrayList<Double>> weights = new ArrayList<>();
        for (int i = 0; i < depth; i++) {
            ArrayList<Double> row = new ArrayList<>();
            for (int j = 0; j <= i; j++) {
                int denom = 0;
                if (pegExists(i, j)) {
                    //Check if peg is next to corner (WORST)
                    if ((j == 1 && i == 1) || (j == 0 && i == 1)) {
                        denom = 1;
                    }
                    else if ((j == i - 1 && i == depth - 1) || (j == 0 && i == depth - 1)) {
                        denom = 1;
                    }
                    else if ((j == 1 && i == depth) || (j == 1 && i == depth)) {
                        denom = 1;
                    }
                    //Check if peg is on corner (Kinda bad)
                    else if (j == 0 && i == 0) {
                        denom = 2;
                    }
                    else if (j == i && i == depth) {
                        denom = 2;
                    }
                    else if (j == 0 && i == depth) {
                        denom = 2;
                    }
                    //Check if peg is on side (Not too bad) 
                    else if (j == 0) {
                        denom = 3;
                    }
                    else if (j == i) {
                        denom = 3;
                    } else {
                        denom = 4;
                    }
                }

                if (denom == 0) {
                    row.add(0.0);
                } else {
                    Double heuristic = 1.0 / denom;
                    row.add(heuristic);
                }
            }
            weights.add(row);
        }
        return weights;
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

    public void printBoard(){
        for (int i = 0; i < depth; i++){
            String row = "";
            //build left empty slot
            int j;
            for (j = i; j < depth - 1; j++){
                row = row + " ";
            }
            //fill pegs
            char marker;    //X if there's a peg here, O otherwise
            for (j = 0; j < i; j++){
                if (pegExists(i, j)){
                    marker = 'X';
                }
                else{
                    marker = 'O';
                }
                row = row + marker + " ";
            }
            if (pegExists(i, j)){
                marker = 'X';
            }
            else{
                marker = 'O';
            }
            row = row + marker;
            System.out.println(row);
        }
    }

    @Override
    public int compareTo(Board other) {
        ArrayList<ArrayList<Double>> weightList = getWeights();
        Double weightSumPrimary = 0.0;
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j <= i; j++) {
                weightSumPrimary += weightList.get(i).get(j);
            }
        }

        weightList = other.getWeights();
        Double weightSumSecondary = 0.0;
        for (int i = 0; i < depth; i++) {
            for (int j = 0; j <= i; j++) {
                weightSumSecondary += weightList.get(i).get(j);
            }
        }
        double weightDifference = weightSumPrimary - weightSumSecondary;
        if (weightDifference < 0) {
            return -1;
        } else if (weightDifference > 0) {
            return 1;
        }
        return 0;
    }
}
