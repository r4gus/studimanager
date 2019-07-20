package timetable;

import java.time.LocalDateTime;
import java.time.LocalTime;

import logging.MyLogger;

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
public class Timetable {
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
    private LocalDateTime dateTime;
    private int semester;

    public Timetable(int unitsPerDay, int semester) {
        this(unitsPerDay, semester, 8, 0, 90, 15, 60, 13, 0);
    }

    public Timetable(int unitsPerDay,
                     int semester,
                     int begin_h,
                     int begin_m,
                     long duration_m,
                     long break_time_m,
                     long lunch_time_m,
                     int begin_lunch_h,
                     int begin_lunch_m)
    {
        MyLogger.LOGGER.entering(getClass().toString(), "Timetable", new Object[]{
                unitsPerDay, semester, begin_h, begin_m, duration_m, break_time_m, lunch_time_m, begin_lunch_h, begin_lunch_m
        });

        this.days = MAX_DAYS;

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

        dateTime = LocalDateTime.now();


        MyLogger.LOGGER.exiting(getClass().toString(), "Timetable");
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
