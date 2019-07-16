package timetable.test;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
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
        assertEquals(lect1, lect1);    // same object reference
        assertEquals(lect1, lect3);    // same state
        assertEquals(lect7, lect6);    // same state

    }

    @Test
    void equalsFalse() {
        assertNotEquals(lect1, lect2);
        assertNotEquals(lect1, lect4);
        assertNotEquals(lect1, lect5);
        assertNotEquals(lect1, lect6);
        assertNotEquals(lect6, lect4);
        assertNotEquals(lect6, lect5);
        assertNotEquals(lect4, lect5);
        assertNotEquals(lect2, lect5);
        assertNotEquals(null, lect2);
    }

}