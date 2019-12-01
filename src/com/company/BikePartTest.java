package com.company;

import junit.framework.TestCase;
import org.junit.*;

public class BikePartTest extends TestCase {

    @Test
    public void testSerialize() {
        System.out.println("Serialize test: ");
        BikePart testPart = new BikePart("testPart", 1234567890,
                23.39, 19.57, false, 35, 5);
        String serialized = testPart.serialize();
        String expected = "testPart,1234567890,23.39,19.57,false,35,5\n";
        assertEquals(expected, serialized);
    }
}