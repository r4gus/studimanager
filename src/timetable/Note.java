package timetable;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * Represents a note/ notepad where the user can store important information regarding a lecture.
 * @author David Sugar
 */
public class Note {
    private String title;
    private String body;
    private LocalDateTime expirationDate;
    private boolean important;

    public Note(String title, String body, LocalDateTime expirationDate, boolean important) {
        this.title = title;
        this.body = body;
        this.expirationDate = expirationDate;
        this.important = important;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDateTime expirationDate) {
        this.expirationDate = expirationDate;
    }

    public boolean isImportant() {
        return important;
    }

    public void setImportant(boolean important) {
        this.important = important;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        return title.equals(note.title) &&
                body.equals(note.body) &&
                Objects.equals(expirationDate, note.expirationDate);
    }

    /**
     * Check if this note has been expired.
     * @return True if it has expired, false otherwise.
     */
    public boolean hasExpired() {
        if(this.expirationDate == null) return false;
        else return this.expirationDate.isBefore(LocalDateTime.now());
    }

}
