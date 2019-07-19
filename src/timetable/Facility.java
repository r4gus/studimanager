package timetable;

import java.util.Objects;
import logging.MyLogger;

/**
 * Used to represent a school or university building.
 *
 * @author David Sugar
 */
public class Facility {
    private String building;
    private String room;
    private String street;
    private String zipcode;
    private String city;


    public Facility(String building, String room, String street, String zipcode, String city) {
        MyLogger.LOGGER.entering(getClass().toString(), "Facility", new Object[]{
                building, room, street, zipcode, city
        });

        this.building = building;
        this.room = room;
        this.street = street;
        this.zipcode = zipcode;
        this.city = city;

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
}
