import java.util.Arrays;

public class Integer {
    boolean isNegative;
    int[] digits;

    /**
     * Constructs a new Integer object with a default value of 0.
     */
    public Integer() {
        this.isNegative = false;
        this.digits = new int[] { 0 };
    }

    /**
     * Constructs a new Integer object by copying the digits from another Integer
     * object.
     * 
     * @param i The Integer object to be copied.
     */
    public Integer(Integer i) {
        this.isNegative = i.isNegative;
        this.digits = i.digits;
    }

    /**
     * Constructs a new Integer object from a string representation.
     * 
     * @param s The string representation of the Integer.
     */
    public Integer(String s) {
        // Declare variables to store the length of the String and the starting index
        int length, startIndex;

        // Check if the String has a sign
        if (s.charAt(0) == '+' || s.charAt(0) == '-') {
            length = s.length() - 1;
            startIndex = 1;
        } else {
            length = s.length();
            startIndex = 0;
        }
        // Create an array of digits to store the Integer
        digits = new int[length];

        // Iterate through the string and populate the array of digits
        int j = 0;
        for (int i = startIndex; i < s.length(); i++) {
            digits[j++] = Character.digit(s.charAt(i), 10);
        }
        // Set the sign of the Integer
        isNegative = s.charAt(0) == '-';
    }

    /**
     * Constructs a new Integer object with the given array of digits.
     * 
     * @param digits The array of digits representing the Integer.
     */
    public Integer(boolean isNegative, int[] digits) {
        this.isNegative = isNegative;
        this.digits = digits;
    }

    /**
     * Adds two Integers and returns the result.
     * 
     * @param lhs The first Integer to be added.
     * @param rhs The second Integer to be added.
     * @return The sum of lhs and rhs as an Integer.
     */
    public static Integer plus(Integer lhs, Integer rhs) {
        // Determine the sign of the result
        boolean isNegative;

        // Check if the result is negative
        if (lhs.isNegative && !rhs.isNegative) {
            // Case for (-a) + b
            Integer lhsTemp = new Integer(lhs);
            lhsTemp.isNegative = false;
            return minus(rhs, lhsTemp);
        } else if (!lhs.isNegative && rhs.isNegative) { // Case for a + (-b)
            Integer rhsTemp = new Integer(rhs);
            rhsTemp.isNegative = false;
            return minus(lhs, rhsTemp);
        } else if (!lhs.isNegative && !rhs.isNegative) { // Case for a + b
            isNegative = false;
        } else { // Case for (-a) + (-b)
            isNegative = true;
        }
        // Determine the biggest and smallest length of the two Integers
        int biggestLength = Math.max(lhs.digits.length, rhs.digits.length);
        int smallestLength = Math.min(lhs.digits.length, rhs.digits.length);

        // Determine which Integer has more digits
        boolean lhsBiggest;
        if (lhs.digits.length >= rhs.digits.length) {
            lhsBiggest = true;
        } else {
            lhsBiggest = false;
        }
        int resultLength = biggestLength + 1; // Result array length
        int[] resultArray = new int[resultLength]; // Result array to store the sum of digits
        int lhsIndex = lhs.digits.length - 1; // Index for iterating through lhs digits
        int rhsIndex = rhs.digits.length - 1; // Index for iterating through rhs digits
        int carry = 0; // Carry value for addition

        // Iterate through the digits of the two Integers
        while (biggestLength > 0) {
            int sum;
            if (smallestLength > 0) {
                // Add digits from lhs and rhs along with the carry
                sum = lhs.digits[lhsIndex--] + rhs.digits[rhsIndex--] + carry;
            } else {
                // Add the digit from the larger Integer along with the carry
                if (lhsBiggest) {
                    sum = lhs.digits[lhsIndex--] + carry;
                } else {
                    sum = rhs.digits[rhsIndex--] + carry;
                }
            }
            // Determine the digit and update carry if necessary
            resultArray[resultLength - 1] = sum % 10;
            carry = sum / 10;

            // Check if the result array is fully populated and there is a carry remaining
            if (smallestLength == biggestLength && smallestLength == 1 && carry > 0
                    || biggestLength == 1 && carry > 0) {
                resultArray[0] = carry;
            }
            // Decrement counters
            smallestLength--;
            biggestLength--;
            resultLength--;
        }
        // Return a new Integer with leading zeros removed and return it
        return new Integer(isNegative, removeLeadingZeros(resultArray));
    }

