import java.awt.Desktop;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CoordinateApp {
    /**
     * Main method
     * 
     * @param args
     */

    static String line;
    static String filePath = "validFeatures.json";
    static List<String> features = new ArrayList<String>();

    public static void main(String[] args) {
        // Create a new Scanner object
        Scanner scanner = new Scanner(System.in);

        // Read in a sequence of lines from stdin
        while (scanner.hasNext()) {
            //Create a new Coordinate object using each line
            Coordinate coordinate = new Coordinate(scanner.nextLine());
                    
            // Add the coordinate to the list of features
            if (coordinate.getLabel() != null) {
                features.add(coordinateToFeature(coordinate.getLongitude(), coordinate.getLatitude(), coordinate.getLabel()));
            } else {
                features.add(coordinateToFeature(coordinate.getLongitude(), coordinate.getLatitude()));
            }

        }
        // Close the Scanner
        scanner.close();

        // Add the features to a valid GeoJSON file
        addFeatures(features);

        // Visualise the saved GeoJSON file using geojson.io
        visualiseMap();
    }

    /**
     * Converts a pair of longitude and latitude coordinates and label into a
     * GeoJSON Point feature.
     *
     * @param longitude The longitude of the point.
     * @param latitude  The latitude of the point.
     * @param label     An optional label for the point.
     * @return A GeoJSON string representing a Point feature with the given
     *         coordinates and label.
     */
    public static String coordinateToFeature(double longitude, double latitude, String... label) {
        // Create a new StringBuilder object
        StringBuilder sb = new StringBuilder();

        // Append the longitude and latitude
        sb.append("{\"type\":\"Feature\",\"geometry\":{\"type\":\"Point\",\"coordinates\":[").append(longitude)
                .append(",").append(latitude).append("]},\"properties\":{");

        // Append the label if it exists
        if (label.length > 0) {
            sb.append("\"name\":\"").append(label[0]).append("\"");
        }

        // Append the closing curly braces
        sb.append("}}");
        return sb.toString();
    }

    /**
     * Adds a list of features to a JSON file named "validFeatures.json" located in
     * the file path specified by the filePath variable.
     * 
     * @param features The list of features to be added to the JSON file.
     * 
     * @throws IOException If an I/O error occurs while writing to the JSON file.
     */
    public static void addFeatures(List<String> features) {
        try {
            // Creates a new JSON file named "validFeatures.json" located in the file path
            // specified by the filePath variable.
            File file = new File(filePath);

            // Create a FileWriter object with the File object
            FileWriter fw = new FileWriter(file);

            // The JSON file will have the following initial content:
            // {
            // "features": [
            // "type": "FeatureCollection",
            // ]
            // }
            fw.write("{\"type\":\"FeatureCollection\",\"features\":[");

            // Close the FileWriter
            fw.close();

            // Create a new FileWriter object that appends to the file
            fw = new FileWriter(file, true);

            // Wrap the FileWriter object with a BufferedWriter for better performance
            BufferedWriter writer = new BufferedWriter(fw);

            // Iterate through all the features and add it to the FeaturesCollection
            int numFeatures = features.size();
            for (int i = 0; i < numFeatures; i++) {
                String feature = features.get(i);
                if (numFeatures == 1 || i == numFeatures - 1) {
                    writer.write(feature);
                } else {
                    writer.write(feature + ",");
                }
            }
            writer.write("]}");

            // Close the BufferedWriter
            writer.close();
        } catch (IOException e) {
            System.err.println("I/O error occured while writing to the JSON file");
        }
    }

    /**
     * Reads the contents of a JSON file named "validFeatures.json" located in the
     * "data" folder,
     * encodes the contents as a URL query parameter, constructs a URL with the
     * encoded parameter,
     * and launches the default web browser to open the URL with geojson.io, a
     * web-based tool for
     * visualizing and analyzing geospatial data.
     *
     * @throws IOException                   if an I/O error occurs while reading
     *                                       the JSON file
     * @throws URISyntaxException            if the URL constructed from the encoded
     *                                       JSON data is invalid
     * @throws UnsupportedOperationException if the default web browser cannot be
     *                                       launched
     */
    public static void visualiseMap() {
        try {
            // Read the contents of the JSON file into a String
            String json = new String(Files.readAllBytes(Paths.get(filePath)), StandardCharsets.UTF_8);

            // Encode the JSON string as a URL query parameter replacing any "+" symbols
            // with "%20" to display
            String encoded = URLEncoder.encode(json, "UTF-8").replace("+", "%20");

            // Construct the URL with the encoded query parameter
            String url = "https://geojson.io/#data=data:application/json," + encoded;

            // Launch the default browser to the URL of geojson.io with the query parameters
            Desktop.getDesktop().browse(new URI(url));
        } catch (IOException e) {
            System.err.println("I/O error occured while reading from the JSON file");
        } catch (URISyntaxException e) {
            System.err.println("Invalid URL constructed from encoded JSON data");
        } catch (UnsupportedOperationException e) {
            System.err.println("Default web browser can't be launched");
        }
    }
}
