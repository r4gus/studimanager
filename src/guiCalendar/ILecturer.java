package guiCalendar;

import timetable.Lecturer;

/**
 * A class that implements {@code ILecturer} has a variable of type {@link timetable.Lecturer} that
 * can be set by other classes.
 *
 * @author David Sugar
 */
public interface ILecturer extends Updatable {
    void setLecturer(Lecturer lecturer);
}
