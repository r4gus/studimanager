package exam;

import custom_exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import logging.MyLogger;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * The <code>ExamList</code> object represents a list of exams written at the end of a semester.
 * It contains all basic operations to insert or delete a Exam.
 *
 * @author Lukas Mendel
 */

public class ExamList {

    private ArrayList<Exam> exams = new ArrayList<>();

    public ExamList() {

    }

    /**
     * the method adds an element Exam to the arrayList in Exams
     *
     * @param exam Element Exam which should be added to the ArrayList
     */

    public void addExam(Exam exam) throws UserException {

        MyLogger.LOGGER.entering(getClass().toString(), "addExam", new Object[]{exam});
        for (Exam e : exams) {

            try {

                if (exam.equals(e)) {
                    throw new IllegalArgumentException("Eintrag ist bereits vorhanden");
                }
            } catch (IllegalArgumentException exception) {

                MyLogger.LOGGER.log(Level.WARNING, exception.getMessage(), exception);
                throw new UserException("Die Prüfung mit dieser Nummer exisitert bereits");
            }

        }
        exams.add(exam);
        MyLogger.LOGGER.exiting(getClass().toString(), "addExam");
    }

    /**
     * the method deletes an element Exam from the arrayList in Exams
     *
     * @param index index indicates which element should be deleted from ArrayList
     */

    public void deleteExam(int index) {

        MyLogger.LOGGER.entering(getClass().toString(), "deleteExam", new Object[]{index});
        if (exams.size() > index) {
            exams.remove(index);
        } else {
            throw new IllegalArgumentException("Index out of Bounds");
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "deleteExam");
    }

    /**
     * the method returns the length of the arraylist.
     */

    public int size() {

        MyLogger.LOGGER.entering(getClass().toString(), "size");
        int size = exams.size();
        MyLogger.LOGGER.exiting(getClass().toString(), "size", size);
        return size;
    }

    /**
     * the method iterates over the individual elements of the array list and checks certain parameters is set to true.
     * If yes, the element is added to the returning list.
     *
     * @param parameter :"upc" = upcoming exam / "pas" = passed exam / "fai" = failed exam
     */
    public ObservableList<Exam> getExamWithSpecalProperties(String parameter) {

        MyLogger.LOGGER.entering(getClass().toString(), "getExamWithSpecalProperties", new Object[]{parameter});
        ObservableList<Exam> obserList = FXCollections.observableArrayList();
        for (Exam e : exams) {

            switch (parameter) {
                case "upc":
                    if (e.isCurrentExam()) {
                        obserList.add(e);
                    }
                    break;
                case "pas":
                    if (e.isInsisted()) {
                        obserList.add(e);
                    }
                    break;
                case "fai":
                    if (!e.isInsisted()) {
                        obserList.add(e);
                    }
                    break;
            }
        }
        MyLogger.LOGGER.exiting(getClass().toString(), "getExamWithSpecalProperties", new Object[]{obserList});
        return obserList;
    }
}
