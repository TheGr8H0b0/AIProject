package src;

import java.util.Random;
import java.util.ArrayList;

public class Board implements Comparable<Board> {

    private int depth;
    private ArrayList<ArrayList<Boolean>> pegs; //true = peg in this location
    private int numPegsOnBoard;
    private int[] initialEmpty;

    //Constructor that will set a random peg as empty
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

    //Constructor that will set a peg at the given coordinates as empty (Throws an error if that coordinate does not exist)
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

        if (emptyPeg[0] >= depth || emptyPeg[1] > pegs.get(emptyPeg[0]).size()) {
            throw new Error("The given coordinates for the empty peg do not exist");
        }
        pegs.get(emptyPeg[0]).set(emptyPeg[1], false);
        numPegsOnBoard = -1;
        for (int i = 0; i < depth; i++){
            numPegsOnBoard += (1+i);
        }
    }

    //Constructor that will copy a previously supplied board state
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

    /*
        Constructor that will create an empty board and put one peg onto the board at the given slot.
        Throws an error if that coordinate does not exist
    */
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
        
        if (pegSetters[0] >= depth || pegSetters[1] > pegs.get(pegSetters[0]).size()) {
            throw new Error("The given coordinates for the empty peg do not exist");
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

    //sets a random peg to be empty
    private int [] setRandomPegEmpty(){
        Random rand = new Random();
        int rowNum = rand.nextInt(depth);
        int colNum = rand.nextInt(rowNum + 1);
        pegs.get(rowNum).set(colNum, false);
        return new int[]{rowNum, colNum};
    }

    //Returns if a peg exists at the given location. (All non-existant locations return false)
    public boolean pegExists(int row, int col){
        try {
            return pegs.get(row).get(col);
        } catch(IndexOutOfBoundsException e) {
            return false;
        }
    }

    //Returns an ArrayList of coordinates of the positions you can move to
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

    /*
        Grabs the value in between the supplied start and end points.
        This is used to grab the middle peg after a jump has been performed
        ** Returns -1 if the supplied coordinates do not have a single space between them
    */
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

    //Returns true if the given end positions can be jumped to
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

    //Returns an ArrayList of coordinates for the positions you can have moved from (For BiDirectional)
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

    //Returns true if the given end positions can be jumped from (For BiDirectional)
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

    //Performs a backwards move, placing IN a peg in the middle position
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

    //Calculates the heuristic for the given board (For A-Star Search)
    public double getWeight() {
        double weight = 0;
        for (int i = 0; i < depth; i++) {
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
                    weight += 0.0;
                } else {
                    weight += (1.0 / denom);
                }
            }
        }
        return weight;
    }

    //Performs a move on the board, REMOVING the middle peg between start and end positions
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

    //Prints out the board in a human-readable format
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

    //Overriding the compare method so that one Board can be easily compared to another (For A-Star)
    @Override
    public int compareTo(Board other) {
        double weightDifference = getWeight() - other.getWeight();
        if (weightDifference < 0) {
            return -1;
        } else if (weightDifference > 0) {
            return 1;
        }
        return 0;
    }
}