    /**
     * Performs subtraction between two Integers and returns the result.
     * 
     * @param lhs The first Integer operand.
     * @param rhs The second Integer operand.
     * 
     * @return The result of subtracting the second operand from the first operand.
     */
    public static Integer minus(Integer lhs, Integer rhs) {
        if (!lhs.isNegative && rhs.isNegative) { // Case for a - (-b)
            Integer lhsTemp = new Integer(lhs);
            lhsTemp.isNegative = false;
            Integer rhsTemp = new Integer(rhs);
            rhsTemp.isNegative = false;
            return plus(lhsTemp, rhsTemp);
        } else if (lhs.isNegative && !rhs.isNegative) { // Case for (-a) - b
            Integer lhsTemp = new Integer(lhs);
            lhsTemp.isNegative = true;
            Integer rhsTemp = new Integer(rhs);
            rhsTemp.isNegative = true;
            return plus(lhsTemp, rhsTemp);
        } else if (lhs.isNegative && rhs.isNegative) { // Case for (-a) - (-b)
            Integer rhsTemp = new Integer(rhs);
            rhsTemp.isNegative = false;
            return plus(lhs, rhsTemp);
        } else {
            // Determine if the returned result is negative
            boolean isNegative;

            // Determine which Integer is larger
            int[] largerDigit;
            int[] smallerDigit;

            if (Integer.greaterThanOrEqual(lhs, rhs)) {
                largerDigit = Arrays.copyOf(lhs.digits, lhs.digits.length);
                smallerDigit = Arrays.copyOf(rhs.digits, rhs.digits.length);
                isNegative = false;
            } else {
                largerDigit = Arrays.copyOf(rhs.digits, rhs.digits.length);
                smallerDigit = Arrays.copyOf(lhs.digits, lhs.digits.length);
                isNegative = true;
            }
            // Determine the biggest and smallest length of the two Integers
            int biggestLength = largerDigit.length;
            int smallestLength = smallerDigit.length;
            int largerIndex = biggestLength - 1; // Index for iterating through larger array
            int smallerIndex = smallestLength - 1; // Index for iterating through smaller array

            // Create a new array to store the result
            int[] resultArray = new int[biggestLength];

            // Iterate through the digits of the two Integers
            int difference = 0;
            while (biggestLength > 0) {
                if (smallestLength > 0) {
                    if (largerDigit[largerIndex] - smallerDigit[smallerIndex] < 0) {
                        // Add 10 to the digit from the larger Integer
                        difference = largerDigit[largerIndex--] - smallerDigit[smallerIndex--] + 10;

                        // Find the next non-zero digit in the larger Integer
                        int tempIndex = largerIndex;
                        while (largerDigit[tempIndex] == 0) {
                            largerDigit[tempIndex] = 9;
                            tempIndex--;
                        }
                        // Subtract the digits from the larger Integer
                        largerDigit[tempIndex]--;
                    } else {
                        // Subtract the digits from the larger Integer
                        difference = largerDigit[largerIndex--] - smallerDigit[smallerIndex--];
                    }
                } else {
                    // Subtract the digit from the larger Integer
                    difference = largerDigit[largerIndex--];
                }
                // Store the difference in the result array
                resultArray[biggestLength - 1] = difference;

                // Decrement counters
                biggestLength--;
                smallestLength--;
            }
            // Return a new Integer with leading zeros removed
            return new Integer(isNegative, removeLeadingZeros(resultArray));
        }
    }

