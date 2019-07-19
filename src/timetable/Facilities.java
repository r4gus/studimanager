package timetable;

import logging.MyLogger;

/**
 * Container that can hold an arbitrary number of {@link Facility} objects. It extends the {@link Container} class
 * which provides all basic operations for adding and removing elements.
 *
 * @author David Sugar
 */
public class Facilities extends Container<Facility> {
    /**
     * Add an {@link Facility} object to the Facilities container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link #add(Facility o) add(Facility o)}.]
     *
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addFacility(Facility o) throws IllegalArgumentException {
        MyLogger.LOGGER.entering(getClass().toString(), "addFacility", o);

        if (o == null) throw new IllegalArgumentException("null pointer passed!");
        if (this.find(o) != -1) throw new IllegalArgumentException("The specified Facility already exists!");

        var ret = this.add(o);

        MyLogger.LOGGER.exiting(getClass().toString(), "addFacility", ret);
        return ret;
    }
}
