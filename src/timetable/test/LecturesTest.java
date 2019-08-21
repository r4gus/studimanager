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
    void addLectureTest() {
        assertEquals(0, lectures.getSize());
        // add lecture successfully
        assertTrue(lectures.addLecture(l1));

        // expect false, object does already exist
        assertFalse(lectures.addLecture(l2));    // object does already exist

        // expect exception
        try {
            assertTrue(lectures.addLecture(null));    // null pointer
            fail();
        } catch (IllegalArgumentException exc) {
            assertEquals("Invalid argument: null", exc.getMessage());
        }

        assertTrue(lectures.addLecture(l3));
        assertEquals(2, lectures.getSize());
    }

    @Test
    void removeLectureTest() {
        /*
        ------------------- ADD SOME LECTURES ----------------------------
         */
        lectures.addLecture(l3);
        lectures.addLecture(l1);

        assertEquals(2, lectures.getSize());
        assertEquals(l3, lectures.getHead());

        /*
        ------------------- DELETE A LECTURE ----------------------------
         */
        assertTrue(lectures.removeLecture(l3));

        assertEquals(1, lectures.getSize());
        assertEquals(l1, lectures.getElement(0));
        assertEquals(l1, lectures.getHead());

        /*
        ------------------- CHECK IF NULL THROWS AN ERROR ----------------
         */
        try {
            lectures.removeLecture(null);
            fail();
        } catch (IllegalArgumentException exc) {
            assertEquals("Invalid argument: null", exc.getMessage());
        }

        assertEquals(1, lectures.getSize());

        /*
        -------------------- TRY TO DELETE AN NOT EXISTING OBJECT ---------
         */

        assertFalse(lectures.removeLecture(l3));
        assertEquals(1, lectures.getSize());

        lectures.removeLecture(l1);
        assertNull(lectures.getHead());
    }
}