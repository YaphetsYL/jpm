package com.jpmorgan.assignment;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Lei
 * @version 1.0
 */
class AdminTest {

    private Admin admin;

    @BeforeEach
    void setUp() {
        admin = new Admin();
    }

    @Test
    void testSetupShow() {
        // Positive test case
        admin.setupShow(1, 5, 10, 2);
        assertTrue(admin.getShowMap().containsKey(1));

        // Negative test cases
        assertThrows(IllegalArgumentException.class, () -> admin.setupShow(-1, 5, 10, 2));
        assertThrows(IllegalArgumentException.class, () -> admin.setupShow(1, 5, 11, 2));
        assertThrows(IllegalArgumentException.class, () -> admin.setupShow(1, -1, 10, 2));
        assertThrows(IllegalArgumentException.class, () -> admin.setupShow(1, 5, 10, 0));
    }

    @Test
    void testViewShows() {
        // Create an Admin instance and set up a show
        Admin admin = new Admin();
        admin.setupShow(2, 3, 5, 2);

        // Redirect standard output to a ByteArrayOutputStream
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        // Call viewShows with showNumber = 2 and verify output
        admin.viewShows(2);
        String expectedOutput = "No ticket has been sold";
        assertTrue(outContent.toString().trim().contains(expectedOutput));

        // Reset standard output stream
        System.setOut(System.out);
    }

}