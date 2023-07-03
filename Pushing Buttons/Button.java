import java.util.Objects;

public class Button {

    private Colour colour;
    private Shape shape;
    private boolean pressed;

    /**
     * Constructs a Button object with the specified color, shape, and state.
     *
     * @param colour  the color of the button
     * @param shape   the shape of the button
     * @param pressed the initial pressed state of the button
     */
    public Button(Colour colour, Shape shape, boolean pressed) {
        this.colour = colour;
        this.shape = shape;
        this.pressed = pressed;
    }

    /**
     * Returns the color of the button.
     *
     * @return the color of the button
     */
    public Colour getColour() {
        return colour;
    }

    /**
     * Sets the color of the button.
     *
     * @param colour the color to set
     */
    public void setColour(Colour colour) {
        this.colour = colour;
    }

    /**
     * Returns the shape of the button.
     *
     * @return the shape of the button
     */
    public Shape getShape() {
        return shape;
    }

    /**
     * Sets the shape of the button.
     *
     * @param shape the shape to set
     */
    public void setShape(Shape shape) {
        this.shape = shape;
    }

    /**
     * Returns the pressed state of the button.
     *
     * @return true if the button is pressed, false otherwise
     */
    public boolean isPressed() {
        return pressed;
    }

    /**
     * Sets the pressed state of the button.
     *
     * @param pressed the pressed state to set
     */
    public void setPressed(boolean pressed) {
        this.pressed = pressed;
    }

    /**
     * Toggles the pressed state of the button.
     */
    public void toggleState() {
        this.pressed = !pressed;
    }

    /**
     * Returns a string representation of the Button object.
     * The string includes the color, shape, and pressed state of the button.
     *
     * @return a string representation of the Button object
     */
    public String toString() {
        return this.colour + " " + this.shape;
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
        if (!(obj instanceof Button)) {
            return false;
        }
        Button other = (Button) obj;
        return this.colour == other.colour &&
                this.shape == other.shape &&
                this.pressed == other.pressed;
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
        return Objects.hash(colour, shape, pressed);
    }
}