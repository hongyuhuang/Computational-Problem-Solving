import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class FloatingPoint {

    public static void main(String[] args) {
        // Create the scanner
        Scanner scanner = new Scanner(System.in);

        String inputFilename;
        String outputFilename;
        String inputPrecision;
        String outputPrecision;

        // Prompt the user for the file path of the input file
        do {
            System.out.print("Please provide the name of the input .bin file: ");
            inputFilename = scanner.nextLine();
        } while (inputFilename.trim().isEmpty());

        // Prompt the user for the file path of the output file
        do {
            System.out.print("Please provide the name of the output .bin file: ");
            outputFilename = scanner.nextLine();
        } while (outputFilename.trim().isEmpty());

        // Prompt the user for the precision of the input file
        do {
            System.out.print("Please enter the precision (single or double) for input file: ");
            inputPrecision = scanner.nextLine();
        } while (!inputPrecision.equalsIgnoreCase("single") &&
                !inputPrecision.equalsIgnoreCase("double"));

        // Prompt the user for the precision of the output file
        do {
            System.out.print("Please enter the (single or double) for output file: ");
            outputPrecision = scanner.nextLine();
        } while (!outputPrecision.equalsIgnoreCase("single") &&
                !outputPrecision.equalsIgnoreCase("double"));

        // Close the scanner
        scanner.close();

        // Read from the input file
        List<String> binaryList = readBinaryFile(inputFilename + ".bin", inputPrecision);

        // Convert from IBM hexadecimal floating-point to IEEE standard format
        List<String> IEEEStrings = IBMToIEEE(binaryList, outputPrecision);

        // Write to the output file
        writeToBinaryFile(outputFilename + ".bin", IEEEStrings);
    }

    /**
     * Reads binary data from a file and returns a list of binary strings.
     *
     * @param filePath  The path to the input file.
     * @param precision The precision of the binary data ("single" for 4 bytes or
     *                  "double" for 8 bytes).
     * @return A list of binary strings read from the file.
     */
    private static List<String> readBinaryFile(String filePath, String precision) {
        // List to store the binary strings
        List<String> binaryStrings = new ArrayList<>();

        // Read the file byte by byte
        try (DataInputStream dis = new DataInputStream(new FileInputStream(filePath))) {
            int bytesToRead = precision.equals("single") ? 4 : 8;
            byte[] buffer = new byte[bytesToRead];
            while (dis.read(buffer) != -1) {
                StringBuilder binaryBuilder = new StringBuilder();
                for (int i = 0; i < bytesToRead; i++) {
                    binaryBuilder.append(decimalByteToBinary(buffer[i]));
                }
                binaryStrings.add(binaryBuilder.toString());
            }
            System.out.println("Successfully read data from " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        // Return the list of binary strings
        return binaryStrings;
    }

    /**
     * Writes binary data to a file based on the provided list of binary strings.
     *
     * @param filePath      The path to the output file.
     * @param binaryStrings The list of binary strings to be written to the file.
     */
    public static void writeToBinaryFile(String filePath, List<String> binaryStrings) {
        // Create a new file or append to an existing file
        File file = new File(filePath);

        // Delete the existing file if it exists
        if (file.exists()) {
            file.delete();
        }

        // Write the binary strings to the file
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file, true))) {
            for (String binaryString : binaryStrings) {
                byte[] data = binaryStringToByteArray(binaryString);
                bos.write(data);
            }
            System.out.println("Successfully wrote data to " + filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Converts binary strings in IBM format to IEEE 754 format based on the
     * provided precision.
     *
     * @param binaryStrings The list of binary strings in IBM format.
     * @param precision     The precision of the output format ("single" or
     *                      "double").
     * @return A list of binary strings in IEEE 754 format.
     */
    public static List<String> IBMToIEEE(List<String> binaryStrings, String precision) {
        // List to store the IEEE 754 binary strings
        List<String> IEEEStrings = new ArrayList<>();

        // Convert each binary string from IBM to IEEE 754
        for (String binaryString : binaryStrings) {
            String decimalValue = binaryIBMToDecimal(binaryString);
            System.out.println("Decimal value: " + decimalValue);
            String IEEEBinaryString;
            if (precision.equals("single")) {
                IEEEBinaryString = decimalToIEEE754(Float.parseFloat(decimalValue));
            } else {
                IEEEBinaryString = decimalToIEEE754(Double.parseDouble(decimalValue));
            }
            IEEEStrings.add(IEEEBinaryString);
        }
        // Return the list of IEEE 754 binary strings
        return IEEEStrings;
    }

    /**
     * Converts a decimal byte value to its binary representation as a string.
     *
     * @param decimalByte the decimal byte value to be converted
     * @return the binary representation of the decimal byte value as a string
     */
    private static String decimalByteToBinary(byte decimalByte) {
        // StringBuilder to store the binary string
        StringBuilder binaryBuilder = new StringBuilder();

        // Convert the decimal byte to binary
        for (int i = 7; i >= 0; i--) {
            int bit = (decimalByte >> i) & 1;
            binaryBuilder.append(bit);
        }
        // Return the binary string
        return binaryBuilder.toString();
    }

    /**
     * Converts a binary string representing an integer to its decimal equivalent.
     *
     * @param binary the binary string to be converted
     * @return the decimal equivalent of the binary integer
     */
    private static int binaryIntegerToDecimal(String binary) {
        // Declare and initialize the decimal and power
        int decimal = 0;
        int power = 0;

        // Convert the binary integer to decimal
        for (int i = binary.length() - 1; i >= 0; i--) {
            int bit = Character.getNumericValue(binary.charAt(i));
            decimal += bit * Math.pow(2, power);
            power++;
        }
        // Return the decimal equivalent
        return decimal;
    }

    /**
     * Converts a binary string representing a fraction to its decimal equivalent.
     *
     * @param binary the binary string to be converted
     * @return the decimal equivalent of the binary fraction
     */
    public static double binaryFractionToDecimal(String binary) {
        // Declare and initialize the decimal and power
        double decimal = 0.0;
        int power = -1;

        // Convert the binary fraction to decimal
        for (int i = 0; i < binary.length(); i++) {
            int bit = Character.getNumericValue(binary.charAt(i));
            decimal += bit * Math.pow(2, power);
            power--;
        }
        // Return the decimal equivalent
        return decimal;
    }

    /**
     * Converts a binary string representing an IBM floating-point number to its
     * decimal equivalent.
     *
     * @param binary the binary string to be converted
     * @return the decimal equivalent of the binary IBM floating-point number
     */
    private static String binaryIBMToDecimal(String binary) {
        // Separate the sign, exponent, and mantissa bits
        System.out.println("Binary: " + binary);
        String signBit = binary.substring(0, 1);
        String exponentBits = binary.substring(1, 8);
        String significandBits = binary.substring(8); // Works for single and double precision

        // Convert the exponent and mantissa bits to decimal
        int exponent = binaryIntegerToDecimal(exponentBits);
        double significand = binaryFractionToDecimal(significandBits);
        System.out.println("Sign: " + signBit);
        System.out.println("Exponent: " + exponent);
        System.out.println("Significand: " + significand);
        

        // Calculate the decimal value
        int bias = 64; // Bias value for IBM floating-point format
        double decimal = Math.pow(-1, Integer.parseInt(signBit)) * significand * Math.pow(16, exponent - bias);

        // Return the decimal value as a string
        return Double.toString(decimal);
    }

    /**
     * Converts a decimal number to its IEEE 754 floating-point representation in
     * single precision (32-bit).
     *
     * @param number the decimal number to be converted
     * @return the IEEE 754 representation of the decimal number
     */
    private static String decimalToIEEE754(float number) {
        // Get the raw bits of the number
        int bits = Float.floatToRawIntBits(number);

        // Convert the bits to a binary string
        StringBuilder binaryString = new StringBuilder(Integer.toBinaryString(bits));

        // Pad with leading zeros if necessary
        while (binaryString.length() < 32) {
            binaryString.insert(0, "0");
        }
        StringBuilder ieee754String = new StringBuilder();

        // Extract sign bit
        ieee754String.append(binaryString.charAt(0));

        // Extract exponent bits
        String exponentBits = binaryString.substring(1, 9);
        ieee754String.append(exponentBits);

        // Extract fraction bits
        String fractionBits = binaryString.substring(9);
        ieee754String.append(fractionBits);

        // Return the IEEE 754 string
        return ieee754String.toString();
    }

    /**
     * Converts a decimal number to its IEEE 754 floating-point representation in
     * double precision (64-bit).
     *
     * @param number the decimal number to be converted
     * @return the IEEE 754 representation of the decimal number
     */
    private static String decimalToIEEE754(double number) {
        // Convert the double to a long
        long bits = Double.doubleToRawLongBits(number);

        // Convert the long to a binary string
        StringBuilder binaryString = new StringBuilder(Long.toBinaryString(bits));

        // Pad with leading zeros if necessary
        while (binaryString.length() < 64) {
            binaryString.insert(0, "0");
        }
        // Declare and initialize the IEEE 754 string
        StringBuilder ieee754String = new StringBuilder();

        // Extract sign bit
        ieee754String.append(binaryString.charAt(0));

        // Extract exponent bits
        String exponentBits = binaryString.substring(1, 12);
        ieee754String.append(exponentBits);

        // Extract fraction bits
        String fractionBits = binaryString.substring(12);
        ieee754String.append(fractionBits);

        // Return the IEEE 754 string
        return ieee754String.toString();
    }

    /**
     * Converts a binary string to a byte array representation.
     *
     * @param binaryString the binary string to be converted
     * @return the byte array representation of the binary string
     */
    private static byte[] binaryStringToByteArray(String binaryString) {
        // Determine the length of the byte array
        int length = binaryString.length() / 8;

        // Declare and initialize the byte array
        byte[] byteArray = new byte[length];

        // Convert each byte in the binary string to a byte value
        for (int i = 0; i < length; i++) {
            String byteString = binaryString.substring(i * 8, (i + 1) * 8);
            byte byteValue = (byte) Integer.parseInt(byteString, 2);
            byteArray[i] = byteValue;
        }
        // Return the byte array
        return byteArray;
    }
}
