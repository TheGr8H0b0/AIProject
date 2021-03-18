# **Cracker-Barrel AI Solver**

>## General Notes:
>Run Game.java to run the tests
>
>- Currently the 4 algorithms are each tested 20 times for each board size they can reasonably solve.
>- The average, worst, and best runtimes for those 20, randomized boards are then printed to the console.
>- Simply turn the passed-in boolean to true if you want to have the solution and path taken printed out.

>## Program Info:
>Board is where a "Cracker-Barrel Board" is created and its data stored.
>
>Game is where the main function and a few helper functions for main lie.
>
>Node is where the custom Nodes for the search algorithm are created.
>
>PegSolitaireSolver is where the actual search algorithms are implemented

## *BFS*
BFS runs out of memory on a board size of size 6 after about 75 seconds.
- Only run BFS on size 5

## *DFS*
DFS on a board size of 7 was run for over an hour with no result.
- Only run DFS on sizes 5 and 6

## *BiDirectional Search*
BiDirectional Search runs out of memory on board size of 6 after about 120 seconds
- Only run BiDirectional Search on size 5

## *A-Star*
A* on a board of size 7 was run for over an hour with no result.
- Only run A* on sizes 5 and 6
