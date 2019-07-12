package timetable;

/**
 * Container that holds all available Facility objects.
 * @author David Sugar
 */
public class Facilities extends Container<Facility> {
    /**
     * Add an Facility object to the Facilities container.
     * [Note: This method implements better argument checks via Exceptions and should be preferred over
     * {@link #add(Facility o) add(Facility o)}.]
     * @param o The object to add.
     * @return True if the object has been added to the container successfully, false otherwise.
     * @throws IllegalArgumentException If a null pointer was passed or if the object already exists.
     */
    public boolean addFacility(Facility o) throws IllegalArgumentException {
        if(o == null) throw new IllegalArgumentException("null pointer passed!");
        if(this.find(o) != -1) throw new IllegalArgumentException("The specified Facility already exists!");

        return this.add(o);
    }
}
