import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

public class DateTest {

    @Test
    public void testValid() throws Exception {
        Date d1 = new Date("01 01 00");
        assertEquals(d1.date, "01 Jan 2000");

        Date d2 = new Date("29/Feb/00");
        assertEquals(d2.date, "29 Feb 2000");

        Date d3 = new Date("11/3/03");
        assertEquals(d3.date, "11 Mar 2003");

        Date d4 = new Date("31-oct-1999");
        assertEquals(d4.date, "31 Oct 1999");

        Date d5 = new Date("6 MAY 1999");
        assertEquals(d5.date, "06 May 1999");

        Date d6 = new Date("03-Feb-69");
        assertEquals(d6.date, "03 Feb 1969");

        Date d7 = new Date("13 03 2023");
        assertEquals(d7.date, "13 Mar 2023");

        Date d8 = new Date("31-dec-3000");
        assertEquals(d8.date, "31 Dec 3000");

        Date d9 = new Date("11 11 11");
        assertEquals(d9.date, "11 Nov 2011");

        Date d10 = new Date("28 09 1800");
        assertEquals(d10.date, "28 Sep 1800");
    }

    @Test
    public void invalidSeperator() throws Exception {
        Exception e1 = assertThrows(Exception.class, () -> new Date("dd.mm.yy"));
        assertEquals("Valid seperator not found", e1.getMessage());

        Exception e2 = assertThrows(Exception.class, () -> new Date("dd,mm,yy"));
        assertEquals("Valid seperator not found", e2.getMessage());

        Exception e3 = assertThrows(Exception.class, () -> new Date("ddmmyy"));
        assertEquals("Valid seperator not found", e3.getMessage());
    }

    @Test 
    public void invalidNumberOfSeperators() throws Exception {
        Exception e1 = assertThrows(Exception.class, () -> new Date(" dd mm yy"));
        assertEquals("Invalid number of seperators", e1.getMessage());

        Exception e2 = assertThrows(Exception.class, () -> new Date("dd mm yy "));
        assertEquals("Invalid number of seperators", e2.getMessage());

        Exception e3 = assertThrows(Exception.class, () -> new Date("dd  mm yy"));
        assertEquals("Invalid number of seperators", e3.getMessage());

        Exception e4 = assertThrows(Exception.class, () -> new Date("dd mm  yy"));
        assertEquals("Invalid number of seperators", e4.getMessage());

        Exception e5 = assertThrows(Exception.class, () -> new Date("-dd-mm-yy"));
        assertEquals("Invalid number of seperators", e5.getMessage());

        Exception e6 = assertThrows(Exception.class, () -> new Date("dd-mm-yy-"));
        assertEquals("Invalid number of seperators", e6.getMessage());

        Exception e7 = assertThrows(Exception.class, () -> new Date("dd--mm-yy"));
        assertEquals("Invalid number of seperators", e7.getMessage());

        Exception e8 = assertThrows(Exception.class, () -> new Date("dd-mm--yy"));
        assertEquals("Invalid number of seperators", e8.getMessage());

        Exception e9 = assertThrows(Exception.class, () -> new Date("/dd/mm/yy"));
        assertEquals("Invalid number of seperators", e9.getMessage());

        Exception e10 = assertThrows(Exception.class, () -> new Date("dd/mm/yy/"));
        assertEquals("Invalid number of seperators", e10.getMessage());

        Exception e11 = assertThrows(Exception.class, () -> new Date("dd//mm/yy"));
        assertEquals("Invalid number of seperators", e11.getMessage());

        Exception e12 = assertThrows(Exception.class, () -> new Date("dd/mm//yy"));
        assertEquals("Invalid number of seperators", e12.getMessage());
    }

    @Test 
    public void invalidYear() throws Exception {
        Exception e1 = assertThrows(Exception.class, () -> new Date("dd mm yy"));
        assertEquals("Year is not in a number format", e1.getMessage());

        Exception e2 = assertThrows(Exception.class, () -> new Date("dd mm 2"));
        assertEquals("Year is not in a yy or yyyy format", e2.getMessage());

        Exception e3 = assertThrows(Exception.class, () -> new Date("dd mm 200"));
        assertEquals("Year is not in a yy or yyyy format", e3.getMessage());

        Exception e4 = assertThrows(Exception.class, () -> new Date("dd mm 20000"));
        assertEquals("Year is not in a yy or yyyy format", e4.getMessage());

        Exception e5 = assertThrows(Exception.class, () -> new Date("dd mm -1"));
        assertEquals("Year is not in a yy or yyyy format", e5.getMessage());

        Exception e6 = assertThrows(Exception.class, () -> new Date("dd mm 1752"));
        assertEquals("Year out of range", e6.getMessage());

        Exception e7 = assertThrows(Exception.class, () -> new Date("dd mm 3001"));
        assertEquals("Year out of range", e7.getMessage());
    }

    @Test
    public void invalidMonth() throws Exception {
        Exception e1 = assertThrows(Exception.class, () -> new Date("dd mm 2000"));
        assertEquals("Month is not first three letters of the month name", e1.getMessage());   
        
        Exception e2 = assertThrows(Exception.class, () -> new Date("dd 001 2000"));
        assertEquals("Month is not a in mm or m or 0m format", e2.getMessage());

        Exception e3 = assertThrows(Exception.class, () -> new Date("dd 0 2000"));
        assertEquals("Month out of range", e3.getMessage());

        Exception e4 = assertThrows(Exception.class, () -> new Date("dd 13 2000"));
        assertEquals("Month out of range", e4.getMessage());

        Exception e5 = assertThrows(Exception.class, () -> new Date("dd 13 2000"));
        assertEquals("Month out of range", e5.getMessage());

        Exception e6 = assertThrows(Exception.class, () -> new Date("dd January 2000"));
        assertEquals("Month is not first three letters of the month name", e6.getMessage());

        Exception e7 = assertThrows(Exception.class, () -> new Date("dd jAN 2000"));
        assertEquals("Month is neither in the same case, or with the first letter upper-case", e7.getMessage());

        Exception e8 = assertThrows(Exception.class, () -> new Date("dd jaN 2000"));
        assertEquals("Month is neither in the same case, or with the first letter upper-case", e8.getMessage());

        Exception e9 = assertThrows(Exception.class, () -> new Date("dd JAn 2000"));
        assertEquals("Month is neither in the same case, or with the first letter upper-case", e9.getMessage());

        Exception e10 = assertThrows(Exception.class, () -> new Date("dd jAn 2000"));
        assertEquals("Month is neither in the same case, or with the first letter upper-case", e10.getMessage());

        Exception e11 = assertThrows(Exception.class, () -> new Date("dd JaN 2000"));
        assertEquals("Month is neither in the same case, or with the first letter upper-case", e11.getMessage());
    }

    @Test
    public void invalidDay() throws Exception {
        Exception e1 = assertThrows(Exception.class, () -> new Date("1st Jan 2000"));
        assertEquals("Day is not in number format", e1.getMessage());     
        
        Exception e2 = assertThrows(Exception.class, () -> new Date("001 Jan 2000"));
        assertEquals("Day is not in a dd or d or 0d format", e2.getMessage());  
        
        Exception e3 = assertThrows(Exception.class, () -> new Date("0 Jan 2000"));
        assertEquals("Day out of range", e3.getMessage()); 

        Exception e4 = assertThrows(Exception.class, () -> new Date("32 Jan 2000"));
        assertEquals("Day out of range", e4.getMessage());  

        Exception e5 = assertThrows(Exception.class, () -> new Date("29 Feb 1900"));
        assertEquals("Day out of range", e5.getMessage());   
    }
}
