package timetable;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * The <code>Note</code> class can be used to store information related to a {@link Lecture}. Among
 * other things it has an <code>expirationDate</code> member that can hold a {@link LocalDateTime}
 * object indicating a point in time at which the <code>Note</code> will expire.
 *
 * @author David Sugar
 */
public class Note {
    private String title;
    private String body;
    private LocalDateTime expirationDate;
    private boolean important;

    public Note(String title, String body, LocalDateTime expirationDate, boolean important) {
        Timetable.logger.entering(getClass().toString(), "Note", new Object[]{title, body, expirationDate,
                important});

        this.title = title;
        this.body = body;
        this.expirationDate = expirationDate;
        this.important = important;

        Timetable.logger.exiting(getClass().toString(), "Note");
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
        Timetable.logger.entering(getClass().toString(), "equals", o);

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Note note = (Note) o;
        var res = title.equals(note.title) && body.equals(note.body);

        Timetable.logger.exiting(getClass().toString(), "equals", res);

        return res;
    }

    /**
     * Check if this note has been expired by comparing it to the current {@link LocalDateTime}.
     *
     * @return True if it has expired, false otherwise.
     */
    public boolean hasExpired() {
        Timetable.logger.entering(getClass().toString(), "equals");

        if (this.expirationDate == null) return false;
        else {
            Timetable.logger.exiting(getClass().toString(), "equals");
            return this.expirationDate.isBefore(LocalDateTime.now());
        }
    }

}
