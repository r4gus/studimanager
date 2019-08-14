package timetable.test;

import custom_exceptions.UserException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import timetable.Facility;
import timetable.Lecture;
import timetable.Lecturer;
import timetable.Timetable;

import static org.junit.jupiter.api.Assertions.*;

class TimetableTest {
    private Timetable table, table2;
    Facility f1, f2, f3;
    Lecturer lect1, lect2, lect3;
    Lecture l1, l2, l3;

    @BeforeEach
    void setUp() {
        table = new Timetable(7, 2, 7);    // use if you want a clean slate
        table2 = new Timetable(7, 2, 7);   // use if you need some predefined objects

        f1 = table2.newFacility("G2", "0.23", "", "73434", "Aalen");
        f2 = table2.newFacility("G2", "0.23", "", "73434", "Aalen");
        f3 = table2.newFacility("G1", "1.44", "", "73434", "Aalen");

        lect1 = table2.newLecturer("Max", "Mustermann", "", f1);
        lect2 = table2.newLecturer("Max", "Mustermann", "", f2);
        lect3 = table2.newLecturer("Maya", "Mustermann", "", f3);

        l1 = table2.newLecture("Algorithmen", f1, lect1, false, null);
        l2 = table2.newLecture("Algorithmen", f2, lect2, false, null);
        l3 = table2.newLecture("OOP", f3, lect3, true, null);
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

    @Test
    void newFacilityTest() {
        Facility f1;

        // create a new Facility object
        assertNotNull((f1 = table.newFacility("G2", "0.23", "", "73434", "Aalen")));

        // try to create another object with the same arguments
        // instead of returning a new object it should return a reference to the object created before
        assertSame(f1, table.newFacility("G2", "0.23", "", "73434", "Aalen"));

        // add a second facility
        assertNotNull(table.newFacility("G2", "1.44", "", "73434", "Aalen"));
        assertEquals(2, table.getFACILITIES().getSize());
    }

    @Test
    void newLecturerTest() {
        Lecturer l1;

        // create a new Lecturer object
        assertNotNull((l1 = table.newLecturer("Max", "Mustermann", "max.mustermann@hs-aalen.de", null)));
        assertEquals(1, table.getLECTURERS().getSize());

        // try to create another object with the same arguments
        // instead of returning a new object it should return a reference to the object created before
        assertSame(l1, table.newLecturer("Max", "Mustermann", "max.mustermann@hs-aalen.de", null));
        assertEquals(1, table.getLECTURERS().getSize());

        // add a second Lecturer
        assertNotNull(table.newLecturer("Maxine", "Meyer", "maxine.meyer@hs-aalen.de", null));
        assertEquals(2, table.getLECTURERS().getSize());
    }

    @Test
    void newLectureTest() {
        Lecture l1;

        // create a new Lecture object
        assertNotNull((l1 = table.newLecture("Test1", null, null, false, null)));
        assertEquals(1, table.getLECTURES().getSize());

        // try to create another object with the same arguments
        // instead of returning a new object it should return a reference to the object created before
        assertSame(l1, table.newLecture("Test1", null, null, false, null));
        assertEquals(1, table.getLECTURES().getSize());

        // add a second Lecture
        assertNotNull(table.newLecture("Test2", null, null, true, null));
        assertEquals(2, table.getLECTURES().getSize());
    }

    @Test
    void newFacility_Lecturer_LectureTest() {
        // f2 and f1 reference the same object
        assertSame(f1, f2);

        // lect1 and lect2 reference the same object
        assertSame(lect1, lect2);

        // l1 and l2 reference the same object
        assertSame(l1, l2);

        // f3 is different from f2 and f1
        assertNotSame(f3, f2);

        // lect3 is different from lect2 and lect1
        assertNotSame(lect3, lect2);

        // l3 is different from l2 and l1
        assertNotSame(l3, l2);
    }

    @Test
    void addLectureTest() {
        // add successfully a lecture
        try {
            assertTrue(table2.addLecture(0,0, l1));
        } catch(UserException exc) {
            fail();
        }

        // try add the object twice
        try {
            table2.addLecture(0,0, l2);
            fail();
        } catch (UserException exc) {
            assertTrue(true);
        }

        // try to add null
        try {
            table2.addLecture(0,0, null);
            fail();
        } catch (UserException exc) {
            assertTrue(true);
        }

        // add successfully two other lectures
        try {
            table2.addLecture(0,0, l3);
            table2.addLecture(2,4, l2);
        } catch (UserException exc) {
            fail();
        }

        assertEquals("Algorithmen", table2.getUnit()[0][0].getElement(0).getTitle());
        assertEquals("OOP", table2.getUnit()[0][0].getElement(1).getTitle());
        assertEquals("Algorithmen", table2.getUnit()[2][4].getElement(0).getTitle());
        assertEquals(2, table2.getLectureMapSize()); // one lecture was assigned to multiple units
    }

    @Test
    void getLectureTest() {
        try {
            assertTrue(table2.addLecture(0,0, l1));
            assertTrue(table2.addLecture(0,0, l3));
            assertTrue(table2.addLecture(1,4, l2));
            assertTrue(table2.addLecture(0,6, l3));
        } catch(UserException exc) {
            fail();
        }

        try {
            assertEquals(l1, table2.getLecture(0,0, 0));
            assertEquals(l3, table2.getLecture(0,0, 1));
            assertEquals(l2, table2.getLecture(1,4, 0));
            assertEquals(l3, table2.getLecture(0,6, 0));
        } catch (UserException exc) {
            fail();
        }

        try {
            table2.addLecture(0, 7, l1);
            fail();
        } catch (UserException exc) {
            assertTrue(true);
        }
    }
}