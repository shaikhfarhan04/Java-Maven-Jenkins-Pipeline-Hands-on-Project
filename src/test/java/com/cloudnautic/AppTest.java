package com.cloudnautic;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AppTest {

    @Test
    public void testGetMessage() {

        String expected =
                "Java Maven Jenkins Pipeline is working!";

        String actual = App.getMessage();

        assertEquals(expected, actual);
    }

    @Test
    public void testAddition() {

        int expected = 10;

        int actual = App.add(5, 5);

        assertEquals(expected, actual);
    }

    @Test
    public void testAnotherAddition() {

        int expected = 25;

        int actual = App.add(10, 15);

        assertEquals(expected, actual);
    }
}
