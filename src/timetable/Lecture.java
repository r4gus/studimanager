package timetable;

import java.util.ArrayList;
import java.util.Objects;

/**
 * Represents a lecture one knows from university. A {@link Facility} and {@link Lecturer} object can be assigned to it
 * as well as a arbitrary number of {@link Note} objects which can be used to link important information.
 * @author David Sugar
 */
public class Lecture {
    private String title;
    private Facility facility;
    private Lecturer lecturer;
    private boolean elective;
    private Notes notes;

    public Lecture(String title, Facility facility, Lecturer lecturer, boolean elective, Notes notes) {
        this.title = title;
        this.facility = facility;
        this.lecturer = lecturer;
        this.elective = elective;

        if(notes == null) this.notes = new Notes();
        else this.notes = notes;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    public Lecturer getLecturer() {
        return lecturer;
    }

    public void setLecturer(Lecturer lecturer) {
        this.lecturer = lecturer;
    }

    public boolean isElective() {
        return elective;
    }

    public void setElective(boolean elective) {
        this.elective = elective;
    }

    public Notes getNotes() {
        return notes;
    }

    public void setNotes(Notes notes) {
        this.notes = notes;
    }

    /**
     * Find the specified {@link Note} object among all notes in {@link Notes} that are linked
     * to this lecture. Internally it calls the {@link Container#find(Object)} method.
     * @param n The note to find.
     * @return Index of the note, -1 if not found.
     */
    public int findNote(Note n) {
        return notes.find(n);
    }

    /**
     * Add a {@link Note} object to this lecture. This method is to be preferred over
     * the {@link Container#add(Object)} method used by the {@link Notes} class due to
     * additional argument checks.
     *
     * @param n The node to add.
     * @return True if the note has been successfully added, false otherwise.
     * @throws IllegalArgumentException If a similar note already exists or if null has been passed as an argument.
     */
    public boolean addNote(Note n) throws IllegalArgumentException {
        if(n == null) throw new IllegalArgumentException("null passed as an argument");
        if(findNote(n) != -1) throw new IllegalArgumentException("The specified note already exists");

        return this.notes.add(n);
    }

    /**
     * Return a {@link Note} object from the specified index. It uses the
     * {@link Container#getElement(int)} method internally which is inherited by the
     * {@link Notes} class.
     * @param i Index
     * @return Reference to a Note object on success, null otherwise.
     */
    public Note getNote(int i) {
        return notes.getElement(i);
    }

    /**
     * Remove the first occurrence of the specified note from the list. It uses the
     * {@link Container#remove(Object)} method internally which is inherited by the
     * {@link Notes} class.
     * @param n The {@link Note} object to remove
     * @return true on success, false otherwise.
     */
    public boolean removeNote(Note n) {
        return notes.remove(n);
    }

    /**
     * Remove the note at the specified index from the list and return it.It uses the
     * {@link Container#remove(int)} method internally which is inherited by the
     * {@link Notes} class.
     * @param i Index
     * @return {@link Note} object on success, null otherwise.
     */
    public Note removeNote(int i) {
        return notes.remove(i);
    }

    /**
     * Remove all notes that have expired from the <code>notes</code> list.
      */
    public void removeExpiredNotes() {
        for(int i = 0; i < notes.size(); i++) {
            if(getNote(i).hasExpired())
                removeNote(i);
        }
    }

    /**
     * equals()  compares this with the specified object and computes if
     * they are equal or not.
     * To be equal the following requirements must be fulfilled:
     * 1. The specified object must be of type Lecture
     * 2. They have to share the same value for the elective member
     * 3. The title has to be the same
     * 4. Both objects need to reference the same facility
     * 5. Both objects need to reference the same lecturer
     *
     * @param o The object to compare this object with.
     * @return true if they are equal, false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Lecture lecture = (Lecture) o;
        return elective == lecture.elective &&
                title.equals(lecture.title) &&
                Objects.equals(this.facility, lecture.facility) &&
                Objects.equals(this.lecturer, lecture.lecturer);
    }


    /**
     * <code>softCopy()</code> returns a new instance of {@link Lecture} which is
     * a direct copy of the object it has been invoked on. Both objects (this and copy) share
     * the same references to {@link Lecturer}, {@link Facility} and {@link Notes}.
     *
     * @return <code>Lecture</code> object (copy of this)
     */
    public Lecture softCopy() {
        return new Lecture(this.title, this.facility,
                this.lecturer, this.elective, this.notes);
    }
}
