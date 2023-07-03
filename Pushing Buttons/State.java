import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Queue;
import java.util.Set;

public class State {
    private List<Button> buttons;
    private List<Button> buttonSequence;

    /**
     * Constructs a ButtonManager object with an empty list of buttons.
     */
    public State() {
        buttons = new ArrayList<>();
        buttonSequence = new ArrayList<>();
    }

    /**
     * Adds a button to the collection.
     *
     * @param button the button to add
     */
    public void addButton(Button button) {
        buttons.add(button);
    }

    /**
     * Toggles the state of the buttons with the same shape or colour as the
     * specified button.
     *
     * @param button the reference button
     */
    public void pressButton(Button button) {
        // Print a message to the console
        System.out.println(
                "Pressed " + button.getColour() + " " + button.getShape() + " button");

        // If the button is already pressed, do nothing
        if (button.isPressed()) {
            System.out.println("Button is already pressed");
            return;
        }
        // Add the button to the sequence of pressed buttons
        buttonSequence.add(button);

        // Get the colour and shape of the reference button
        Colour colour = button.getColour();
        Shape shape = button.getShape();

        // Toggle the state of all buttons with the same colour or shape
        for (Button btn : buttons) {
            if (btn.getColour() == colour || btn.getShape() == shape) {
                btn.toggleState();
            }
        }
    }

    /**
     * Returns whether the puzzle is solved or not.
     *
     * @return true if all buttons are pressed, false otherwise
     */
    public boolean isSolved() {
        // Check if all buttons are pressed
        for (Button button : buttons) {
            if (!button.isPressed()) {
                return false; // If any button is not pressed, the puzzle is not solved
            }
        }
        return true; // All buttons are pressed
    }

    /**
     * Returns a list of neighboring states from the current state by pressing
     * unpressed buttons.
     *
     * @param currentState the current state of the puzzle
     * @return a list of neighboring states
     */
    public static List<State> getNeighbours(State currentState) {
        // Create a list to store neighboring states
        List<State> neighbours = new ArrayList<>();

        // Press each unpressed button and add the resulting state to the list
        for (Button button : currentState.buttons) {
            if (!button.isPressed()) {
                // Create a deep copy of the current state
                State nextState = new State();

                // Copy the button sequence from the current state
                nextState.buttonSequence.addAll(currentState.buttonSequence);

                // Add the pressed button to the sequence
                nextState.buttonSequence.add(button);

                // Copy the buttons from the current state
                for (Button btn : currentState.buttons) {
                    Button copiedButton = new Button(btn.getColour(), btn.getShape(), btn.isPressed());
                    nextState.addButton(copiedButton);
                }

                // Press the button in the copied state
                for (Button btn : nextState.buttons) {
                    if (btn.getColour() == button.getColour() || btn.getShape() == button.getShape()) {
                        btn.toggleState();
                    }
                }
                // Add the copied state to the list of neighbours
                neighbours.add(nextState);
            }
        }
        // Return the list of neighbouring states
        return neighbours;
    }

    /**
     * Solves the puzzle by finding the minimum number of moves required.
     * Prints the initial state, current state, and the number of moves if the
     * puzzle is solved.
     * Prints an error message if no solution is found within the maximum number of
     * moves.
     */
    public void solvePuzzle() {
        // Print the initial state of the puzzle
        System.out.println("Initial puzzle state:");
        System.out.println(this.toString());

        // Check if the puzzle is already solved
        if (isSolved()) {
            System.out.println("Puzzle is already solved!");
            return;
        }
        // Create a queue to store puzzle states
        Queue<State> queue = new LinkedList<>();

        // Create a set to store visited states
        Set<State> visited = new HashSet<>();
        queue.add(this); // Add the initial puzzle state to the queue

        // Iterate through the queue until it is empty
        while (!queue.isEmpty()) {
            // Retrieve the next puzzle state from the queue
            State currentPuzzle = queue.poll();
            if (currentPuzzle.isSolved()) {
                System.out.println("Solved!");
                System.out.println("Button sequence: " + currentPuzzle.buttonSequence);
                System.out.println("Number of moves: " + currentPuzzle.buttonSequence.size());
                return;
            }
            // Iterate through each button and press it if its pressed boolean is false
            for (State state : State.getNeighbours(currentPuzzle)) {
                if (!visited.contains(state)) {
                    queue.add(state);
                    visited.add(state);
                }
                // Print the current state of the puzzle
                System.out.println("Current puzzle state:");
                System.out.println(state.toString());
            }
        }

        // If no solution is found within the maximum number of moves, print an error
        // message
        System.out.println("No solution found within the maximum number of moves!");
    }

    /**
     * Returns a string representation of the Puzzle object in the form of a table.
     * The table displays the shape, color, and pressed state of each button,
     * as well as the overall solved state of the puzzle.
     *
     * @return a string representation of the Puzzle object as a table
     */
    public String toString() {
        // Create a string builder to store the table
        StringBuilder table = new StringBuilder();

        // Append the table header
        table.append("---------------------------------------\n");
        table.append("\u001B[1mShape\t\tColor\t\tPressed\u001B[0m\n");
        table.append("---------------------------------------\n");

        // Append the table body
        for (Button button : buttons) {
            table.append(button.getColour()).append("\t\t")
                    .append(button.getShape()).append("\t\t")
                    .append(button.isPressed()).append("\n");
        }
        // Append the table footer
        table.append("---------------------------------------\n");
        table.append("\u001B[1mSolved\u001B[0m\t\t\t\t").append(isSolved()).append("\n");
        table.append("---------------------------------------\n");

        // Return the table as a string
        return table.toString();
    }

    /**
     * Indicates whether some other object is "equal to" this one.
     * The equality is determined by comparing the colour, shape, and pressed state
     * of the buttons.
     *
     * @param obj the reference object with which to compare
     * @return {@code true} if this object is the same as the obj argument;
     *         {@code false} otherwise.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        State otherState = (State) obj;
        return buttons.equals(otherState.buttons);
    }

    /**
     * Returns a hash code value for the button.
     * The hash code is generated based on the colour, shape, and pressed state of
     * the button.
     *
     * @return a hash code value for this button.
     */
    @Override
    public int hashCode() {
        return Objects.hash(buttons);
    }
}
