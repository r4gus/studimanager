package timetable;

import java.io.Serializable;

/**
 * Container class to store {@link Note} objects. The class inherits most of it's functionality
 * form {@link Container}, like {@link Container#add(Object)} or {@link Container#remove(Object)}.
 * It's typically used by the {@link Lecture} class to share <code>notes</code> between objects.
 *
 * @author David Sugar
 */
public class Notes extends Container<Note> implements Serializable {
    /**
     * A basic size function.
     *
     * @return The number of elements held by Notes.
     */
    public int size() {
        return this.getSize();
    }
}
