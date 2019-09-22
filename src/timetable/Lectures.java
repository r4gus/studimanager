package timetable;

import com.fasterxml.jackson.annotation.JsonIgnore;
import logging.MyLogger;

import java.io.Serializable;
import java.time.LocalTime;
import java.util.logging.Level;

/**
 * Container that can be used to store {@link Lecture} objects. It extends the {@link Container} class
 * which provides all basic operations for adding and removing elements.
 *
 * @author David Sugar
 */
public class Lectures extends Container<Lecture> implements Serializable {
    @JsonIgnore
    private LocalTime from;  // start of the lecture
    @JsonIgnore
    private LocalTime to;    // end of the lecture

    private Lecture head; // lecture to display inside the grid pane

    public Lectures() {
        from = null;
        to = null;
    }

    public Lectures(LocalTime from, LocalTime to) {
        this.from = from;
        this.to = to;
    }

    public LocalTime getFrom() {
        return from;
    }

    public void setFrom(LocalTime from) {
        this.from = from;
    }

    public LocalTime getTo() {
        return to;
    }

    public void setTo(LocalTime to) {
        this.to = to;
    }

    public Lecture getHead() {
        return head;
    }

    public void setHead(Lecture head) {
        this.head = head;
    }

    /**
     * Add an {@link Lecture} object to the <code>Lectures</code> container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link Container#add(Object o)}.]
     *
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addLecture(Lecture o) throws IllegalArgumentException {
        MyLogger.LOGGER.entering(getClass().toString(), "addLecture", o);

        if (o == null) {
            MyLogger.LOGGER.log(Level.WARNING, "Invalid argument: null", o);
            throw new IllegalArgumentException("Invalid argument: null");
        }
        if (this.find(o) != -1) {
            MyLogger.LOGGER.log(Level.INFO, "the specified Lecture object already exists", o);
            return false;
        }

        /* set lecture to display on grid*/
        if (this.getSize() == 0) {
            this.setHead(o);  // the head is the lecture that gets later displayed representative
        }                                           // for all lectures assigned to a unit

        MyLogger.LOGGER.exiting(getClass().toString(), "addLecture");
        return this.add(o);
    }

    /**
     * Removes the specified {@code Lecture} object 'o' from the {@code Lectures} container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link Container#remove(Object o)}.]
     *
     * @param o The object to remove
     * @return true on successful deletion, false otherwise
     * @throws IllegalArgumentException Throws an Exception if the calling function passes null as an argument
     */
    public boolean removeLecture(Lecture o) throws IllegalArgumentException {
        MyLogger.LOGGER.entering(getClass().toString(), "removeLecture", o);

        if (o == null) {
            MyLogger.LOGGER.log(Level.WARNING, "Invalid argument: null");
            throw new IllegalArgumentException("Invalid argument: null");
        }

        /*
        replace the head element if the currently assigned head equals o.
         */
        if (o.equals(this.head)) {
            if (this.getSize() > 1)
                this.head = (this.getElement(0).equals(o) ? this.getElement(1) : this.getElement(0));
            else
                this.head = null;
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "removeLecture");
        return this.remove(o);
    }

    /**
     * Get the start and end time of the unit/ lecture separated with an '-'.
     * @return start and end time separated with an '-'
     */
    @JsonIgnore
    public String getTime() {
        return this.from + "\n-\n" + this.to;
    }
}
