package timetable;

/**
 * An Lecturer object represents a person who teaches/ lectures at a school/ university.
 * @author David Sugar
 */
public class Lecturer {
    private String firstName;
    private String lastName;
    private String email;
    private Facility facility;

    public Lecturer(String firstName, String lastName, String email, Facility facility) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.facility = facility;
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
        if(this == o)
        {
            return true;
        }
        else if(o == null)
        {
            return false;
        }
        else if(!(o instanceof Lecturer))
        {
            return false;
        }

        boolean b1, b2;

        if(this.firstName != null && ((Lecturer)o).firstName != null) {
            b1 = this.firstName.equals(((Lecturer) o).firstName);
        } else {
            b1 = this.firstName == null && ((Lecturer) o).firstName == null;
        }

        if(this.lastName != null && ((Lecturer)o).lastName != null) {
            b2 = this.lastName.equals(((Lecturer) o).lastName);
        } else {
            b2 = this.lastName == null && ((Lecturer) o).lastName == null;
        }

        return b1 && b2;
    }
}
