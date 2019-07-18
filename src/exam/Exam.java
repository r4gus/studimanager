package exam;

/**
 * Das <code>Exam</code> object repräsentiert eine Klausur, welche am Ende eines Semesters geschrieben wird.
 * @author Lukas Mendel
 */

import java.util.Date;

public class Exam {

    private String subjectNumber;
    private String technicalName;
    private String semester;
    private Date date;
    private String begin;
    private String duration;
    private String building;
    private int roomNumber;
    private int trialNumber;
    private double mark;
    private boolean insisted;
    private boolean currentExam;

    public Exam()
    {

    }

    public String getSubjectNumber() {
        return subjectNumber;
    }

    public void setSubjectNumber(String subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public String getTechnicalName() {
        return technicalName;
    }

    public void setTechnicalName(String technicalName) {
        this.technicalName = technicalName;
    }

    public String getSemester() {
        return semester;
    }

    public void setSemester(String semester) {
        this.semester = semester;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getDuration() {
        return duration;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }

    public String getBuilding() {
        return building;
    }

    public void setBuilding(String building) {
        this.building = building;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public void setRoomNumber(int roomNumber) {
        this.roomNumber = roomNumber;
    }

    public int getTrialNumber() {
        return trialNumber;
    }

    public void setTrialNumber(int trialNumber) {
        this.trialNumber = trialNumber;
    }

    public double getMark() {
        return mark;
    }

    public void setMark(double mark) {
        this.mark = mark;
    }

    public boolean isInsisted() {
        return insisted;
    }

    public void setInsisted(boolean insisted) {
        this.insisted = insisted;
    }

    public boolean isCurrentExam() {
        return currentExam;
    }

    public void setCurrentExam(boolean currentExam) {
        this.currentExam = currentExam;
    }
}
