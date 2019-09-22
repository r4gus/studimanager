package exam;

/**
 * The <code>Exam</code> object represents an exam written at the end of a semester.
 *
 * @author Lukas Mendel
 */

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import logging.MyLogger;

import java.time.LocalDate;
import java.util.Objects;

public class Exam {

    private SimpleStringProperty subjectNumber;
    private SimpleStringProperty technicalName;
    private SimpleStringProperty semester;
    private SimpleStringProperty date;
    private SimpleStringProperty begin;
    private SimpleStringProperty duration;
    private SimpleStringProperty building;
    private SimpleStringProperty roomNumber;
    private SimpleStringProperty trialNumber;
    private SimpleStringProperty mark;
    private SimpleStringProperty modulMark;
    private boolean insisted;
    private boolean currentExam;

    private static final String valueNotAvailibale = "N. V.";

    public Exam(String subjectNumber) {

        this(subjectNumber, Exam.valueNotAvailibale, "3", "2019-04-03", Exam.valueNotAvailibale, Exam.valueNotAvailibale, Exam.valueNotAvailibale, Exam.valueNotAvailibale, "1", Exam.valueNotAvailibale, Exam.valueNotAvailibale , false, true);
        MyLogger.LOGGER.entering(getClass().toString(), "Exam", new Object[]{subjectNumber});
        MyLogger.LOGGER.exiting(getClass().toString(), "Exam");
    }


    public Exam(String subjectNumber,
                String technicalName,
                String semester,
                String date,
                String begin,
                String duration,
                String building,
                String roomNumber,
                String trialNumber,
                String mark,
                String modulMark ,
                boolean insisted,
                boolean currentExam) {

        MyLogger.LOGGER.entering(getClass().toString(), "Exam", new Object[]{subjectNumber, technicalName, semester, date, begin, duration, building, roomNumber, trialNumber});
        this.subjectNumber = new SimpleStringProperty(subjectNumber);
        this.technicalName = new SimpleStringProperty(technicalName);
        this.semester = new SimpleStringProperty(semester);
        this.date = new SimpleStringProperty(date);
        this.begin = new SimpleStringProperty(begin);
        this.duration = new SimpleStringProperty(duration);
        this.building = new SimpleStringProperty(building);
        this.roomNumber = new SimpleStringProperty(roomNumber);
        this.trialNumber = new SimpleStringProperty(trialNumber);
        this.mark = new SimpleStringProperty(mark);
        this.modulMark = new SimpleStringProperty(modulMark);
        this.insisted = insisted;
        this.currentExam = currentExam;
        MyLogger.LOGGER.exiting(getClass().toString(), "Exam");
    }

    public String getSubjectNumber() {
        return subjectNumber.get();
    }

    public void setSubjectNumber(SimpleStringProperty subjectNumber) {
        this.subjectNumber = subjectNumber;
    }

    public String getTechnicalName() {
        return technicalName.get();
    }

    public void setTechnicalName(SimpleStringProperty technicalName) {
        this.technicalName = technicalName;
    }

    public String getSemester() {
        return semester.get();
    }

    public void setSemester(SimpleStringProperty semester) {
        this.semester = semester;
    }

    public String getDate() {
        return date.get();
    }

    public void setDate(SimpleStringProperty date) {
        this.date = date;
    }

    public String getBegin() {
        return begin.get();
    }

    public void setBegin(SimpleStringProperty begin) {
        this.begin = begin;
    }

    public String getDuration() {
        return duration.get();
    }

    public void setDuration(SimpleStringProperty duration) {
        this.duration = duration;
    }

    public String getBuilding() {
        return building.get();
    }

    public void setBuilding(SimpleStringProperty building) {
        this.building = building;
    }

    public String getRoomNumber() {
        return roomNumber.get();
    }

    public void setRoomNumber(SimpleStringProperty roomNumber) {
        this.roomNumber = roomNumber;
    }

    public String getTrialNumber() {
        return trialNumber.get();
    }

    public void setTrialNumber(SimpleStringProperty trialNumber) {
        this.trialNumber = trialNumber;
    }

    public String getMark() {
        return mark.get();
    }

    public void setMark(SimpleStringProperty mark) {
        this.mark = mark;
    }

    public String getModulMark() { return modulMark.get(); }

    public void setModulMark(SimpleStringProperty modulMark) { this.modulMark = modulMark; }

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

    @Override
    public boolean equals(Object o) {

        MyLogger.LOGGER.entering(getClass().toString(), "equals", new Object[]{o});
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Exam exam = (Exam) o;
        boolean result = insisted == exam.insisted &&
                currentExam == exam.currentExam &&
                Objects.equals(subjectNumber, exam.subjectNumber) &&
                Objects.equals(technicalName, exam.technicalName) &&
                Objects.equals(semester, exam.semester) &&
                Objects.equals(date, exam.date) &&
                Objects.equals(begin, exam.begin) &&
                Objects.equals(duration, exam.duration) &&
                Objects.equals(building, exam.building) &&
                Objects.equals(roomNumber, exam.roomNumber) &&
                Objects.equals(trialNumber, exam.trialNumber) &&
                Objects.equals(mark, exam.mark) &&
                Objects.equals(modulMark, exam.modulMark);
        MyLogger.LOGGER.exiting(getClass().toString(), "equals", new Object[]{result});
        return result;
    }


    @Override
    public String toString() {
        return "Exam{" +
                "subjectNumber=" + subjectNumber +
                ", technicalName=" + technicalName +
                ", semester=" + semester +
                ", date=" + date +
                ", begin=" + begin +
                ", duration=" + duration +
                ", building=" + building +
                ", roomNumber=" + roomNumber +
                ", trialNumber=" + trialNumber +
                ", mark=" + mark +
                ", modulMark=" + modulMark +
                ", insisted=" + insisted +
                ", currentExam=" + currentExam +
                '}';
    }
}
