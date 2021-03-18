package src;

import java.util.ArrayList;

public class Game {

    private static ArrayList<Long> times = new ArrayList<>();
    private static long startTime = 0;
    private static long endTime = 0;
    private static Node answer = null;
    
    public static void main(String args[]) {
        runBFS(5, 2, false);
        runDFS(5, 20, false);
        runDFS(6, 20, false);
        runBiDirSearch(5, 20, false);
        runAStar(5, 20, false);
        runAStar(6, 20, false);
    }

    //Run BFS the given number of times on the given board size. Print out each path if printPath == true 
    private static void runBFS(int boardSize, int repititions, boolean printPath) {
        for (int i = 0; i < repititions; i++) {
            Board tempBoard = new Board(boardSize);
            PegSolitaireSolver tempSolver = new PegSolitaireSolver(tempBoard);
            startTime = System.currentTimeMillis();
            answer = tempSolver.breadthFirstSearch();
            endTime = System.currentTimeMillis();
            times.add((endTime - startTime));
            if (printPath) {
                printSolutionAndPath(answer);
            }
        }
        //Print space between results
        System.out.println("\n\n");
        getAverageWorstAndBest(times, "Breadth-First Search with size " + boardSize);
        times = new ArrayList<>();
    }

    //Run A-Star Search the given number of times on the given board size. Print out each path if printPath == true
    private static void runAStar(int boardSize, int repititions, boolean printPath) {
        for (int i = 0; i < repititions; i++) {
            Board tempBoard = new Board(boardSize);
            PegSolitaireSolver tempSolver = new PegSolitaireSolver(tempBoard);
            startTime = System.currentTimeMillis();
            answer = tempSolver.AStarSearch();
            endTime = System.currentTimeMillis();
            times.add((endTime - startTime));
            if (printPath) {
                printSolutionAndPath(answer);
            }
        }
        //Print space between results
        System.out.println("\n\n");
        getAverageWorstAndBest(times, "A* Search with size " + boardSize);
        times = new ArrayList<>();
    }

    //Run BiDirectional Search the given number of times on the given board size. Print out each path if printPath == true
    private static void runBiDirSearch(int boardSize, int repititions, boolean printPath) {
        for (int i = 0; i < repititions; i++) {
            Board tempBoard = new Board(boardSize);
            PegSolitaireSolver tempSolver = new PegSolitaireSolver(tempBoard);
            startTime = System.currentTimeMillis();
            answer = tempSolver.bidirectionalSearch();
            endTime = System.currentTimeMillis();
            times.add((endTime - startTime));
            if (printPath) {
                printSolutionAndPath(answer);
            }
        }
        //Print space between results
        System.out.println("\n\n");
        getAverageWorstAndBest(times, "BiDirectional Search with size " + boardSize);
        times = new ArrayList<>();
    }

    //Run DFS the given number of times on the given board size. Print out each path if printPath == true
    private static void runDFS(int boardSize, int repititions, boolean printPath) {
        for (int i = 0; i < repititions; i++) {
            Board tempBoard = new Board(boardSize);
            PegSolitaireSolver tempSolver = new PegSolitaireSolver(tempBoard);
            startTime = System.currentTimeMillis();
            answer = tempSolver.depthFirstSearch();
            endTime = System.currentTimeMillis();
            times.add((endTime - startTime));
            if (printPath) {
                printSolutionAndPath(answer);
            }
        }
        //Print space between results
        System.out.println("\n\n");
        getAverageWorstAndBest(times, "Depth-First Search with size " + boardSize);
        times = new ArrayList<>();
    }

    //Print out the average, worst, and best runtimes for the given set of times
    private static void getAverageWorstAndBest(ArrayList<Long> times, String test) {
        long average = 0;
        long worst = 0;
        long best = Long.MAX_VALUE;
        for (int i = 0; i < times.size(); i++) {
            long temp = times.get(i);
            average += times.get(i);
            if (temp > worst) {
                worst = temp;
            } 
            if (best > temp) {
                best = temp;
            }
        }
        System.out.println(test + " took " + average / times.size() + " milliseconds on average.");
        System.out.println(test + " took  " + worst + " milliseconds at the worst.");
        System.out.println(test + " took  " + best + " milliseconds at the best.");
    }

    //Print out the Final Solution of the given answer as well as the path to get there. 
    private static void printSolutionAndPath(Node answer) {
        System.out.println("Solution: ");
        if (answer != null) {
            answer.currentState.printBoard();
            System.out.println("Path: ");
            for (int i = 0; i < answer.path.size(); i++){
                System.out.println(i + ")");
                answer.path.get(i).printBoard();
            }
        }
    }
}
