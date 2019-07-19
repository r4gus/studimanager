package timetable.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Lecture;
import timetable.Lectures;

import static org.junit.jupiter.api.Assertions.*;

class LecturesTest {
    private Lectures lectures;
    private Lecture l1, l2, l3;

    @BeforeEach
    void setUp() {
        lectures = new Lectures(null, null);
        l1 = new Lecture("OOP", null, null, false, null);
        l2 = new Lecture("OOP", null, null, false, null);
        l3 = new Lecture("Algorithmen", null, null, true, null);
    }

    @Test
    void addLecture() {
        assertEquals(0, lectures.getSize());
        // add lecture successfully
        assertTrue(lectures.addLecture(l1));

        // expect exception
        try {
            assertTrue(lectures.addLecture(l2));    // object does already exist
            fail();
        } catch (IllegalArgumentException exc) {
            assertEquals("The specified Lecture already exists!", exc.getMessage());
        }

        // expect exception
        try {
            assertTrue(lectures.addLecture(null));    // null pointer
            fail();
        } catch (IllegalArgumentException exc) {
            assertEquals("null pointer passed!", exc.getMessage());
        }

        assertTrue(lectures.addLecture(l3));
        assertEquals(2, lectures.getSize());
    }
}