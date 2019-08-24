package timetable;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import custom_exceptions.UserException;
import logging.MyLogger;

import java.io.*;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.logging.Level;

/**
 * Represents a timetable with n <code>days</code> and m <code>unitsPerDay</code>.
 * An arbitrary number of {@link Lecture} objects can be assigned to each unit. This is facilitated
 * by the {@link Lectures} class which acts as a container for <code>lectures</code>.
 *
 * The constructor uses {@link #DEFAULT_BEGIN}, {@link #DEFAULT_DURATION_M}, {@link #DEFAULT_BREAK_TIME},
 * {@link #DEFAULT_LUNCH_TIME} and {@link #DEFAULT_LUNCH_TIME} to calculate the timespan for each lecture.
 * The beginning and end of each unit can then be obtained by calling {@link Lectures#getFrom()}
 * and {@link Lectures#getTo()} (timespan = to - from ).
 *
 * @author David Sugar
 */
public class Timetable implements Serializable {
    private static final int MIN_DAYS = 1;
    private static final int MAX_DAYS = 7;
    private static final int MIN_UNITS = 1;
    private static final int MAX_UNITS = 16;
    private static final int MIN_SEMESTER = 1;
    private final LocalTime DEFAULT_BEGIN;
    private final long DEFAULT_DURATION_M;
    private final long DEFAULT_BREAK_TIME;
    private final long DEFAULT_LUNCH_TIME;
    private final LocalTime DEFAULT_LUNCH_AT;

    private final int days;
    private final int unitsPerDay;
    private Lectures unit[][];
    private int semester;
    private Lectures LECTURES = new Lectures(); // used to keep record over all records
    private Lecturers LECTURERS = new Lecturers(); // used to keep record over all lecturers
    private Facilities FACILITIES = new Facilities(); // used to keep record over all facilities


    /**
     * Creates an object with default time settings
     */
    public Timetable(int unitsPerDay, int semester, int days) {
        this(unitsPerDay, semester, 8, 0, 90, 15, 60, 13, 0, days);
    }

    /** This constructor should be used in most cases. It provides precise control over the construction of the
     * Timetable object.
     *
     * @param unitsPerDay The number of lectures one can attend in a row at most.
     * @param semester The current semester
     * @param begin_h Begin of the first lecture (Hour)
     * @param begin_m Begin of the first lecture (Minute)
     * @param duration_m Duration of a lecture in minutes
     * @param break_time_m Timespan between two lectures in minutes
     * @param lunch_time_m Lunch time in minutes
     * @param begin_lunch_h Begin of the lunch time (Hour)
     * @param begin_lunch_m Begin of the lunch time (Minute)
     */
    public Timetable(int unitsPerDay,
                     int semester,
                     int begin_h,
                     int begin_m,
                     long duration_m,
                     long break_time_m,
                     long lunch_time_m,
                     int begin_lunch_h,
                     int begin_lunch_m,
                     int days)
    {
        MyLogger.LOGGER.entering(getClass().toString(), "Timetable", new Object[]{
                unitsPerDay, semester, begin_h, begin_m, duration_m, break_time_m, lunch_time_m, begin_lunch_h, begin_lunch_m, days
        });

        if(days > MAX_DAYS) this.days = MAX_DAYS;
        else if(days < 1) this.days = 1;
        else this.days = days;

        if (unitsPerDay < MIN_UNITS || unitsPerDay > MAX_UNITS) this.unitsPerDay = 7;
        else this.unitsPerDay = unitsPerDay;

        if (semester < MIN_SEMESTER) this.semester = 1;
        else this.semester = semester;

        this.DEFAULT_BEGIN = LocalTime.of(begin_h, begin_m);
        this.DEFAULT_DURATION_M = duration_m;
        this.DEFAULT_BREAK_TIME = break_time_m;
        this.DEFAULT_LUNCH_TIME = lunch_time_m;
        this.DEFAULT_LUNCH_AT =  LocalTime.of(begin_lunch_h, begin_lunch_m);

        unit = new Lectures[unitsPerDay][days];

        // calculate the beginning and end of each unit
        for (int i = 0; i < days; i++)
            for (int j = 0; j < unitsPerDay; j++) {
                LocalTime start = DEFAULT_BEGIN.plusMinutes(DEFAULT_DURATION_M * j + DEFAULT_BREAK_TIME * j);
                if (start.compareTo(DEFAULT_LUNCH_AT) >= 0)
                    start = start.plusMinutes(DEFAULT_LUNCH_TIME - DEFAULT_BREAK_TIME);

                this.unit[j][i] = new Lectures(start, start.plusMinutes(DEFAULT_DURATION_M));
            }

        MyLogger.LOGGER.exiting(getClass().toString(), "Timetable");
    }

