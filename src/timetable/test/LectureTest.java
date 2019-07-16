package timetable.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Lecture;
import timetable.Note;


import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

class LectureTest {
    private Lecture l1, l2;
    private Note n1 = new Note("Don't forget", "Yo don't forget your tests", LocalDateTime.now().minusSeconds(3), true);
    private Note n2 = new Note("World", "Hello World", LocalDateTime.now().plusSeconds(10), false);
    private Note n3 = new Note("Test", "Test test", null, false);
    private Note n4 = new Note("Don't forget", "Yo don't forget your tests", null, true);

    @BeforeEach
    void setUp() {
        l2 = new Lecture("Algorithmen und Datenstrukturen", null, null, false, null);
        l2.addNote(n1);
        l2.addNote(n2);
    }

    @Test
    void instanciateWithNull() {
        l1 = new Lecture("Rechnerarchitektur", null, null, true, null);
        assertEquals(0, l1.getNotes().size());
    }

    @Test
    void findNote() {
        assertEquals(1, l2.findNote(n2)); // same address
        assertEquals(-1, l2.findNote(null)); // null
        assertEquals(0, l2.findNote(n4)); // same content
        assertEquals(-1, l2.findNote(n3)); // doesn't exist
    }

    @Test
    void addNote() {
        assertEquals(2, l2.getNotes().size());
        assertTrue(l2.addNote(n3));
        assertEquals(3, l2.getNotes().size());

        // try to add note twice
        try {
            l2.addNote(n3);
            fail();
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
        }

        // try to add null
        try {
            l2.addNote(null);
            fail();
        } catch (IllegalArgumentException exc) {
            assertTrue(true);
        }

        // no change in state
        assertEquals(3, l2.getNotes().size());
    }

    @Test
    void getNote() {
        // get note
        assertEquals(n1, l2.getNote(0));
        assertEquals(n2, l2.getNote(1));

        // out of bounds
        assertNull(l2.getNote(-1));
        assertNull(l2.getNote(2));
    }

    @Test
    void removeNote() {
        assertTrue(l2.removeNote(n2));
        assertEquals(1, l2.getNotes().size());

        // try to remove null
        assertFalse(l2.removeNote(null));
        // try to remove nonexistent object
        assertFalse(l2.removeNote(n3));
        // no change in state
        assertEquals(1, l2.getNotes().size());

        // out of bounds
        assertNull(l2.removeNote(-1));
        assertNull(l2.removeNote(1));

        // success
        assertEquals(n1, l2.removeNote(0));
        assertEquals(0, l2.getNotes().size());
    }

    @Test
    void removeExpiredNotes() {
        l2.addNote(n3);

        assertEquals(3, l2.getNotes().size());
        l2.removeExpiredNotes();
        assertEquals(2, l2.getNotes().size());
        assertEquals(n2, l2.getNote(0));
        assertEquals(n3, l2.getNote(1));
    }

    @Test
    void softCopyTest() {
        var clone = l2.softCopy();
        assertSame(l2.getClass(), clone.getClass()); // instances of the same class
        assertNotSame(l2, clone); // different objects in memory
        // share the same references on Facility, Lecturer, Notes
        assertSame(l2.getFacility(), clone.getFacility());
        assertSame(l2.getLecturer(), clone.getLecturer());
        assertSame(l2.getNotes(), clone.getNotes());
    }
}