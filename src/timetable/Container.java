package timetable;

import java.util.ArrayList;

/**
 * A container can hold objects of the specified type T by utilizing the {@link ArrayList} class.
 * It is used to store objects of the same type at one place and implements basic functionality like
 * {@link #add(Object) add(Object} and {@link #remove(Object) remove(Object}.
 *
 * @param <T> Type of the objects one wants to store.
 * @author David Sugar
 */
public abstract class Container<T> {
    private ArrayList<T> container = new ArrayList<>();

    public ArrayList<T> getContainer() {
        return container;
    }

    public void setContainer(ArrayList<T> container) {
        this.container = container;
    }

    /**
     * Get the number of elements that the container holds.
     *
     * @return Number of elements as int.
     */
    public int getSize() {
        return container.size();
    }

    /**
     * Add an object of type T to the container.
     *
     * @param o The object to add.
     * @return true if the object has been successfully added, false otherwise.
     */
    public boolean add(T o) {
        if (o == null) return false;
        else return container.add(o);
    }

    /**
     * Remove the first element that equals the specified object from the container.
     * [NOTE: The behaviour of this function depends heavily on the implementation of the equals() method for objects of type T]
     *
     * @param o The object to remove.
     * @return true on success, false otherwise.
     */
    public boolean remove(T o) {
        if (o == null) return false;
        else return container.remove(o);
    }

    /**
     * Remove the object at index i from the container.
     *
     * @param i Index
     * @return A reference to the removed object on success, null otherwise.
     */
    public T remove(int i) {
        if (i < 0 || i >= container.size()) return null;
        else return container.remove(i);
    }

    /**
     * Find the specified object within the container.
     * [NOTE: The behaviour of this function depends heavily on the implementation of the equals() method for objects of type T]
     *
     * @param o Object to find.
     * @return The index of the object within the container, -1 if not found.
     */
    public int find(T o) {
        if (o != null) {
            for (int i = 0; i < container.size(); i++) {
                if (container.get(i).equals(o)) return i;
            }
        }
        return -1;
    }

    /**
     * Return the element at the specified index from the container.
     *
     * @param i Index
     * @return Object of type T on success, null otherwise.
     */
    public T getElement(int i) {
        if (i < 0 || i >= container.size()) return null;
        else return container.get(i);
    }
}
