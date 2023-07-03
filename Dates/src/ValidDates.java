public class ValidDates {

    public static void main(String[] args) {
        generateDates(" ");
        generateDates("-");
        generateDates("/");
    }

    /**
     * Method that generates all the valid dates between the years
     * 1753 and 3000 and in any of the following formats:
     * 
     * day: dd or d or 0d
     * month: mm or m or 0m or the first three letters of the month name (all in the
     * same case,
     * or with the first letter upper-case)
     * year: yy or yyyy
     * separator: - or / or <space>
     * 
     * Notes:
     * 1. Only one separator type to be used in one date
     * 2. 29th of February is only considered a valid date in leap years
     * 3. If the year is written with only two digits, the date lies between 1950
     * and 2049, so 65 means 1965 and 42 means 2042.
     * 
     * @param seperator the seperator to be used
     */
    public static void generateDates(String seperator) {

        final int SMALLEST_YEAR = 1753;
        final int LARGEST_YEAR = 3000;

        final String[] MONTHS = { "Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Oct", "Nov",
                "Dec" };

        int[] numberOfDays = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

        for (int year = SMALLEST_YEAR; year <= LARGEST_YEAR; year++) {
            numberOfDays[1] = isLeapYear(year) ? 29 : 28;
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= numberOfDays[month - 1]; day++) {
                    if(day < 10) {
                        System.out.println("0".concat(Integer.toString(day)) + seperator + month + seperator + year); 
                        System.out.println("0".concat(Integer.toString(day)) + seperator + MONTHS[month - 1] + seperator + year); 
                    }
                    if(day < 10 && month < 10) { 
                        System.out.println("0".concat(Integer.toString(day)) + seperator + "0".concat(Integer.toString(month)) + seperator + year);     
                    }
                    if(month < 10){
                        System.out.println(day + seperator + "0".concat(Integer.toString(month)) + seperator + year);     
                    }
                    System.out.println(day + seperator + month + seperator + year); 
                    System.out.println(day + seperator + MONTHS[month - 1] + seperator + year); 
                }    
            }
        }

        for (int year = 0; year <= 99; year++) {
            String yearString;
            if(year < 10) {
                yearString = "200".concat(Integer.toString(year));
            } else if(year < 50) {
                yearString = "20".concat(Integer.toString(year));
            } else {
                yearString = "19".concat(Integer.toString(year));
            }
            numberOfDays[1] = isLeapYear(Integer.parseInt(yearString)) ? 29 : 28;
            yearString = year < 10 ? "0".concat(Integer.toString(year)) : Integer.toString(year);
            for (int month = 1; month <= 12; month++) {
                for (int day = 1; day <= numberOfDays[month - 1]; day++) {
                    if(day < 10) {
                        System.out.println("0".concat(Integer.toString(day)) + seperator + month + seperator + yearString); 
                        System.out.println("0".concat(Integer.toString(day)) + seperator + MONTHS[month - 1] + seperator + yearString); 
                    }
                    if(day < 10 && month < 10) { 
                        System.out.println("0".concat(Integer.toString(day)) + seperator + "0".concat(Integer.toString(month)) + seperator + yearString);     
                    }
                    if(month < 10){
                        System.out.println(day + seperator + "0".concat(Integer.toString(month)) + seperator + yearString);     
                    }
                    System.out.println(day + seperator + month + seperator + yearString); 
                    System.out.println(day + seperator + MONTHS[month - 1] + seperator + yearString); 
                }    
            }    
        }

    }

    /**
     * Method that determines if a given year is a leap year or not
     * 
     * @param year the int to be evaluated
     * @return true if the int is a leap year, false otherwise
     */
    private static boolean isLeapYear(int year) {
        return year % 4 == 0 && (year % 100 != 0 || year % 400 == 0);
    }
}
