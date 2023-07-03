import java.util.Arrays;

/**
 * The BigLong class represents a large integer using an array of longs for each
 * digit.
 */
public class BigLong {
    private long[] digits;

    /**
     * Constructs a BigLong using an array of longs for each digit.
     * 
     * @param digits the array of longs for each digit
     */
    public BigLong(long[] digits) {
        this.digits = digits;
    }

    /**
     * Constructs a BigLong using a string representation of the integer.
     * 
     * @param s the string representation of the integer
     */
    public BigLong(String s) {
        digits = new long[s.length()];
        for (int i = 0; i < s.length(); i++) {
            digits[i] = Character.digit(s.charAt(i), 10);
        }
    }

    /**
     * Multiplies this BigLong by a long integer.
     * 
     * @param multiplier the long integer to multiply by
     * @return the product of this BigLong and the long integer
     */
    public BigLong multiply(long multiplier) {
        return new BigLong(multiply(this.digits, multiplier));
    }

    /**
     * Divides this BigLong by a long integer.
     * 
     * @param divisor the long integer to divide by
     * @return the quotient of this BigLong divided by the long integer
     */
    public BigLong divide(long divisor) {
        return new BigLong(divide(this.digits, divisor));
    }

    /**
     * Multiplies a long array representing a large integer by a long integer.
     * 
     * @param num        the long array representing a large integer
     * @param multiplier the long integer to multiply by
     * 
     * @return the product of the long array and the long integer
     */
    public long[] multiply(long[] num, long multiplier) {
        int n = num.length;
        long[] result = new long[n + 1];
        long carry = 0;

        // Multiply each digit of the num array by the multiplier
        for (int i = n - 1; i >= 0; i--) {
            long product = num[i] * multiplier + carry; // Multiply the current digit of the num array by the multiplier
            result[i + 1] = product % 10; // Store the ones digit of the product in the result array
            carry = product / 10; // Store the tens digit of the product as the carry
        }
        // Store the carry in the first element of the result array
        result[0] = carry;

        // Remove any leading zeroes from the result array before returning it
        return stripLeadingZeros(result);
    }

    /**
     * Divides a long array representing a large integer by a long integer.
     * 
     * @param dividend the long array representing a large integer to divide
     * @param divisor  the long integer to divide by
     * 
     * @return the quotient of the long array divided by the long integer
     */
    public long[] divide(long[] dividend, long divisor) {
        long[] quotient = new long[dividend.length];
        long carry = 0;

        // Divide the dividend by the divisor
        for (int i = 0; i < dividend.length; i++) {
            long num = carry * 10 + dividend[i]; // Add the carry to the current digit of the dividend
            quotient[i] = num / divisor; // Store the quotient in the quotient array
            carry = num % divisor; // Store the remainder as the carry
        }
        // Remove leading zeroes
        return stripLeadingZeros(quotient);
    }

    /**
     * Removes leading zeroes from a long array representing a large integer.
     * Find the array index of the first non-zero element and return a copy of 
     * 
     * @param num the long array representing a large integer to remove leading
     *            zeroes from
     * @return the long array with leading zeroes removed
     */
    public long[] stripLeadingZeros(long[] num) {
        int i = 0;
        while (i < num.length - 1 && num[i] == 0) {
            i++;
        }
        return Arrays.copyOfRange(num, i, num.length);
    }

    /**
     * Returns the string representation of this BigLong instance.
     * 
     * @return a string representation of this BigLong instance
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(digits.length);
        for (int i = 0; i < digits.length; i++) {
            sb.append(digits[i]);
        }
        return sb.toString();
    }
}
