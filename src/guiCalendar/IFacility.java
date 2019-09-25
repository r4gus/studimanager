package guiCalendar;

import timetable.Facility;

/**
 * A class that implements {@code IFacility} has a variable of type {@link timetable.Facility} that
 * can be set by other classes by invoking {@code setFacility()}.
 *
 * @author David Sugar
 */
public interface IFacility extends Updatable {
    void setFacility(Facility facility);
}
