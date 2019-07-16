package timetable;
import java.time.LocalTime;

/**
 * Container that can be used to store {@link Lecture} objects. It extends the {@link Container} class
 * which provides all basic operations for adding and removing elements.
 * @author David Sugar
 */
public class Lectures extends Container<Lecture> {
    private LocalTime from;  // start of the lecture
    private LocalTime to;    // end of the lecture

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

    /**
     * Add an {@link Lecture} object to the <code>Lectures</code> container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link #add(Lecture o)}.]
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addLecture(Lecture o) throws IllegalArgumentException {
        if(o == null) throw new IllegalArgumentException("null pointer passed!");
        if(this.find(o) != -1) throw new IllegalArgumentException("The specified Lecture already exists!");

        return this.add(o);
    }
}
