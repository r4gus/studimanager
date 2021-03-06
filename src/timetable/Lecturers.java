package timetable;

import logging.MyLogger;

import java.io.Serializable;
import java.util.logging.Level;

/**
 * Container that can be used to store available {@link Lecturer} objects. It extends the {@link Container} class
 * which provides all basic operations for adding and removing elements.
 *
 * @author David Sugar
 */
public class Lecturers extends Container<Lecturer> implements Serializable {

    /**
     * Add an {@link Lecturer} object to the Lecturers container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link Container#add(Object o) add(Lecturer o)}.]
     *
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addLecturer(Lecturer o) throws IllegalArgumentException {
        MyLogger.LOGGER.entering(getClass().toString(), "addLecture", o);

        if (o == null) {
            MyLogger.LOGGER.log(Level.WARNING, "null pointer passed!");
            throw new IllegalArgumentException("null pointer passed!");
        } if (this.find(o) != -1) {
            MyLogger.LOGGER.log(Level.WARNING, "The specified Lecturer already exists!");
            throw new IllegalArgumentException("The specified Lecturer already exists!");
        }

        var ret = this.add(o);

        MyLogger.LOGGER.entering(getClass().toString(), "addLecture", ret);
        return ret;
    }

}