    /**
     * Multiplies two integers and returns the result as a new Integer object.
     *
     * @param lhs The first integer operand.
     * @param rhs The second integer operand.
     * @return The product of lhs and rhs as a new Integer object.
     */
    public static Integer multiply(Integer lhs, Integer rhs) {
        boolean isNegative = lhs.isNegative ^ rhs.isNegative; // Determine if the result is negative
        int lhsLength = lhs.digits.length;
        int rhsLength = rhs.digits.length;
        int[] resultArray = new int[lhsLength + rhsLength]; // Maximum possible length of the product

        // Iterate through the digits of the two Integers
        for (int i = lhsLength - 1; i >= 0; i--) {
            int carry = 0; // Carry for the current digit
            for (int j = rhsLength - 1; j >= 0; j--) {
                int product = lhs.digits[i] * rhs.digits[j] + carry + resultArray[i + j + 1]; // Calculate the product
                resultArray[i + j + 1] = product % 10; // Store the digit
                carry = product / 10; // Update the carry
            }
            resultArray[i] += carry; // Handle any remaining carry
        }
        // Return a new Integer with leading zeros removed
        return new Integer(isNegative, removeLeadingZeros(resultArray));
    }

    /**
     * Divides two Integer numbers.
     * 
     * @param lhs The dividend, represented as an Integer object.
     * @param rhs The divisor, represented as an Integer object.
     * @return The quotient of the division as an Integer object.
     */
    public static Integer divide(Integer lhs, Integer rhs) {
        boolean isNegative = lhs.isNegative ^ rhs.isNegative; // Determine if the result is negative
        if (isNegative == false && lessThan(lhs, rhs)) {
            return new Integer(); // Return zero if the dividend is less than the divisor
        }else if(equal(lhs, rhs)){
            return new Integer("1");
        }else {
            // Create copies of the dividend and divisor
            int[] dividend = Arrays.copyOf(lhs.digits, lhs.digits.length);
            int[] divisor = Arrays.copyOf(rhs.digits, rhs.digits.length);

            // Create an array to store the quotient
            int[] quotient = new int[lhs.digits.length];

            // Create new Integers using a copy of the dividend and divisor
            int dividendIndex = divisor.length - 1;
            Integer dividendTemp = new Integer(false, Arrays.copyOf(lhs.digits, dividendIndex + 1));
            Integer divisorTemp = new Integer(false, divisor);

            // Iterate through the digits of the dividend
            while (dividendIndex < dividend.length) {
                int multiple = 0;

                // Subtract divisor from dividend until dividend becomes less than divisor
                while (greaterThanOrEqual(minus(dividendTemp, divisorTemp), new Integer())) {
                    dividendTemp = minus(dividendTemp, divisorTemp);
                    multiple++;
                }
                // Add the next digit from the dividend to the dividendTemp
                if (dividendIndex < dividend.length - 1) {
                    int[] temporary;
                    if (equal(dividendTemp, new Integer())) {
                        temporary = new int[dividendTemp.digits.length];
                    } else {
                        temporary = new int[dividendTemp.digits.length + 1];
                    }
                    // Copy digits from dividendTemp to temporary array
                    for (int i = 0; i < dividendTemp.digits.length; i++) {
                        temporary[i] = dividendTemp.digits[i];
                    }
                    temporary[temporary.length - 1] = dividend[dividendIndex + 1]; // Add the next digit from the
                                                                                   // dividend
                    dividendTemp.digits = temporary; // Update the dividendTemp
                }
                quotient[dividendIndex++] = multiple; // Store the multiple
            }
            // Return a new Integer with leading zeros removed
            return new Integer(isNegative, removeLeadingZeros(quotient));
        }

    }

