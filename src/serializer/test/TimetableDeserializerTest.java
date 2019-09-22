package serializer.test;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import serializer.SerializerIO;
import serializer.TimetableObjectCollection;
import timetable.*;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class TimetableDeserializerTest {
    TimetableObjectCollection timetableObjectCollection = null;
    Timetable timetable = null;
    Lecture l1, l2, l3, l4;

    @BeforeEach
    void setUp() {
        timetable = new Timetable(4, 7, 5);
        Facility f1 = timetable.newFacility("G2", "0.23", "", "73434", "Aalen");
        Facility f2 = timetable.newFacility("Hauptgebäude", "H.1", "", "73434", "Aalen");
        Facility f3 = timetable.newFacility("G1", "1.44", "", "73434", "Aalen");

        Lecturer lect1 = timetable.newLecturer("Max", "Mustermann", "mustermann@hs-aalen.de", f1);
        Lecturer lect2 = timetable.newLecturer("Klaus", "Dieter", null, null);
        Lecturer lect3 = timetable.newLecturer("Maya", "Mustermann", "musterfrau@gmx.de", f3);

        l1 = timetable.newLecture("Algorithmen", f1, lect1, false, null);
        l2 = timetable.newLecture("Algorithmen", f2, lect2, false, null);
        l3 = timetable.newLecture("OOP", f3, lect3, true, null);
        l4 = timetable.newLecture("Rechnerarchitektur", null, null, true, null);

        l1.addNote(new Note("Finish the paper before Friday", "Do what you've been told!", null, true));
        l1.addNote(new Note("Eat more vegetables!", "One apple a day keeps the doctor away.", null, true));
        l4.addNote(new Note("Eat more vegetables!", "One apple a day keeps the doctor away.", null, true));

        try {
            timetable.addLecture(0, 0, l1);
            timetable.addLecture(0,0, l3);
            timetable.addLecture(1, 0, l1);
            timetable.addLecture(3,2, l2);
            timetable.addLecture(1,4, l3);
            timetable.addLecture(3, 1, l4);
        } catch (IllegalArgumentException exc) {
            System.out.println("FUCK!");
        }
    }

    @AfterEach
    void tearDown() {
        File f = new File("files/test.json");
        f.delete();
    }

    @Test
    void deserialize() {
        try {
            timetable.store("files/test.json");    // serialize that shit
        } catch (IOException exc) {
            fail();
        }

        // check if everything got parsed the right way
        try {
            timetableObjectCollection = SerializerIO.loadJson("files/test.json");
            timetable = timetableObjectCollection.getTimetable();
        } catch (IOException exc) {
            fail();
        }

        /*
        ------------------ BASIC TIMETABLE SETTINGS ----------------------
         */
        assertEquals(4, timetable.getUnitsPerDay());
        assertEquals(7, timetable.getSemester());
        assertEquals(5, timetable.getDays());

        /*
        -------------------------- LECTURES ------------------------------
         */
        assertEquals(l1, timetable.getUnit()[0][0].getElement(0));
        assertEquals(l3, timetable.getUnit()[0][0].getElement(1));
        assertEquals(l1, timetable.getUnit()[1][0].getElement(0));
        assertEquals(l2, timetable.getUnit()[3][2].getElement(0));
        assertEquals(l3, timetable.getUnit()[1][4].getElement(0));
        assertEquals(l4, timetable.getUnit()[3][1].getElement(0));

    }
}