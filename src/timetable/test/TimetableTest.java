package timetable.test;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Timetable;

import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {
    private Timetable table;

    @BeforeEach
    void setUp() {
        table = new Timetable(7, 2);
    }

    @Test
    void timetableTest() {
        // check the default time values assigned to each unit
        assertEquals("08:00", table.getUnit()[0][0].getFrom().toString());
        assertEquals("09:45", table.getUnit()[1][1].getFrom().toString());
        assertEquals("11:30", table.getUnit()[2][2].getFrom().toString());
        assertEquals("14:00", table.getUnit()[3][3].getFrom().toString());
        assertEquals("15:45", table.getUnit()[4][4].getFrom().toString());
        assertEquals("17:30", table.getUnit()[5][5].getFrom().toString());
        assertEquals("19:15", table.getUnit()[6][6].getFrom().toString());
    }
}