    /**
     * Calculates the remainder of dividing two Integer numbers.
     * 
     * @param lhs The dividend, represented as an Integer object.
     * @param rhs The divisor, represented as an Integer object.
     * @return The remainder of the division as an Integer object.
     */
    public static Integer remainder(Integer lhs, Integer rhs) {
        boolean isNegative = lhs.isNegative; // Determine if the result is negative
        if (isNegative == false && lessThan(lhs, rhs)) {
            return new Integer(lhs); // Return the dividend if it is less than the divisor
        } else {
            // Create copies of the dividend and divisor
            int[] dividend = Arrays.copyOf(lhs.digits, lhs.digits.length);
            int[] divisor = Arrays.copyOf(rhs.digits, rhs.digits.length);

            // Create new Integers using a copy of the dividend and divisor
            int dividendIndex = divisor.length - 1;
            Integer dividendTemp = new Integer(false, Arrays.copyOf(lhs.digits, dividendIndex + 1));
            Integer divisorTemp = new Integer(false, divisor);

            // Iterate through the digits of the dividend
            while (dividendIndex < dividend.length) {
                while (greaterThanOrEqual(minus(dividendTemp, divisorTemp), new Integer())) {
                    // Subtract divisor from dividend until dividend becomes less than divisor
                    dividendTemp = minus(dividendTemp, divisorTemp);
                }
                // Add the next digit from the dividend to the dividendTemp
                if (dividendIndex < dividend.length - 1) {
                    int[] temporary;
                    if (equal(dividendTemp, new Integer())) {
                        temporary = new int[dividendTemp.digits.length];
                    } else {
                        temporary = new int[dividendTemp.digits.length + 1];
                    }
                    for (int i = 0; i < dividendTemp.digits.length; i++) {
                        temporary[i] = dividendTemp.digits[i];
                    }
                    temporary[temporary.length - 1] = dividend[dividendIndex + 1]; // Add the next digit from the
                                                                                   // dividend
                    dividendTemp.digits = temporary; // Update the dividendTemp
                }
                dividendIndex++; // Increment the dividend index
            }
            return new Integer(isNegative, removeLeadingZeros(dividendTemp.digits));
        }
    }

    /**
     * 
     * Checks if the first Integer is less than the second Integer.
     * 
     * @param lhs The first Integer.
     * @param rhs The second Integer.
     * @return {@code true} if lhs is less than rhs, {@code false} otherwise.
     */
    public static boolean lessThan(Integer lhs, Integer rhs) {
        if (lhs.isNegative && !rhs.isNegative) { // If lhs is negative and rhs is positive
            return true;
        } else if (!lhs.isNegative && rhs.isNegative) { // If lhs is positive and rhs is negative
            return false;
        } else {
            if (lhs.digits.length < rhs.digits.length) { // If the lengths are different
                return true;
            } else if (lhs.digits.length > rhs.digits.length) { // If the lengths are different
                return false;
            } else {
                for (int i = 0; i < lhs.digits.length; i++) { // If any of the digits are different
                    if (lhs.digits[i] < rhs.digits[i]) {
                        return true;
                    } else if (lhs.digits[i] > rhs.digits[i])
                        return false;
                }
            }
            return false;
        }
    }

    /**
     * 
     * Checks if the first Integer is greater than the second Integer.
     * 
     * @param lhs The first Integer.
     * @param rhs The second Integer.
     * @return {@code true} if lhs is greater than rhs, {@code false} otherwise.
     */
    public static boolean greaterThan(Integer lhs, Integer rhs) {
        if (lhs.isNegative && !rhs.isNegative) { // Case for (-a) > b
            return false;
        } else if (!lhs.isNegative && rhs.isNegative) { // Case for a > (-b)
            return true;
        } else {
            if (lhs.digits.length > rhs.digits.length) { // Case for a > b
                return true;
            } else if (lhs.digits.length < rhs.digits.length) { // Case for a < b
                return false;
            } else {
                for (int i = 0; i < lhs.digits.length; i++) { // If any of the digits are different
                    if (lhs.digits[i] > rhs.digits[i]) {
                        return true;
                    } else if (lhs.digits[i] < rhs.digits[i]) {
                        return false;
                    }
                }
            }
            return false;
        }
    }

    /**
     * 
     * Checks if the first Integer is less than or equal to the second Integer.
     * 
     * @param lhs The first Integer.
     * @param rhs The second Integer.
     * @return {@code true} if lhs is less than or equal to rhs, {@code false}
     *         otherwise.
     */
    public static boolean lessThanOrEqual(Integer lhs, Integer rhs) {
        return lessThan(lhs, rhs) || equal(lhs, rhs);
    }

