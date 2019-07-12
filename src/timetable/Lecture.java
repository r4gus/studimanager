package timetable;

import java.util.ArrayList;

/**
 * Class that represents a lecture one knows from university.
 * @author David Sugar
 */
public class Lecture {
    private String title;
    private Facility facility;
    private Lecturer lecturer;
    private boolean elective;
    private ArrayList<Note> notes;

    public Lecture(String title, Facility facility, Lecturer lecturer, boolean elective, ArrayList<Note> notes) {
        this.title = title;
        this.facility = facility;
        this.lecturer = lecturer;
        this.elective = elective;

        if(notes == null) this.notes = new ArrayList<>();
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

    public ArrayList<Note> getNotes() {
        return notes;
    }

    public void setNotes(ArrayList<Note> notes) {
        this.notes = notes;
    }

    /**
     * Find the specified note within all the other notes.
     * @param n The note to find.
     * @return Index of the note, -1 if not found.
     */
    public int findNote(Note n) {
        if(n != null) {
            for(int i = 0; i < notes.size(); i++) {
                if(notes.get(i).equals(n)) return i;
            }
        }
        return -1;
    }

    /**
     * Add a note to this lecture.
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
     * Return the note from the specified index.
     * @param i Index
     * @return Reference to a Note object on success, null otherwise.
     */
    public Note getNote(int i) {
        if(i < 0 || i >= notes.size()) return null;
        else return notes.get(i);
    }
}
