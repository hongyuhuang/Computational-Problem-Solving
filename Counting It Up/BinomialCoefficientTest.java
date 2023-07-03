import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.math.BigInteger;

import org.junit.jupiter.api.Test;

public class BinomialCoefficientTest {

    @Test
    public void test() {
        // Test for implementation using BigLong versus BigInteger
        compareImplementations(100, 50);
        compareImplementations(200, 100);
        compareImplementations(300, 150);
        compareImplementations(400, 200);
        compareImplementations(410, 205);
        compareImplementations(420, 210);
        compareImplementations(423, 212);

        // Test for "n must be positive" exception
        binomialCoefficient(-1, 0, "n must be positive");

        // Test for "k must be larger or equal to zero" exception
        binomialCoefficient(5, -1, "k must be larger or equal to zero");

        // Test for "k must be less than or equal to n" exception
        binomialCoefficient(5, 6, "k must be less than or equal to n");

        // Test k = 0 and k = n
        binomialCoefficient(5, 0, "1\n");
        binomialCoefficient(5, 5, "1\n");

        // Test k = 1 and k = n - 1
        binomialCoefficient(5, 1, "5\n");
        binomialCoefficient(5, 4, "5\n");

        // Test arbitrary values
        binomialCoefficient(6, 6, "1\n");
        binomialCoefficient(6, 1, "6\n");
        binomialCoefficient(6, 2, "15\n");
        binomialCoefficient(6, 3, "20\n");
        binomialCoefficient(7, 7, "1\n");
        binomialCoefficient(7, 1, "7\n");
        binomialCoefficient(7, 2, "21\n");
        binomialCoefficient(7, 3, "35\n");

        // Test large values
        binomialCoefficient(100, 50, "100891344545564193334812497256\n");
        binomialCoefficient(200, 100, "90548514656103281165404177077484163874504589675413336841320\n");
        binomialCoefficient(300, 150,
                "93759702772827452793193754439064084879232655700081358920472352712975170021839591675861424\n");
        binomialCoefficient(400, 200,
                "102952500135414432972975880320401986757210925381077648234849059575923332372651958598336595518976492951564048597506774120\n");
    }

    /**
     * This method calculates the binomial coefficient for given values of n and k.
     * 
     * @param n
     * @param k
     * @param expectedOutput
     * @param expectedErrorMessage
     */
    public void binomialCoefficient(long n, long k, String expectedOutput) {
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        try {
            BinomialCoefficient.binomialCoefficient(n, k);
            assertEquals(expectedOutput, outContent.toString());
        } catch (IllegalArgumentException e) {
            assertEquals(expectedOutput, e.getMessage());
        }
    }

    /**
     * This method compares the binomial coefficient for given values of n and k
     * between using a custom BigLong class and using BigInteger.
     * 
     * @param n
     * @param k
     */
    public void compareImplementations(long n, long k) {
        ByteArrayOutputStream outContent1 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent1));
        try {
            BinomialCoefficient.binomialCoefficient(n, k);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }

        ByteArrayOutputStream outContent2 = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent2));
        try {
            binomialCoefficientUsingBigInteger(n, k);
        } catch (Exception e) {
            fail("Exception thrown: " + e.getMessage());
        }
        assertEquals(outContent1.toString(), outContent2.toString());
    }

    /**
     * This method calculates the binomial coefficient for given values of n and k.
     * The method uses a loop to calculate the binomial coefficient using the
     * quotient rule.
     *
     * @param n the total number of items
     * @param k the number of items to choose
     * @return the binomial coefficient of n and k
     * @throws IllegalArgumentException if n or k is negative or if k is greater
     *                                  than n
     */
    @Test
    public static void binomialCoefficientUsingBigInteger(long n, long k) {
        // Check if n is positive
        if (n < 1) {
            throw new IllegalArgumentException("n must be positive");
        } else if (k < 0) { // Check if k is non-negative
            throw new IllegalArgumentException("k must be larger or equal to zero");
        } else if (k > n) { // Check if k is less than or equal to n
            throw new IllegalArgumentException("k must be less than or equal to n");
        } else if (k == 0 || k == n) { // Check if k is 0 or n
            System.out.println(1);
        } else if (k == 1 || k == n - 1) { // Check if k is 1 or n - 1
            System.out.println(n);
        } else {
            // Create a new BigLong object with value 1
            BigInteger result = new BigInteger("1");

            // Calculate the binomial coefficient using the quotient rule
            for (int i = 1; i <= Long.min(k, n - k); i++) {
                result = result.multiply(new BigInteger(String.valueOf(n - i + 1)));
                result = result.divide(new BigInteger(String.valueOf(i)));
            }
            // Print the result to standard output
            System.out.println(result);
        }
    }
}