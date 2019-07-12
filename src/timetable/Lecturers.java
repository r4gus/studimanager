package timetable;

/**
 * Container that holds all available Lecturer objects.
 * @author David Sugar
 */
public class Lecturers extends Container<Lecturer> {

    /**
     * Add an Lecturer object to the Lecturers container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link #add(Lecturer o) add(Lecturer o)}.]
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addLecturer(Lecturer o) throws IllegalArgumentException {
        if(o == null) throw new IllegalArgumentException("null pointer passed!");
        if(this.find(o) != -1) throw new IllegalArgumentException("The specified Lecturer already exists!");

        return this.add(o);
    }

}
