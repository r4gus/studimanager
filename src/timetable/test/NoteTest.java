package timetable.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Note;

import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class NoteTest {
    private Note note;


    @Test
    void hasExpiredTrue() {
        note = new Note("First Note", "Hello World", LocalDateTime.now().minusSeconds(3), false);
        assertTrue(note.hasExpired());

    }

    @Test
    void hasExpiredFalse() {
        note = new Note("First Note", "Hello World", LocalDateTime.now().plusSeconds(3), false);
        assertFalse(note.hasExpired());

    }
}