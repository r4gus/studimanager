package timetable;

import java.io.Serializable;
import java.util.Objects;
import logging.MyLogger;

/**
 * Used to represent a school or university building.
 *
 * @author David Sugar
 */
public class Facility implements Serializable {
    private String building;
    private String room;
    private String street;
    private String zipcode;
    private String city;


    public Facility(String building, String room, String street, String zipcode, String city) {
        MyLogger.LOGGER.entering(getClass().toString(), "Facility", new Object[]{
                building, room, street, zipcode, city
        });

        if(building == null) this.building = "";
        else this.building = building;


        if(room == null) this.room= "";
        else this.room = room;

        if(street == null) this.street = "";
        else this.street = street;

        if(zipcode == null) this.zipcode = "";
        else this.zipcode = zipcode;

        if(city == null) this.city = "";
        else this.city = city;

        MyLogger.LOGGER.exiting(getClass().toString(), "Facility");
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getZipcode() {
        return zipcode;
    }

    public void setZipcode(String zipcode) {
        this.zipcode = zipcode;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        MyLogger.LOGGER.entering(getClass().toString(), "equals", o);

        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Facility facility = (Facility) o;
        var ret = building.equals(facility.building) &&
                room.equals(facility.room) &&
                Objects.equals(street, facility.street) &&
                Objects.equals(zipcode, facility.zipcode) &&
                Objects.equals(city, facility.city);

        MyLogger.LOGGER.exiting(getClass().toString(), "equals", ret);
        return ret;
    }

    @Override
    public int hashCode() {
        return Objects.hash(building, room, street, zipcode, city);
    }

    @Override
    public String toString() {
        return this.building + " " + this.room;
    }
}