    /**
     * 
     * Checks if the first Integer is greater than or equal to the second Integer.
     * 
     * @param lhs The first Integer.
     * @param rhs The second Integer.
     * @return {@code true} if lhs is greater than or equal to rhs, {@code false}
     *         otherwise.
     */
    public static boolean greaterThanOrEqual(Integer lhs, Integer rhs) {
        return greaterThan(lhs, rhs) || equal(lhs, rhs);
    }

    /**
     * Checks if the two Integers are equal.
     * 
     * @param lhs The first Integer.
     * @param rhs The second Integer.
     * @return {@code true} if lhs is equal to rhs, {@code false} otherwise.
     */
    public static boolean equal(Integer lhs, Integer rhs) {
        if (lhs.isNegative != rhs.isNegative) { // If one is negative and the other is positive
            return false;
        }
        if (lhs.digits.length != rhs.digits.length) { // If the lengths are different
            return false;
        }
        for (int i = 0; i < lhs.digits.length; i++) { // If any of the digits are different
            if (lhs.digits[i] != rhs.digits[i]) {
                return false;
            }
        }
        return true;
    }

    /**
     * Checks if the two Integers are not equal.
     * 
     * @param lhs The first Integer.
     * @param rhs The second Integer.
     * @return {@code true} if lhs is not equal to rhs, {@code false} otherwise.
     */
    public static boolean notEqual(Integer lhs, Integer rhs) {
        if (lhs.isNegative != rhs.isNegative) { // If one is negative and the other is positive
            return true;
        }
        if (lhs.digits.length != rhs.digits.length) { // If the lengths are different
            return true;
        }
        for (int i = 0; i < lhs.digits.length; i++) { // If any of the digits are different
            if (lhs.digits[i] != rhs.digits[i]) {
                return true;
            }
        }
        return false;
    }

    /**
     * Calculates the greatest common divisor (GCD) of two Integer numbers.
     * 
     * @param a The first Integer number.
     * @param b The second Integer number.
     * @return The greatest common divisor as an Integer object.
     */
    public static Integer gcd(Integer a, Integer b) {
        int[] largerArray;
        int[] smallerArray;

        // Determine if a or b is the larger Integer
        if (Integer.greaterThan(a, b)) {
            largerArray = Arrays.copyOf(a.digits, a.digits.length);
            smallerArray = Arrays.copyOf(b.digits, b.digits.length);
        } else {
            largerArray = Arrays.copyOf(b.digits, b.digits.length);
            smallerArray = Arrays.copyOf(a.digits, a.digits.length);
        }
        Integer largerDigit = new Integer(false, largerArray);
        Integer smallerDigit = new Integer(false, smallerArray);

        // If a is equal to b then return b
        if (equal(a, b)) {
            return a;
        } else {
            // Find the greatest common divisor using Euclidean algorithm
            while (notEqual(smallerDigit, new Integer())) {
                Integer temp = new Integer(smallerDigit);
                smallerDigit = remainder(largerDigit, smallerDigit);
                largerDigit = temp;
            }
            return largerDigit; // Return the GCD
        }
    }

    /**
     * Removes leading zeros from the given integer array and returns a new integer
     * array.
     * 
     * @param digits The integer array to remove leading zeros from.
     * @return A new integer array with leading zeros removed.
     */
    public static int[] removeLeadingZeros(int[] arr) {
        // Find the first non-zero digit
        int i = 0;
        while (i < arr.length && arr[i] == 0) {
            i++;
        }
        // If all digits are zero, return an array with one zero
        if (i == arr.length) {
            return new int[] { 0 };
        }
        // Copy the remaining digits to a new array
        int[] result = new int[arr.length - i];
        for (int j = 0; j < result.length; j++) {
            result[j] = arr[i + j];
        }
        return result;
    }

    /**
     * Returns a string representation of the Integer.
     * 
     * @return A string representation of the Integer.
     */
    public String toString() {
        StringBuilder sb = new StringBuilder(digits.length);
        if (isNegative) {
            sb.append("-");
        }
        for (int i = 0; i < digits.length; i++) {
            sb.append(digits[i]);
        }
        return sb.toString();
    }
}
