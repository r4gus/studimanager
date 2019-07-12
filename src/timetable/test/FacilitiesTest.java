package timetable.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Facilities;
import timetable.Facility;
import timetable.Lecturer;
import timetable.Lecturers;

import static org.junit.jupiter.api.Assertions.*;

class FacilitiesTest {
    private Facilities facilities;
    private Facility f1, f2, f3, f4, f5;

    @BeforeEach
    void setUp() {
        facilities = new Facilities();
        f1 = new Facility("G2", "1.01", "", "73434", "Aalen");
        f2 = new Facility("G1", "1.42", "", "73434", "Aalen");
        f3 = new Facility("G2", "1.01", "", "73434", "Aalen");
        f4 = new Facility("G2", "1.01", null, null, null);
        f5 = new Facility("G2", "1.01", null, null, null);

        facilities.add(f1);
        facilities.add(f2);
    }

    @Test
    void add() {
        // add an element successfully
        assertEquals(2, facilities.getSize());
        assertTrue(facilities.add(f3));
        assertEquals(3, facilities.getSize());

        // try to add a null object
        assertFalse(facilities.add(null));
        assertEquals(3, facilities.getSize());
    }

    @Test
    void remove() {
        // remove an object successfully
        assertTrue(facilities.remove(f1));
        assertEquals(1, facilities.getSize());

        // try to remove an nonexistent object
        assertFalse(facilities.remove(f1));
        assertEquals(1, facilities.getSize());

        // try to remove an null object
        assertFalse(facilities.remove(null));
        assertEquals(1, facilities.getSize());

    }

    @Test
    void remove1() {
        // remove an object successfully
        assertEquals(f1, facilities.remove(0));
        assertEquals(1, facilities.getSize());

        // out of bounds -1
        assertNull(facilities.remove(-1));
        assertEquals(1, facilities.getSize());

        // out of bounds -1
        assertNull(facilities.remove(2));
        assertEquals(1, facilities.getSize());


    }

    @Test
    void find() {
        // Same object
        assertEquals(0, facilities.find(f1));

        // Same state
        assertEquals(0, facilities.find(f3));

        // Doesn't exist
        assertEquals(-1, facilities.find(f4));

        // Null obj
        assertEquals(-1, facilities.find(null));
    }

    @Test
    void getElement() {
        // In range
        assertEquals(f1, facilities.getElement(0));
        assertEquals(f2, facilities.getElement(1));

        // Out of bounds -1
        assertNull(facilities.getElement(-1));

        // Out of bounds 2
        assertNull(facilities.getElement(2));
    }

    @Test
    void addFacility() {
        // Add object successfully
        try {
            assertTrue(facilities.addFacility(f4));
        } catch(IllegalArgumentException exc) {
            fail("This shouldn't throw an exception");
        }

        // Null pointer
        try {
            facilities.addFacility(null);
            // Shouldn't come this far
            fail("This should have thrown an exception");
        } catch(IllegalArgumentException exc) {
            assertTrue(true);
        }

        // Try to add an existing object
        try {
            facilities.addFacility(f1);
            // Shouldn't come this far
            fail("Previous statement should have thrown an exception.");
        } catch(IllegalArgumentException exc) {
            assertTrue(true);
        }

        // Try to add an existing object in terms of state
        try {
            facilities.addFacility(f5);
            // Shouldn't come this far
            fail("Previous statement should have thrown an exception.");
        } catch(IllegalArgumentException exc) {
            assertTrue(true);
        }
    }
}