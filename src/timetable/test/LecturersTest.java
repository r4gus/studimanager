package timetable.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Lecturer;
import timetable.Lecturers;

import static org.junit.jupiter.api.Assertions.*;

class LecturersTest {
    private Lecturers lecturers;
    private Lecturer lect1, lect2, lect3;

    @BeforeEach
    void setUp() {
        lecturers = new Lecturers();
        lect1 = new Lecturer("Franziska", "Diaconu", "franzi@<3.de", null);
        lect2 = new Lecturer("David", "Sugar", "david.sugar@studmail.com", null);
        lect3 = new Lecturer("Franziska", "Diaconu", "franziska@web.de", null);
    }

    @Test
    void addSuccess() {
        assertEquals(0, lecturers.getSize());
        lecturers.add(lect1);
        assertEquals(1, lecturers.getSize());
        assertEquals("Franziska", lecturers.getElement(0).getFirstName());
    }

    @Test
    void addFailure() {
        // try to add null object
        assertEquals(0, lecturers.getSize());
        assertFalse(lecturers.add(null));
        assertEquals(0, lecturers.getSize());
    }

    @Test
    void removeWithObject() {
        lecturers.add(lect1);
        lecturers.add(lect2);
        lecturers.add(lect3);
        assertEquals(3, lecturers.getSize());
        assertTrue(lecturers.remove(lect2));
        assertEquals(2, lecturers.getSize());
        assertEquals("Franziska", lecturers.getElement(0).getFirstName());
        assertEquals("Franziska", lecturers.getElement(1).getFirstName());
    }

    @Test
    void removeWithObjectNull() {
        lecturers.add(lect1);
        lecturers.add(lect2);
        lecturers.add(lect3);
        assertEquals(3, lecturers.getSize());
        assertFalse(lecturers.remove(null));
        assertEquals(3, lecturers.getSize());
        assertEquals("Franziska", lecturers.getElement(0).getFirstName());
        assertEquals("David", lecturers.getElement(1).getFirstName());
        assertEquals("Franziska", lecturers.getElement(2).getFirstName());
    }

    @Test
    void removeWithObjectNotFound() {
        lecturers.add(lect1);
        lecturers.add(lect3);
        assertEquals(2, lecturers.getSize());
        assertFalse(lecturers.remove(lect2));
        assertEquals(2, lecturers.getSize());
        assertEquals("Franziska", lecturers.getElement(0).getFirstName());
        assertEquals("Franziska", lecturers.getElement(1).getFirstName());
    }

    @Test
    void removeBecauseOfEquals() {
        lecturers.add(lect1);
        lecturers.add(lect2);
        assertEquals(2, lecturers.getSize());
        assertTrue(lecturers.remove(lect3));
        assertEquals(1, lecturers.getSize());
        assertEquals("David", lecturers.getElement(0).getFirstName());
    }

    @Test
    void remove1() {
        lecturers.add(lect1);
        lecturers.add(lect2);
        lecturers.add(lect3);
        assertEquals(3, lecturers.getSize());
        assertNull(lecturers.remove(3));
        assertNull(lecturers.remove(-1));
        assertEquals(3, lecturers.getSize());
        assertEquals("David", lecturers.remove(1).getFirstName());
    }

    @Test
    void find() {
        lecturers.add(lect1);
        lecturers.add(lect2);
        lecturers.add(lect3);
        assertEquals(1, lecturers.find(lect2));
        assertEquals(0, lecturers.find(lect3));
        lecturers.remove(lect1);
        assertEquals(1, lecturers.find(lect3));
        lecturers.remove(lect3);
        assertEquals(-1, lecturers.find(lect3));
    }

    @Test
    void addLecture() {
        // Add object successfully
        try {
            assertEquals(true, lecturers.addLecturer(lect1));
        } catch(IllegalArgumentException exc) {
            assertTrue(false);
        }

        // Null pointer
        try {
            lecturers.addLecturer(null);
            // Shouldn't come this far
            fail("Previous statement should have thrown an exception.");
        } catch(IllegalArgumentException exc) {
            assertTrue(true);
        }

        // Try to add an existing object
        try {
            lecturers.addLecturer(lect1);
            // Shouldn't come this far
            fail("Previous statement should have thrown an exception.");
        } catch(IllegalArgumentException exc) {
            assertTrue(true);
        }

        // Try to add an existing object in terms of state
        try {
            lecturers.addLecturer(lect3);
            // Shouldn't come this far
            fail("Previous statement should have thrown an exception.");
        } catch(IllegalArgumentException exc) {
            assertTrue(true);
        }
    }
}