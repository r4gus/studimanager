package timetable;

import logging.MyLogger;

import java.io.Serializable;
import java.util.Objects;

/**
 * An <code>Lecturer</code> object represents a person who teaches/ lectures at a school/ university.
 * An instance of this class can be assigned to a {@link Lecture} and on the other hand multiple lectures can be held
 * by the same <code>Lecturer</code>.
 *
 * @author David Sugar
 */
public class Lecturer implements Serializable {
    private String firstName;
    private String lastName;
    private String email;
    private Facility facility;

    public Lecturer(String firstName, String lastName, String email, Facility facility) {
        MyLogger.LOGGER.entering(getClass().toString(), "Lecturer", new Object[]{
                firstName, lastName, email, facility
        });

        if( firstName == null ) this.firstName = "";
        else this.firstName = firstName;

        if(lastName == null) this.lastName = "";
        else this.lastName = lastName;

        if(email == null) this.email = "";
        else this.email = email;

        this.facility = facility;

        MyLogger.LOGGER.exiting(getClass().toString(), "Lecturer");
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Facility getFacility() {
        return facility;
    }

    public void setFacility(Facility facility) {
        this.facility = facility;
    }

    @Override
    public boolean equals(Object o) {
        MyLogger.LOGGER.entering(getClass().toString(), "equals", o);

        if (this == o) {
            return true;
        } else if (o == null) {
            return false;
        } else if (!(o instanceof Lecturer)) {
            return false;
        }

        boolean b1, b2;

        if (this.firstName != null && ((Lecturer) o).firstName != null) {
            b1 = this.firstName.equals(((Lecturer) o).firstName);
        } else {
            b1 = this.firstName == null && ((Lecturer) o).firstName == null;
        }

        if (this.lastName != null && ((Lecturer) o).lastName != null) {
            b2 = this.lastName.equals(((Lecturer) o).lastName);
        } else {
            b2 = this.lastName == null && ((Lecturer) o).lastName == null;
        }

        var ret = b1 && b2;

        MyLogger.LOGGER.exiting(getClass().toString(), "equals", ret);
        return ret;
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, email, facility);
    }

    @Override
    public String toString() {
        return this.firstName + " " + this.lastName;
    }
}
