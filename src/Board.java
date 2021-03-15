import java.util.Random;
import java.util.ArrayList;

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



}
