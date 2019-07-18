package exam;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;

/**
 * The <code>ExamList</code> object represents a list of exams written at the end of a semester.
 * It contains all basic operations to insert or delete a document.
 *
 * @author Lukas Mendel
 */

public class ExamList {

    private ArrayList<Exam> exams = new ArrayList<>();

    public ExamList() {

    }

    /**
     * the method deletes an element Exam from the arrayList in Exams
     *
     * @param exam Element Exam which should be added to the ArrayList
     */

    public void addExam(Exam exam) {
        exams.add(exam);
    }

    /**
     * the method deletes an element Exam from the arrayList in Exams
     *
     * @param index index indicates which element should be deleted from ArrayList
     */

    public void deleteExam(int index) {
        exams.remove(index);
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
