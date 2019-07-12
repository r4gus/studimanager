package timetable.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import timetable.Lecturer;

class LecturerTest {
    private Lecturer lect1, lect2, lect3, lect4, lect5, lect6, lect7;

    @org.junit.jupiter.api.BeforeEach
    void setUp() {
        lect1 = new Lecturer("Franziska", "Diaconu", "franzi@<3.de", null);
        lect2 = new Lecturer("David", "Sugar", "david.sugar@studmail.com", null);
        lect3 = new Lecturer("Franziska", "Diaconu", "franziska@web.de", null);
        lect4 = new Lecturer(null, "Diaconu", "franziska@web.de", null);
        lect5 = new Lecturer("Franziska", null, "franziska@web.de", null);
        lect6 = new Lecturer(null, null, "franziska@web.de", null);
        lect7 = new Lecturer(null, null, "franziska@web.de", null);

    }

    @org.junit.jupiter.api.Test
    void equalsTrue() {
        assertTrue(lect1.equals(lect1));    // same object reference
        assertTrue(lect1.equals(lect3));    // same state
        assertTrue(lect7.equals(lect6));    // same state

    }

    @Test
    void equalsFalse() {
        assertFalse(lect1.equals(lect2));
        assertFalse(lect1.equals(lect4));
        assertFalse(lect1.equals(lect5));
        assertFalse(lect1.equals(lect6));
        assertFalse(lect6.equals(lect4));
        assertFalse(lect6.equals(lect5));
        assertFalse(lect4.equals(lect5));
        assertFalse(lect2.equals(lect5));
        assertFalse(lect2.equals(null));
    }

}