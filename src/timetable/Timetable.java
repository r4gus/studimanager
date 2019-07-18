package timetable;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.logging.*;

/**
 * Represents a timetable with n <code>days</code> and m <code>unitsPerDay</code>.
 * An arbitrary number of {@link Lecture} objects can be assigned to each unit. This is facilitated
 * by the {@link Lectures} class which acts as a container for <code>lectures</code>.
 * <p>
 * An {@link Logger} object is used to log all actions e.g. method and constructor calls that
 * happen within the {@link timetable} package as well as Exceptions which occur during those calls.
 *
 * @author David Sugar
 */
public class Timetable {
    private static final int MIN_DAYS = 1;
    private static final int MAX_DAYS = 7;
    private static final int MIN_UNITS = 1;
    private static final int MAX_UNITS = 16;
    private static final int MIN_SEMESTER = 1;
    private static final LocalTime DEFAULT_BEGIN = LocalTime.of(8, 0);
    private static final long DEFAULT_DURATION_M = 90;
    private static final long DEFAULT_BREAK_TIME = 15;
    private static final long DEFAULT_LUNCH_TIME = 60;
    private static final LocalTime DEFAULT_LUNCH_AT = LocalTime.of(13, 0);

    // Setup logger
    protected static final Logger logger = Logger.getLogger(Timetable.class.getName());

    static {
        try {
            // New file handler with simple output
            Handler handler = new FileHandler("log/timetable-log.%u%g.txt", 2048 * 2048, 1, true);
            handler.setFormatter(new SimpleFormatter()); // Uses FXML format by default, uncomment for simple format

            // add new handler
            logger.addHandler(handler);
            logger.setLevel(Level.FINER);
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    private final int days;
    private final int unitsPerDay;
    private Lectures unit[][];
    private LocalDateTime dateTime;
    private int semester;

    public Timetable(int unitsPerDay, int semester) {
        logger.entering(getClass().toString(), "Timetable", new Object[]{
                unitsPerDay, semester
        });

        this.days = MAX_DAYS;

        if (unitsPerDay < MIN_UNITS || unitsPerDay > MAX_UNITS) this.unitsPerDay = 7;
        else this.unitsPerDay = unitsPerDay;

        if (semester < MIN_SEMESTER) this.semester = 1;
        else this.semester = semester;

        unit = new Lectures[unitsPerDay][days];

        // calculate the beginning and end of each unit
        for (int i = 0; i < days; i++)
            for (int j = 0; j < unitsPerDay; j++) {
                LocalTime start = DEFAULT_BEGIN.plusMinutes(DEFAULT_DURATION_M * j + DEFAULT_BREAK_TIME * j);
                if (start.compareTo(DEFAULT_LUNCH_AT) >= 0)
                    start = start.plusMinutes(DEFAULT_LUNCH_TIME - DEFAULT_BREAK_TIME);

                this.unit[j][i] = new Lectures(start, start.plusMinutes(DEFAULT_DURATION_M));
            }

        dateTime = LocalDateTime.now();


        logger.exiting(getClass().toString(), "Timetable");
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

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public void setDateTime(LocalDateTime dateTime) {
        this.dateTime = dateTime;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }
}
