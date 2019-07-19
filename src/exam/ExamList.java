package exam;

import custom_exceptions.UserException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.*;

/**
 * The <code>ExamList</code> object represents a list of exams written at the end of a semester.
 * It contains all basic operations to insert or delete a Exam.
 *
 * @author Lukas Mendel
 */

public class ExamList {

    protected static final Logger logger = Logger.getLogger(ExamList.class.getName());

    static {
        try {
            Handler handler = new FileHandler("log/ExamList-log.%u%g.txt", 2048 * 2048, 1, true);
            handler.setFormatter(new SimpleFormatter());
            logger.addHandler(handler);
            logger.setLevel(Level.FINER);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private ArrayList<Exam> exams = new ArrayList<>();

    public ExamList() {

    }

    /**
     * the method deletes an element Exam from the arrayList in Exams
     *
     * @param exam Element Exam which should be added to the ArrayList
     */

    public void addExam(Exam exam) throws UserException {

        logger.entering(getClass().toString(), "addExam", new Object[]{exam});
        for (Exam e : exams) {

            try {

                if (exam.equals(e)) {
                    throw new IllegalArgumentException("Eintrag ist bereits vorhanden");
                }
            } catch (IllegalArgumentException exception) {
                //loggen
                throw new UserException("Die Prüfung mit dieser Nummer exisitert bereits");
            }

        }
        exams.add(exam);
        logger.exiting(getClass().toString(), "addExam");
    }

    /**
     * the method deletes an element Exam from the arrayList in Exams
     *
     * @param index index indicates which element should be deleted from ArrayList
     */

    public void deleteExam(int index) {

        if (exams.size() > index) {
            exams.remove(index);
        } else {
            throw new IllegalArgumentException("Index zu groß");
        }
    }

    /**
     * the method returns the length of the arraylist.
     */

    public int size() {
        return exams.size();
    }

    /**
     * the method iterates over the individual elements of the array list and checks certain parameters is set to true.
     * If yes, the element is added to the returning list.
     *
     * @param parameter :"upc" = upcoming exam / "pas" = passed exam / "fai" = failed exam
     */
    public ObservableList<Exam> getExamWithSpecalProperties(String parameter) {
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
        return obserList;
    }
}
