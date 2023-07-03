import java.util.Scanner;

/**
 * This program calculates the binomial coefficient for given input values of n
 * and k.
 * The program reads input from standard input and prints output to standard
 * output.
 * If input is not in the correct format, an error message is printed to
 * standard error.
 */
public class BinomialCoefficient {
    /**
     * The main method reads input from standard input and prints output to standard
     * output.
     * If input is not in the correct format, an error message is printed to
     * standard error.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        // Create new Scanner object to read input from standard input
        Scanner scanner = new Scanner(System.in);
        try {
            // Regex pattern for two positive integers separated by a single white space
            String regex = "\\d+\\s\\d+";

            // Read input until end of input is reached
            while (scanner.hasNextLine()) {
                String input = scanner.nextLine();
                if (input.matches(regex)) {
                    // Parse input values of n and k
                    long n = Long.parseLong(input.split("\\s+")[0]);
                    long k = Long.parseLong(input.split("\\s+")[1]);

                    // Calculate binomial coefficient and print result to standard output
                    pascalsTriangle(n, k);
                } else {
                    // Print error message to standard error if input is not in the correct format
                    System.err.println(
                            "Input doesn't match two positive 64-bit integers separated by a single whitespace character");
                }
            }
        } finally {
            // Close the Scanner object to free resources
            scanner.close();
        }
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
    public static void binomialCoefficient(long n, long k) {
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
            BigLong result = new BigLong("1");

            // Calculate the binomial coefficient using the multiplicative formula
            for (int i = 1; i <= Long.min(k, n - k); i++) {
                result = result.multiply(n - i + 1);
                result = result.divide(i);
            }
            // Print the result to standard output
            System.out.println(result);
        }
    }

    /**
     * This method calculates the binomial coefficient for given values of n and k.
     * The method uses a loop to calculate the binomial coefficient using pascals
     * triangle.
     *
     * @param n the total number of items
     * @param k the number of items to choose
     * @return the binomial coefficient of n and k
     * @throws IllegalArgumentException if n or k is negative or if k is greater
     *                                  than n
     */
    private static void pascalsTriangle(long n, long k) {
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
        } else if (k == 2 || k == n - 2){
            System.out.println(n / 2 * (n - 1)); // Check if k is 2
        } else {
            // Initialize the first row of Pascal's triangle
            long[] row = new long[(int) (k + 1)];
            row[0] = 1;

            // Calculate each row of Pascal's triangle
            for (int i = 1; i <= n; i++) {
                long[] newRow = new long[(int) (k + 1)];
                newRow[0] = 1;
                for (int j = 1; j <= k; j++) {
                    long sum = row[j] + row[j - 1];
                    newRow[j] = sum;
                }
                row = newRow;
            }
            System.out.println(row[(int) k]);
        }
    }
}
