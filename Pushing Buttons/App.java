public class App {

    public static void main(String[] args) {
        // State puzzle8 = puzzle8();
        // puzzle8.solvePuzzle();
        
        // State puzzle17 = puzzle17();
        // puzzle17.solvePuzzle();

        State puzzle24 = puzzle24();
        puzzle24.solvePuzzle();
    }

    public static State puzzle8() {
        // Create a new State object
        State state = new State();

        // Create new Button objects
        Button blueCircle = new Button(Colour.BLUE, Shape.CIRCLE, true);
        Button blueDiamond = new Button(Colour.BLUE, Shape.DIAMOND, true);
        Button blueStar = new Button(Colour.BLUE, Shape.STAR, false);
        Button yellowCircle = new Button(Colour.YELLOW, Shape.CIRCLE, true);
        Button yellowStar = new Button(Colour.YELLOW, Shape.STAR, true);
        Button redCircle = new Button(Colour.RED, Shape.CIRCLE, false);
        Button redDiamond = new Button(Colour.RED, Shape.DIAMOND, true);
        Button redStar = new Button(Colour.RED, Shape.STAR, true);

        // Add the buttons to the state
        state.addButton(blueCircle);
        state.addButton(blueDiamond);
        state.addButton(blueStar);
        state.addButton(yellowCircle);
        state.addButton(yellowStar);
        state.addButton(redCircle);
        state.addButton(redDiamond);
        state.addButton(redStar);

        // Return the state
        return state;
    }

    public static State puzzle17() {
        // Create a new State object
        State state = new State();

        // Create new Button objects
        Button blueCircle = new Button(Colour.BLUE, Shape.CIRCLE, false);
        Button orangeCircle = new Button(Colour.ORANGE, Shape.CIRCLE, true);
        Button orangeDiamond = new Button(Colour.ORANGE, Shape.DIAMOND, true);
        Button greenDiamond = new Button(Colour.GREEN, Shape.DIAMOND, true);
        Button greenTriangle = new Button(Colour.GREEN, Shape.TRIANGLE, true);
        Button redTriangle = new Button(Colour.RED, Shape.TRIANGLE, false);
        Button greenStar = new Button(Colour.GREEN, Shape.STAR, true);
        Button purpleStar = new Button(Colour.PURPLE, Shape.STAR, true);
        Button purpleSquare = new Button(Colour.PURPLE, Shape.SQUARE, true);
        Button yellowSquare = new Button(Colour.YELLOW, Shape.SQUARE, false);

        // Add the buttons to the state
        state.addButton(blueCircle);
        state.addButton(orangeCircle);
        state.addButton(orangeDiamond);
        state.addButton(greenDiamond);
        state.addButton(greenTriangle);
        state.addButton(redTriangle);
        state.addButton(greenStar);
        state.addButton(purpleStar);
        state.addButton(purpleSquare);
        state.addButton(yellowSquare);

        // Return the state
        return state;
    }

    public static State puzzle24() {
        // Create a new State object
        State puzzle24 = new State();

        // Create new Button objects
        Button greenDiamond = new Button(Colour.GREEN, Shape.DIAMOND, false);
        Button purpleDiamond = new Button(Colour.PURPLE, Shape.DIAMOND, false);
        Button yellowDiamond = new Button(Colour.YELLOW, Shape.DIAMOND, false);
        Button purpleStar = new Button(Colour.PURPLE, Shape.STAR, false);
        Button yellowStar = new Button(Colour.YELLOW, Shape.STAR, false);
        Button yellowSquare = new Button(Colour.YELLOW, Shape.SQUARE, false);
        Button blueSquare = new Button(Colour.BLUE, Shape.SQUARE, false);
        Button blueCross = new Button(Colour.BLUE, Shape.CROSS, false);

        // Add the buttons to the state
        puzzle24.addButton(greenDiamond);
        puzzle24.addButton(purpleDiamond);
        puzzle24.addButton(yellowDiamond);
        puzzle24.addButton(purpleStar);
        puzzle24.addButton(yellowStar);
        puzzle24.addButton(yellowSquare);
        puzzle24.addButton(blueSquare);
        puzzle24.addButton(blueCross);

        // Return the state
        return puzzle24;
    }
}
