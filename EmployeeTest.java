package com.company;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotEquals;

class EmployeeTest {

    @Test
    void serialize() {
        Employee test = new Employee("brad,weems,b@gmail.com,bweem,1,911,sales");
        String serialized = test.Serialize();
        String expected = "brad,weems,b@gmail.com,bweem,1,911,sales";
        assertNotEquals(expected,serialized);
    }
}