import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Coordinate {

    private final double SMALLEST_LATITUDE = -90;
    private final double LARGEST_LATITUDE = 90;
    private final double SMALLEST_LONGITUDE = -180;
    private final double LARGEST_LONGITUDE = 180;

    private double longitude = 0.0d;
    private double latitude = 0.0d;
    private String label = "";

    // Regex expressions for standard form
    private final String STN_LAT_REGEX = "([-+]?\\d{1,2}(?:\\.\\d{1,6})?)";
    private final String STN_LONG_REGEX = "([-+]?\\d{1,3}(?:\\.\\d{1,6})?)";

    // Regex expressions for non-negative standard form
    private final String NN_LAT_REGEX = "(\\d{1,2}(?:\\.\\d{1,6})?)\s*([NS])";
    private final String NN_LONG_REGEX = "(\\d{1,3}(?:\\.\\d{1,6})?)\s*([EW])";

    // Regex expressions for degree, minutes and seconds form
    private final String DMS_LAT_REGEX = "([-+]?\\d{1,2})°\s*(\\d{1,2})[′']\s*(\\d{1,2}(?:\\.\\d{1,6})?)[″\"]\s*([NS])";
    private final String DMS_LONG_REGEX = "([-+]?\\d{1,3})°\s*(\\d{1,2})[′']\s*(\\d{1,2}(?:\\.\\d{1,6})?)[″\"]\s*([EW])";

    // Regex expressions for degree and minutes form
    private final String DM_LAT_REGEX = "([-+]?\\d{1,2})°\s*(\\d{1,2}(?:\\.\\d{1,6})?)[′']\s*([NS])";
    private final String DM_LONG_REGEX = "([-+]?\\d{1,3})°\s*(\\d{1,2}(?:\\.\\d{1,6})?)[′']\s*([EW])";

    // Regex expression for the optional label
    private final String LABEL_REGEX = "(?:\s(.*))?";

    // Regex pattern for non-negative longitude and latitude
    private final String NN_LAT_NN_LONG_REGEX = NN_LAT_REGEX + ",?\s*" + NN_LONG_REGEX + "\s*"
            + LABEL_REGEX;
    private final String NN_LONG_NN_LAT_REGEX = NN_LONG_REGEX + ",?\s*" + NN_LAT_REGEX + "\s*"
            + LABEL_REGEX;

    // Regex pattern for standard form latitude annd non-negative latitude
    private final String NN_LAT_STN_LONG_REGEX = NN_LAT_REGEX + ",?\s*" + STN_LONG_REGEX + "\s*"
            + LABEL_REGEX;
    private final String STN_LONG_NN_LAT_REGEX = STN_LONG_REGEX + ",?\s*" + NN_LAT_REGEX + "\s*"
            + LABEL_REGEX;

    // Regex pattern for non-negative longitude and standard form latitude
    private final String NN_LONG_STN_LAT_REGEX = NN_LONG_REGEX + ",?\s*" + STN_LAT_REGEX + "\s*"
            + LABEL_REGEX;
    private final String STN_LAT_NN_LONG_REGEX = STN_LAT_REGEX + ",?\s*" + NN_LONG_REGEX + "\s*"
            + LABEL_REGEX;

    // Regex patterns for standard form longitude and latitude
    private final String STN_LAT_STN_LONG_REGEX = STN_LAT_REGEX + ",?\s*" + STN_LONG_REGEX + "\s*" + LABEL_REGEX;

    // Regex patterns for degrees, minutes, and seconds form longitude and latitude
    private final String DMS_LAT_DMS_LONG_REGEX = DMS_LAT_REGEX + ",?\s*" + DMS_LONG_REGEX + "\s*" + LABEL_REGEX;
    private final String DMS_LONG_DMS_LAT_REGEX = DMS_LONG_REGEX + ",?\s*" + DMS_LAT_REGEX + "\s*" + LABEL_REGEX;

    // Regex patterns for degrees and minutes form longitude and latitude
    private final String DM_LAT_DM_LONG_REGEX = DM_LAT_REGEX + ",?\s*" + DM_LONG_REGEX + "\s*" + LABEL_REGEX;
    private final String DM_LONG_DM_LAT_REGEX = DM_LONG_REGEX + ",?\s*" + DM_LAT_REGEX + "\s*" + LABEL_REGEX;

    // Regex patterns for d m s form longitude and latitude (lowercase)
    private final String LOWERCASE_DMS_LAT_DMS_LONG_REGEX = "(\\d{1,2})\\s*d\\s*(\\d{1,2})\\s*m\\s*(\\d{1,2})\\s*s\\s*([NS]),\\s*(\\d{1,3})\\s*d\\s*(\\d{1,2})\\s*m\\s*(\\d{1,2})\\s*s\\s*([EW])\s*"
            + LABEL_REGEX;

    // Regex pattern for degrees, minutes, and seconds form longitude and
    // non-negative degrees, minutes, and seconds form latitude
    private final String DMS_LONG_NN_LAT_REGEX = "(\\d{1,3})°(\\d{1,2})\\s(\\d{2}(?:\\.\\d+)?)\"[NS]\\s(\\d{1,3})°(\\d{1,2})'(\\d{2}(?:\\.\\d+)?)\"\s*"
            + LABEL_REGEX;

    // Array of all the regex patterns
    private final Pattern[] PATTERNS = { Pattern.compile(LOWERCASE_DMS_LAT_DMS_LONG_REGEX),
            Pattern.compile(NN_LAT_NN_LONG_REGEX), Pattern.compile(NN_LONG_NN_LAT_REGEX),
            Pattern.compile(NN_LAT_STN_LONG_REGEX), Pattern.compile(STN_LONG_NN_LAT_REGEX),
            Pattern.compile(NN_LONG_STN_LAT_REGEX), Pattern.compile(STN_LAT_NN_LONG_REGEX),
            Pattern.compile(STN_LAT_STN_LONG_REGEX), Pattern.compile(DMS_LAT_DMS_LONG_REGEX),
            Pattern.compile(DMS_LONG_DMS_LAT_REGEX), Pattern.compile(DM_LAT_DM_LONG_REGEX),
            Pattern.compile(DM_LONG_DM_LAT_REGEX), Pattern.compile(DMS_LONG_NN_LAT_REGEX) };

    /**
     * 
     * Constructs a Coordinate object from a given string input.
     * 
     * @param input the string input to be parsed into a Coordinate object
     * @throws Exception if the input string is not a valid format for a Coordinate
     *                   object
     */
    public Coordinate(String input) {
        boolean patternMatched = false;
        Matcher matcher = null;
        int patternIndex;
        for (patternIndex = 0; patternIndex < PATTERNS.length; patternIndex++) {
            matcher = PATTERNS[patternIndex].matcher(input.trim());
            if (matcher.matches()) {
                patternMatched = true;
                break;
            }
        }
        if (patternMatched) {
            switch (matcher.pattern().pattern()) {
                case LOWERCASE_DMS_LAT_DMS_LONG_REGEX:
                    parseDMSLatDMSLongLabel(matcher, 1, 5, 9);
                    break;
                case NN_LAT_NN_LONG_REGEX:
                    parseNNLatNNLongLabel(matcher, 1, 3, 5);
                    break;
                case NN_LONG_NN_LAT_REGEX:
                    parseNNLatNNLongLabel(matcher, 3, 1, 5);
                    break;
                case NN_LAT_STN_LONG_REGEX:
                    parseNNLatLongLabel(matcher, 1, 3, 4);
                    break;
                case STN_LONG_NN_LAT_REGEX:
                    parseNNLatLongLabel(matcher, 2, 1, 4);
                    break;
                case NN_LONG_STN_LAT_REGEX:
                    parseLatNNLongLabel(matcher, 3, 1, 4);
                    break;
                case STN_LAT_NN_LONG_REGEX:
                    parseLatNNLongLabel(matcher, 1, 2, 4);
                    break;
                case STN_LAT_STN_LONG_REGEX:
                    parseLatLongLabel(matcher, 1, 2, 3);
                    break;
                case DMS_LAT_DMS_LONG_REGEX:
                    parseDMSLatDMSLongLabel(matcher, 1, 5, 9);
                    break;
                case DMS_LONG_DMS_LAT_REGEX:
                    parseDMSLatDMSLongLabel(matcher, 5, 1, 9);
                    break;
                case DM_LAT_DM_LONG_REGEX:
                    parseDMLatDMLongLabel(matcher, 1, 4, 7);
                    break;
                case DM_LONG_DM_LAT_REGEX:
                    parseDMLatDMLongLabel(matcher, 4, 1, 7);
                    break;
                case DMS_LONG_NN_LAT_REGEX:
                    parseDMSLatNNDMSLongLabel(matcher, 1, 4, 7);
                    break;
            }
            if (validLatitude(this.latitude) && validLongitude(this.longitude)) {
                return;
            }
        }
        System.err.println("Unable to process: " + input);
    }

    /**
     * Parses the latitude, longitude, and label from a standard lat/long string.
     * 
     * @param matcher        the Matcher object containing the matched groups from
     *                       the regex
     * @param latitudeIndex  the index of the latitude value in the Matcher's groups
     * @param longitudeIndex the index of the longitude value in the Matcher's
     *                       groups
     * @param labelIndex     the index of the label value in the Matcher's groups
     */
    private void parseLatLongLabel(Matcher matcher, int latitudeIndex, int longitudeIndex,
            int labelIndex) {
        String latitude = matcher.group(latitudeIndex);
        String longitude = matcher.group(longitudeIndex);
        this.latitude = Double.parseDouble(latitude);
        this.longitude = Double.parseDouble(longitude);
        this.label = matcher.group(labelIndex);
    }

    /**
     * Parses a latitude-longitude label in the format of non-negative latitude and
     * longitude followed by the letter N or S (for latitude) and E or W (for
     * longitude), possibly in the wrong order.
     *
     * @param matcher            the matcher containing the latitude, longitude,
     *                           sign of latitude, sign of longitude, and label
     *                           information
     * @param latitudeIndex      the index of the latitude group in the matcher
     * @param latitudeSignIndex  the index of the sign of the latitude group in the
     *                           matcher
     * @param longitudeIndex     the index of the longitude group in the matcher
     * @param longitudeSignIndex the index of the sign of the longitude group in the
     *                           matcher
     * @param labelIndex         the index of the label group in the matcher
     */
    private void parseNNLatNNLongLabel(Matcher matcher, int latitudeIndex,
            int longitudeIndex, int labelIndex) {
        String latitude = matcher.group(latitudeIndex);
        String latitudeSign = matcher.group(latitudeIndex + 1);
        String longitude = matcher.group(longitudeIndex);
        String longitudeSign = matcher.group(longitudeIndex + 1);
        this.latitude = parseParameterWithSign(latitude, latitudeSign);
        this.longitude = parseParameterWithSign(longitude, longitudeSign);
        this.label = matcher.group(labelIndex);
    }

    /**
     * Parses a latitude-longitude label in the format of non-negative latitude and
     * longitude followed by the letter N or S (for latitude), possibly in the wrong
     * order.
     *
     * @param matcher           the matcher containing the latitude, longitude, sign
     *                          of latitude, and label information
     * @param latitudeIndex     the index of the latitude group in the matcher
     * @param latitudeSignIndex the index of the sign of the latitude group in the
     *                          matcher
     * @param longitudeIndex    the index of the longitude group in the matcher
     * @param labelIndex        the index of the label group in the matcher
     */
    private void parseNNLatLongLabel(Matcher matcher, int latitudeIndex,
            int longitudeIndex, int labelIndex) {
        String latitude = matcher.group(latitudeIndex);
        String latitudeSign = matcher.group(latitudeIndex + 1);
        String longitude = matcher.group(longitudeIndex);
        this.latitude = parseParameterWithSign(latitude, latitudeSign);
        this.longitude = Double.parseDouble(longitude);
        this.label = matcher.group(labelIndex);
    }

    /**
     * Parses a latitude-longitude label in the format of non-negative latitude and
     * non-negative longitude, followed by the letter E or W (for longitude),
     * possibly in the wrong order.
     *
     * @param matcher           the matcher containing the latitude, longitude, sign
     *                          of longitude, and label information
     * @param latitudeIndex     the index of the latitude group in the matcher
     * @param longitudeIndex    the index of the longitude group in the matcher
     * @param latitudeSignIndex the index of the sign of the latitude group in the
     *                          matcher
     * @param labelIndex        the index of the label group in the matcher
     */
    private void parseLatNNLongLabel(Matcher matcher, int latitudeIndex, int longitudeIndex, int labelIndex) {
        String latitude = matcher.group(latitudeIndex);
        String longitude = matcher.group(longitudeIndex);
        String longitudeSign = matcher.group(longitudeIndex + 1);
        this.longitude = parseParameterWithSign(longitude, longitudeSign);
        this.latitude = Double.parseDouble(latitude);
        this.label = matcher.group(labelIndex);
    }

    /**
     * Parses a latitude-longitude label in the format of degree, minutes, and
     * seconds latitude and degree, minutes, and seconds longitude, followed by the
     * letter N or S (for latitude) and E or W (for longitude), possibly in the
     * wrong order.
     *
     * @param matcher           the matcher containing the latitude, longitude, sign
     *                          of longitude, and label information
     * @param latitudeIndex     the index of the latitude group in the matcher
     * @param longitudeIndex    the index of the longitude group in the matcher
     * @param latitudeSignIndex the index of the sign of the latitude group in the
     *                          matcher
     * @param labelIndex        the index of the label group in the matcher
     */
    private void parseDMSLatDMSLongLabel(Matcher matcher, int latitudeIndex, int longitudeIndex, int labelIndex) {
        String latitudeDegree = matcher.group(latitudeIndex);
        String latitudeMinute = matcher.group(latitudeIndex + 1);
        String latitudeSecond = matcher.group(latitudeIndex + 2);
        String latitudeSign = matcher.group(latitudeIndex + 3);
        this.latitude = parseParameterWithSign(
                Double.parseDouble(latitudeDegree) + (Double.parseDouble(latitudeMinute) / 60.0)
                        + (Double.parseDouble(latitudeSecond) / 3600.0),
                latitudeSign);
        String longitudeDegree = matcher.group(longitudeIndex);
        String longitudeMinute = matcher.group(longitudeIndex + 1);
        String longitudeSecond = matcher.group(longitudeIndex + 2);
        String longitudeSign = matcher.group(longitudeIndex + 3);
        this.longitude = parseParameterWithSign(
                Double.parseDouble(longitudeDegree) + (Double.parseDouble(longitudeMinute) / 60.0)
                        + (Double.parseDouble(longitudeSecond) / 3600.0),
                longitudeSign);
        this.label = matcher.group(labelIndex);
    }

    /**
     * Parses a latitude-longitude label in the format of degree and minutes
     * latitude and degree and minutes longitude, followed by the letter N or S (for
     * latitude) and E or W (for longitude), possibly in the wrong order.
     *
     * @param matcher           the matcher containing the latitude, longitude, sign
     *                          of longitude, and label information
     * @param latitudeIndex     the index of the latitude group in the matcher
     * @param longitudeIndex    the index of the longitude group in the matcher
     * @param latitudeSignIndex the index of the sign of the latitude group in the
     *                          matcher
     * @param labelIndex        the index of the label group in the matcher
     */
    private void parseDMLatDMLongLabel(Matcher matcher, int latitudeIndex, int longitudeIndex, int labelIndex) {
        String latitudeDegree = matcher.group(latitudeIndex);
        String latitudeMinute = matcher.group(latitudeIndex + 1);
        String latitudeSign = matcher.group(latitudeIndex + 2);
        this.latitude = parseParameterWithSign(
                Double.parseDouble(latitudeDegree) + (Double.parseDouble(latitudeMinute) / 60.0), latitudeSign);
        String longitudeDegree = matcher.group(longitudeIndex);
        String longitudeMinute = matcher.group(longitudeIndex + 1);
        String longitudeSign = matcher.group(longitudeIndex + 2);
        this.longitude = parseParameterWithSign(
                Double.parseDouble(longitudeDegree) + (Double.parseDouble(longitudeMinute) / 60.0), longitudeSign);
        this.label = matcher.group(labelIndex);
    }


    private void parseDMSLatNNDMSLongLabel(Matcher matcher, int latitudeIndex, int longitudeIndex, int labelIndex) {
        String latitudeDegree = matcher.group(latitudeIndex);
        String latitudeMinute = matcher.group(latitudeIndex + 1);
        String latitudeSecond = matcher.group(latitudeIndex + 2);
        String latitudeSign = matcher.group(latitudeIndex + 3);
        this.latitude = parseParameterWithSign(
                Double.parseDouble(latitudeDegree) + (Double.parseDouble(latitudeMinute) / 60.0)
                        + (Double.parseDouble(latitudeSecond) / 3600.0),
                latitudeSign);
        String longitudeDegree = matcher.group(longitudeIndex);
        String longitudeMinute = matcher.group(longitudeIndex + 1);
        String longitudeSecond = matcher.group(longitudeIndex + 2);
        this.longitude = Double.parseDouble(longitudeDegree) + (Double.parseDouble(longitudeMinute) / 60.0)
                        + (Double.parseDouble(longitudeSecond) / 3600.0);
        this.label = matcher.group(labelIndex);
    }

    /**
     * Parses the given parameter string with the given sign and returns the
     * corresponding parameter value.
     * 
     * @param parameterString The string representation of the parameter value to be
     *                        parsed.
     * @param sign            The sign of the parameter value, either "N" for north,
     *                        "S" for south, "E" for east or "W" for west.
     * @return The parameter value with the appropriate sign.
     * @throws NumberFormatException If the given parameter string cannot be parsed
     *                               as a double.
     */
    private double parseParameterWithSign(String parameterString, String sign) {
        double parameter = Double.parseDouble(parameterString);
        return sign.equalsIgnoreCase("N") || sign.equalsIgnoreCase("E") ? parameter : -parameter;
    }

    /**
     * Parses the given parameter value with the given sign and returns the
     * corresponding parameter value.
     * 
     * @param parameter Parameter value to be
     * 
     * @param sign      The sign of the parameter value, either "N" for north,
     *                  "S" for south, "E" for east or "W" for west.
     * @return The parameter value with the appropriate sign.
     * @throws NumberFormatException If the given parameter string cannot be parsed
     *                               as a double.
     */
    private double parseParameterWithSign(Double parameter, String sign) {
        return sign.equalsIgnoreCase("N") || sign.equalsIgnoreCase("E") ? parameter : -parameter;
    }

    /**
     * Determines whether the given latitude value is within the valid range of
     * latitudes.
     * 
     * @param latitude the latitude value to check.
     * @return true if the latitude value is valid, false otherwise.
     */
    public boolean validLatitude(Double latitude) {
        return latitude >= SMALLEST_LATITUDE && latitude <= LARGEST_LATITUDE;
    }

    /**
     * Determines whether the given longitude value is within the valid range of
     * longitudes.
     * 
     * @param longitude the longitude value to check.
     * @return true if the longitude value is valid, false otherwise.
     */
    public boolean validLongitude(Double longitude) {
        return longitude >= SMALLEST_LONGITUDE && longitude <= LARGEST_LONGITUDE;
    }

    /**
     * 
     * Returns the longitude of this location.
     * 
     * @return the longitude of this location
     */
    public double getLongitude() {
        return longitude;
    }

    /**
     * 
     * Sets the longitude of this location.
     * 
     * @param longitude the longitude of this location
     */
    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    /**
     * 
     * Returns the latitude of this location.
     * 
     * @return the latitude of this location
     */
    public double getLatitude() {
        return latitude;
    }

    /**
     * 
     * Sets the latitude of this location.
     * 
     * @param latitude the latitude of this location
     */
    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    /**
     * 
     * Returns the label of this location.
     * 
     * @return the label of this location
     */
    public String getLabel() {
        return label;
    }

    /**
     * 
     * Sets the label of this location.
     * 
     * @param label the label of this location
     */
    public void setLabel(String label) {
        this.label = label;
    }

    /**
     * Returns a string representation of this Location object.
     * 
     * @return a string representation of this Location object, including the
     *         latitude, longitude, and label.
     */
    public String toString() {
        return "Latitude: " + latitude + " Longitude: " + longitude + " Label: " + label;
    }
}
