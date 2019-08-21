package timetable;

import logging.MyLogger;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * Container that can hold an arbitrary number of {@link Facility} objects. It extends the {@link Container} class
 * which provides all basic operations for adding and removing elements.
 *
 * @author David Sugar
 */
public class Facilities extends Container<Facility> implements Serializable {
    /**
     * Add an {@link Facility} object to the Facilities container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link Container#add(Object o)}.]
     *
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addFacility(Facility o) throws IllegalArgumentException {
        MyLogger.LOGGER.entering(getClass().toString(), "addFacility", o);

        if (o == null) {
            MyLogger.LOGGER.log(Level.WARNING, "null pointer passed!");
            throw new IllegalArgumentException("null pointer passed!");
        } if (this.find(o) != -1) {
            MyLogger.LOGGER.log(Level.WARNING, "The specified Facility already exists");
            throw new IllegalArgumentException("The specified Facility already exists!");
        }

        var ret = this.add(o);

        MyLogger.LOGGER.exiting(getClass().toString(), "addFacility", ret);
        return ret;
    }
}
