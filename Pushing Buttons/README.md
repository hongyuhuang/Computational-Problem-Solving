# COSC326 Étude 10 Pushing Buttons

![Pushing Buttons](images/pushingbuttons.png)


## Introduction
"Pushing Buttons" is an engaging mini-game featuring a variety of challenging puzzles for players to solve. In each level, the objective is to press the buttons strategically until all of them are in the pressed state. However, there are certain rules to follow.

You can only press buttons that are currently active, meaning they haven't been pressed before. When you press a button, it triggers a chain reaction that flips the pressed state of all other buttons that share either the same shape or color as the button you just pressed. This mechanism adds complexity and requires careful thinking to find the optimal sequence of button presses.

By navigating through the levels and successfully pressing the buttons, players can advance and tackle increasingly intricate puzzles. Each puzzle presents a unique configuration of buttons, requiring players to analyze and plan their moves accordingly.

In order for us to solve each level as efficiently as possible, we decided to simulate the game in our own Java implementation. In order to do this, we created a Button class which had three class variables, shape, colour and pressed. We also created a State class which has two class variables, buttons which is a list of buttons representing the current state of the puzzle and buttonsSequence which is a list of the buttons that have been pressed before reaching this current state.

![Button Class](images/buttons.png)

---
## Our BFS Algorithm

![Solve Method](images/solveMethod.png)

To determine the minimum number of moves required to solve the puzzle, we implemented a Breadth First Search (BFS) algorithm. This algorithm systematically explores all possible states of the puzzle by traversing through each state that can be reached from the initial state.

To facilitate the BFS algorithm, we utilized several essential functions and data structures. Firstly, we employed a queue to store the states of the puzzle. The initial state was added to the queue, and subsequent states were processed in a first-in, first-out manner.

Upon dequeuing a state from the queue, we checked if it represented a solved puzzle. If not, we utilized the getNeighbours() function to obtain a list of all possible states that could be reached from the current state.

Next, we iterated through each of these returned states and examined whether they had been visited before. To keep track of visited states, we employed a hashset data structure. If the hashset did not contain the current state, it indicated that the state had not been visited previously. In this case, we added the state to both the queue and the hashset to mark it as visited.

By utilizing this algorithm, we ensured that every possible state of the puzzle was explored. The BFS algorithm terminates once it discovers the shortest "path" or sequence of moves required to solve the puzzle. This path is determined as the first one encountered during the BFS traversal.

Overall, this approach guarantees an exhaustive search through all possible states, ultimately leading to the determination of the shortest sequence of button presses required to solve the puzzle.

---
## Important structures and fixes
The hashset plays a crucial role in the BFS algorithm by preventing the revisiting of states that have already been explored. During our development process, we encountered an issue with puzzle 17, where the puzzle appeared to be unsolvable. After investigating the problem, we discovered that the hashset was not accurately comparing states to determine whether a particular state was already contained in the hashset or not.

To address this issue, we overrode the equals() and hashCode() methods in both the State and Button classes. By doing so, we ensured that the hashset's contains() method compared the contents of the objects rather than their references. This allowed for accurate state comparison and prevented the addition of duplicate states to the hashset.

![Override](images/buttonsOverride.png)

![Override](images/stateOverride.png)

The BFS solution we developed for puzzle 8, 17 and 24 can be generalized to any level in the Pushing Buttons game. All that is required is to establish the game's layout by creating the appropriate buttons and adding them to the State class, as demonstrated in the main code for puzzles 8, 17, and 24.

By utilizing this BFS algorithm and adapting it to different puzzle configurations, players can solve various levels of the Pushing Buttons game by determining the minimum number of moves required to press all the buttons and achieve a solved state.

---

## Solutions

### Level 8

Can be solved with a minimum of 3 moves.
![Puzzle 8 Step 1](images/level8a.png)
Press Blue Star
![Puzzle 8 Step 2](images/level8b.png)
Press Blue Circle
![Puzzle 8 Step 3](images/level8c.png)
Press Yellow Star
![Puzzle 8 Step 4](images/level8d.png)

### Level 17

Can be solved with a minimum of 10 moves.
![Puzzle 17 Step 1](images/level17a.png)
Press Blue Circle
![Puzzle 17 Step 2](images/level17b.png)
Press Orange Circle
![Puzzle 17 Step 3](images/level17c.png)
Press Blue Circle
![Puzzle 17 Step 4](images/level17d.png)
Press Orange Diamond
![Puzzle 17 Step 5](images/level17e.png)
Press Green Diamond
![Puzzle 17 Step 6](images/level17f.png)
Press Red Triangle
![Puzzle 17 Step 7](images/level17g.png)
Press Green Star
![Puzzle 17 Step 8](images/level17h.png)
Press Green Diamond
![Puzzle 17 Step 9](images/level17i.png)
Press Purple Star
![Puzzle 17 Step 10](images/level17j.png)
Press Yellow Square
![Puzzle 17 Step 10](images/level17k.png)

### Level 24

Can be solved with a minimum of 6 moves.
![Puzzle 17 Step 1](images/level24a.png)
Press Green Diamond
![Puzzle 17 Step 1](images/level24b.png)
Press Purple Star
![Puzzle 17 Step 1](images/level24c.png)
Press Purple Diamond
![Puzzle 17 Step 1](images/level24d.png)
Press Yellow Diamond
![Puzzle 17 Step 1](images/level24e.png)
Press Purple Star
![Puzzle 17 Step 1](images/level24f.png)
Press Blue Cross
![Puzzle 17 Step 1](images/level24g.png)




