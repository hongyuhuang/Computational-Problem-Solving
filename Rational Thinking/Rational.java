public class Rational {
    Integer numerator;
    Integer denominator;

    /**
     * Constructs a new Rational object with default values for numerator and
     * denominator. The default numerator and denominator are set to zero.
     */
    public Rational() {
        this.numerator = new Integer();
        this.denominator = new Integer();
    }

    /**
     * Constructs a new Rational object by copying the values from another Rational
     * object.
     * 
     * @param r the Rational object to be copied
     */
    public Rational(Rational r) {
        this.numerator = r.numerator;
        this.denominator = r.denominator;
    }

    /**
     * Constructs a new Rational object with the specified numerator and a
     * denominator of 1.
     * 
     * @param a the numerator value for the Rational
     */
    public Rational(Integer a) {
        this.numerator = a;
        this.denominator = new Integer("1");
    }

    /**
     * Constructs a new Rational object with the specified numerator and
     * denominator. The function also performs normalization by adjusting the sign
     * of the Rational to ensure the numerator and denominator have the same sign
     * (if necessary).
     * 
     * @param a the numerator value for the Rational
     * @param b the denominator value for the Rational
     */
    public Rational(Integer a, Integer b) {
        this.numerator = a;
        this.denominator = b;
        if (this.denominator.isNegative == true && this.numerator.isNegative == false) {
            this.denominator.isNegative = false;
            this.numerator.isNegative = true;
        } else if (this.denominator.isNegative == true && this.numerator.isNegative == true) {
            this.denominator.isNegative = false;
            this.numerator.isNegative = false;
        }
    }

    /**
     * Constructs a Rational object with the given numerator, denominator, and sign.
     * 
     * @param a The integer part of the rational number.
     * @param b The numerator of the fractional part of the rational number.
     * @param c The denominator of the fractional part of the rational number.
     */
    public Rational(Integer a, Integer b, Integer c) {
        this.denominator = c;
        if (Integer.lessThan(new Integer(a), new Integer())) {
            Integer multiplier = Integer.multiply(a, this.denominator);
            multiplier.isNegative = false;
            this.numerator = Integer.plus(new Integer(b), multiplier);
            this.numerator.isNegative = true;
        } else {
            this.numerator = Integer.plus(new Integer(b),
                    Integer.multiply(new Integer(a), this.denominator));
        }
    }

    /**
     * Constructs a new Rational object from a string representation.
     * The string representation can be in one of the following formats:
     * - Decimal format: "x.y", where x is the whole part and y is the fractional
     * part.
     * - Fraction format: "x/y", where x is the numerator and y is the denominator.
     * - Integer format: "x", where x is a single integer.
     * If the string contains a decimal point, the resulting rational number will be
     * in decimal format.
     * If the string contains a slash ('/'), the resulting rational number will be
     * in fraction format.
     * Otherwise, the string is interpreted as an integer.
     * 
     * @param s the string representation of the rational number
     */
    public Rational(String s) {
        if (s.contains(".")) { // Case for when the string contains a whole number and a fraction
            String[] whole = s.split("\\.");
            String[] parts = s.substring(s.indexOf(".") + 1).split("/");
            this.numerator = new Integer(parts[0]);
            this.denominator = new Integer(parts[1]);
            if (Integer.lessThan(new Integer(whole[0]), new Integer())) {
                Integer multiplier = Integer.multiply(new Integer(whole[0]), this.denominator);
                multiplier.isNegative = false;
                this.numerator = Integer.plus(new Integer(parts[0]), multiplier);
                this.numerator.isNegative = true;
            } else {
                this.numerator = Integer.plus(new Integer(parts[0]),
                        Integer.multiply(new Integer(whole[0]), this.denominator));
            }
        } else if (s.contains("/")) { // Case for when the string contains a fraction
            String[] parts = s.split("/");
            this.numerator = new Integer(parts[0]);
            this.denominator = new Integer(parts[1]);
        } else { // Case for when the string contains a whole number
            this.numerator = new Integer(s);
            this.denominator = new Integer("1");
        }
    }

    /**
     * Adds the specified Rational to this Rational and returns the result as a new
     * Rational object.
     * 
     * @param other the Rational to be added
     * @return the sum of this Rational and the specified Rational
     */
    public Rational plus(Rational other) {
        Integer newNumerator = Integer.plus(Integer.multiply(this.numerator, other.denominator),
                Integer.multiply(other.numerator, this.denominator));
        Integer newDenominator = Integer.multiply(this.denominator, other.denominator);
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * Subtracts the specified Rational from this Rational and returns the result as
     * a new Rational object.
     * 
     * @param other the Rational to be subtracted
     * @return the difference between this Rational and the specified Rational
     */
    public Rational minus(Rational other) {
        Integer newNumerator = Integer.minus(Integer.multiply(this.numerator, other.denominator),
                Integer.multiply(other.numerator, this.denominator));
        Integer newDenominator = Integer.multiply(this.denominator, other.denominator);
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * Multiplies this Rational by the specified Rational and returns the result as
     * a new Rational object.
     * 
     * @param other the Rational to be multiplied
     * @return the product of this Rational and the specified Rational
     */
    public Rational multiply(Rational other) {
        Integer newNumerator = Integer.multiply(this.numerator, other.numerator);
        Integer newDenominator = Integer.multiply(this.denominator, other.denominator);
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * Divides this Rational by the specified Rational and returns the result as a
     * new Rational object.
     * 
     * @param other the Rational to be divided by
     * @return the quotient of this Rational divided by the specified Rational
     */
    public Rational divide(Rational other) {
        Integer newNumerator = Integer.multiply(this.numerator, other.denominator);
        Integer newDenominator = Integer.multiply(this.denominator, other.numerator);
        return new Rational(newNumerator, newDenominator);
    }

    /**
     * 
     * Checks if the current Rational object is equal to the specified Rational
     * object.
     * 
     * @param other The Rational object to compare.
     * @return true if the two Rational objects are equal, false otherwise.
     */
    public boolean equals(Rational other) {
        return Integer.equal(this.numerator, other.numerator)
                && Integer.equal(this.denominator, other.denominator);
    }

    /**
     * 
     * Checks if the current Rational object is not equal to the specified Rational
     * object.
     * 
     * @param other The Rational object to compare.
     * @return true if the two Rational objects are not equal, false otherwise.
     */
    public boolean notEqual(Rational other) {
        return !Integer.equal(this.numerator, other.numerator)
                || !Integer.equal(this.denominator, other.denominator);
    }

    /**
     * 
     * Checks if the current Rational object is less than the specified Rational
     * object.
     * 
     * @param other The Rational object to compare.
     * @return true if the current Rational object is less than the specified
     *         Rational object, false otherwise.
     */
    public boolean lessThan(Rational other) {
        return Integer.lessThan(Integer.multiply(this.numerator, other.denominator),
                Integer.multiply(other.numerator, this.denominator));
    }

    /**
     * 
     * Checks if the current Rational object is greater than the specified Rational
     * object.
     * 
     * @param other The Rational object to compare.
     * @return true if the current Rational object is greater than the specified
     *         Rational object, false otherwise.
     */
    public boolean greaterThan(Rational other) {
        return Integer.greaterThan(Integer.multiply(this.numerator, other.denominator),
                Integer.multiply(other.numerator, this.denominator));
    }

    /**
     * 
     * Checks if the current Rational object is less than or equal to the specified
     * Rational object.
     * 
     * @param other The Rational object to compare.
     * @return true if the current Rational object is less than or equal to the
     *         specified Rational object, false otherwise.
     */
    public boolean lessThanOrEqual(Rational other) {
        return Integer.lessThanOrEqual(Integer.multiply(this.numerator, other.denominator),
                Integer.multiply(other.numerator, this.denominator));
    }

    /**
     * 
     * Checks if the current Rational object is greater than or equal to the
     * specified Rational object.
     * 
     * @param other The Rational object to compare.
     * @return true if the current Rational object is greater than or equal to the
     *         specified Rational object, false otherwise.
     */
    public boolean greaterThanOrEqual(Rational other) {
        return Integer.greaterThanOrEqual(Integer.multiply(this.numerator, other.denominator),
                Integer.multiply(other.numerator, this.denominator));
    }

    /**
     * Returns a string representation of this Rational.
     * 
     * @return a string representation of this Rational
     * @throws IllegalArgumentException if the denominator is zero (division by
     *                                  zero)
     */
    public String toString() {
        if (Integer.equal(this.denominator, new Integer())) {
            throw new IllegalArgumentException("Cannot divide by 0");
        }
        StringBuilder sb = new StringBuilder();
        if (Integer.greaterThan(this.numerator, this.denominator)) {
            // Case for when the numerator is greater than the denominator
            simplify(this.numerator, this.denominator);
            Integer quotient = Integer.divide(this.numerator, this.denominator);
            Integer remainder = Integer.remainder(this.numerator, this.denominator);
            if (Integer.equal(remainder, new Integer())) {
                sb.append(quotient);
            } else {
                sb.append(quotient).append('.').append(remainder).append('/').append(this.denominator);
            }
        } else if (Integer.equal(this.numerator, this.denominator)) {
            // Case for when the numerator is equal to the denominator
            sb.append(new Integer("1"));
        } else if (Integer.equal(this.numerator, new Integer())) {
            // Case for when the numerator is 0
            sb.append(0);
        } else if (Integer.lessThan(this.numerator, new Integer())) {
            // Case for when the numerator is negative
            Integer positiveNumerator = new Integer(false, this.numerator.digits);
            simplify(positiveNumerator, this.denominator);
            Integer quotient = Integer.divide(positiveNumerator, this.denominator);
            Integer remainder = Integer.remainder(positiveNumerator, this.denominator);
            sb.append('-');
            if (Integer.equal(remainder, new Integer())) {
                sb.append(quotient);
            } else if (Integer.greaterThan(quotient, new Integer())) {
                sb.append(quotient).append('.').append(remainder).append('/').append(this.denominator);
            } else {
                sb.append(this.numerator).append('/').append(this.denominator);
            }
        } else {
            // Case for when the numerator is positive
            simplify(this.numerator, this.denominator);
            sb.append(this.numerator).append('/').append(this.denominator);
        }

        return sb.toString();
    }

    /**
     * Simplifies the numerator and denominator of this Rational by dividing them by
     * their greatest common divisor (GCD).
     * 
     * @param numerator   the numerator of the Rational
     * @param denominator the denominator of the Rational
     */
    public void simplify(Integer numerator, Integer denominator) {
        Integer gcd = Integer.gcd(numerator, denominator);
        this.numerator = Integer.divide(numerator, gcd);
        this.denominator = Integer.divide(denominator, gcd);
    }
}