    public Lectures getLECTURES() {
        return LECTURES;
    }

    public Lecturers getLECTURERS() {
        return LECTURERS;
    }

    public Facilities getFACILITIES() {
        return FACILITIES;
    }

    public int getDays() {
        return days;
    }

    public int getUnitsPerDay() {
        return unitsPerDay;
    }

    public Lectures[][] getUnit() {
        return unit;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public LocalTime getDEFAULT_BEGIN() {
        return DEFAULT_BEGIN;
    }

    public long getDEFAULT_DURATION_M() {
        return DEFAULT_DURATION_M;
    }

    public long getDEFAULT_BREAK_TIME() {
        return DEFAULT_BREAK_TIME;
    }

    public long getDEFAULT_LUNCH_TIME() {
        return DEFAULT_LUNCH_TIME;
    }

    public LocalTime getDEFAULT_LUNCH_AT() {
        return DEFAULT_LUNCH_AT;
    }

    /**
     * Get the today's date.
     * @return The today's date as String.
     */
    public String getDate() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern("E dd.MM.yyyy"));
    }

    /**
     *  This method returns an Lecture object. First it checks if an object with the given
     *  parameters already exists. If true a reference to the existing object is returned. Otherwise a new object
     *  is created.
     * @param title Title String
     * @param facility Facility object
     * @param lecturer Lecturer object
     * @param elective boolean
     * @param notes Notes object
     * @return {@link Lecture} object
     */
    public Lecture newLecture(String title, Facility facility, Lecturer lecturer, boolean elective, Notes notes) {
        MyLogger.LOGGER.entering(getClass().toString(), "newLecture", new Object[]{title, facility, lecturer, elective, notes});
        Lecture l = new Lecture(title, facility, lecturer, elective, notes);

        if(LECTURES.find(l) == -1) {
            LECTURES.addLecture(l);
        } else {
            l = LECTURES.getElement(LECTURES.find(l));
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "newLecture", l);
        return l;
    }

    /**
     * This method returns an Lecturer object. First it checks if an object with the given
     * parameters already exists. If true a reference to the existing object is returned. Otherwise a new object
     * is created.
     * @param firstName First Name String
     * @param lastName Last Name String
     * @param email E-Mail String
     * @param facility Facility object
     * @return {@link Lecturer} object
     */
    public Lecturer newLecturer(String firstName, String lastName, String email, Facility facility) {
        MyLogger.LOGGER.entering(getClass().toString(), "newLecturer", new Object[]{firstName, lastName, email, facility});
        Lecturer l = new Lecturer(firstName, lastName, email, facility);

        if(LECTURERS.find(l) == -1) {
            LECTURERS.addLecturer(l);
        } else {
            l = LECTURERS.getElement(LECTURERS.find(l));
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "newLecturer", l);
        return l;
    }

    /**
     *  This method returns an Facility object. First it checks if an object with the given
     *  parameters already exists. If true a reference to the existing object is returned. Otherwise a new object
     *  is created.
     * @param building Building String
     * @param room Room String
     * @param street Street String
     * @param zipcode zip-code String
     * @param city City String
     * @return {@link Facility} object
     */
    public Facility newFacility(String building, String room, String street, String zipcode, String city) {
        MyLogger.LOGGER.entering(getClass().toString(), "newFacility", new Object[]{building, room, street, zipcode, city});
        Facility f = new Facility(building, room, street, zipcode, city);

        if(FACILITIES.find(f) == -1) {
            FACILITIES.addFacility(f);
        } else {
            f = FACILITIES.getElement(FACILITIES.find(f));
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "newFacility", f);
        return f;
    }

    public boolean removeFromLECTURE(Lecture l) throws UserException {
        return false;
    }

    public Lecture removeFromLECTURE(int i) throws UserException {
        return null;
    }

    /**
     * Obtain a {@link Lecture} object from the specified unit.
     * @param unit The unit to get the lecture from.
     * @param day The day of the week
     * @param i Index
     * @return An {@link Lecture} object
     * @throws IllegalArgumentException Thrown if a negative number or a number larger then or equal {@link #MAX_DAYS} or {@link #MAX_UNITS} is passed to 'day' and/ or 'unit'.
     */
    public Lecture getLecture(int unit, int day, int i) throws IllegalArgumentException {
        if(unit < 0 || unit >= unitsPerDay || day < 0 || day >= days)
            throw new IllegalArgumentException("Index out of bounds");

        return getUnit()[unit][day].getElement(i);
    }

    /**
     * Add an {@code Lecture} object to the specified unit. Lectures are only added if it doesn't already exists.
     * If you try to add it anyway an {@code UserException} is thrown.
     *
     * @param unit The unit to add the lecture to
     * @param day The day
     * @param lecture The lecture to add
     * @return true on success, false otherwise (for example if null has been passed as argument)
     * @throws IllegalArgumentException Thrown if {@code #lecture} if index is out of bounds.
     */
    public boolean addLecture(int unit, int day, Lecture lecture) throws IllegalArgumentException {
        if(unit < 0 || unit >= unitsPerDay || day < 0 || day >= days)
            throw new IllegalArgumentException("Index out of bounds");

        MyLogger.LOGGER.entering(getClass().toString(), "addLecture", lecture);

        try {
            boolean x = getUnit()[unit][day].addLecture(lecture);

            MyLogger.LOGGER.exiting(getClass().toString(), "addLecture", x);
            return x;
        } catch (IllegalArgumentException exc) {
            MyLogger.LOGGER.log(Level.WARNING, exc.getMessage());
            return false;
        }
    }

    /**
     * Store the current Timetable object data at the specified location. It can later be retrieved by calling
     * the {@code load()} method.
     * @param path Place to store the data on the system
     */
    public void store(String path) {
        storeJson(path);
    }

    public void storeJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        TimetableSerializer timetableSerializer = new TimetableSerializer(Timetable.class);

        SimpleModule module = new SimpleModule("TimetableSerializer",
                new Version(0, 1, 0, null, null, null));
        module.addSerializer(Timetable.class, timetableSerializer);

        objectMapper.registerModule(module);

        try (FileOutputStream fout = new FileOutputStream(path)) {
            try {
                objectMapper.writerWithDefaultPrettyPrinter().writeValue(fout, this);
            } catch (JsonProcessingException exc) {
                exc.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loads data that has previously been stored by the {@code store()} method.
     * @param path File to retrieve the data from
     * @return Timetable object on success, null otherwise
     */
    public static Timetable load(String path) {
        return loadJson(path);
    }

    /**
     * Calls a custom deserializer {@link TimetableDeserializer#deserialize(JsonParser, DeserializationContext)}
     * method to retrieve data from a json file.
     * @param path Path to the Json file
     * @return Timetable object on success, null otherwise
     */
    public static Timetable loadJson(String path) {
        ObjectMapper objectMapper = new ObjectMapper();
        Timetable timetable = null;

        SimpleModule module = new SimpleModule("TimetableDeserializer",
                new Version(0, 1, 0, null, null, null));
        module.addDeserializer(Timetable.class, new TimetableDeserializer(Timetable.class));

        objectMapper.registerModule(module);

        try(FileInputStream fin = new FileInputStream(path)) {
            try {
                timetable = objectMapper.readValue(fin, Timetable.class);
            } catch (JsonProcessingException exc) {
                exc.printStackTrace();
            }
        } catch (FileNotFoundException exc) {
            /*
            let the user choose a file
             */
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        return timetable;
    }
}
