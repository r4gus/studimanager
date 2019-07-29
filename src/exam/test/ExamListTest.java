package exam.test;

import custom_exceptions.UserException;
import exam.Exam;
import exam.ExamList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class ExamListTest {

    private ExamList examList = new ExamList();
    private ExamList examListEmpty = new ExamList();
    private Exam f1, f2, f3, f4, f5, f6, f7, f8;

    @BeforeEach
    void setUp() {
        examList = new ExamList();
        f1 = new Exam("0");
        f2 = new Exam("10");
        f3 = new Exam("1", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                true, true);
        f4 = new Exam("2", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                true, true);
        f5 = new Exam("3", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                true, false);
        f6 = new Exam("4", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                false, true);
        f7 = new Exam("5", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                false, false);
        f8 = new Exam("101");


        try {
            examList.addExam(f1);
            examList.addExam(f2);
            examList.addExam(f3);
            examList.addExam(f4);
            examList.addExam(f5);
            examList.addExam(f6);
            examList.addExam(f7);
        } catch (UserException e) {

        }

    }

    @Test
    void deleteObjektFromArrayList() {

        assertEquals(7, examList.size());
        examList.deleteExam(1);
        assertEquals(6, examList.size());
    }

    @Test
    void addObjektToArrayList() {

        assertEquals(7, examList.size());
        try {
            examList.addExam(f8);
        } catch (UserException e) {

        }
        assertEquals(8, examList.size());
    }

    @Test
    void addExistingObjektFromArrayList() {

        assertEquals(7, examList.size());
        try {
            examList.addExam(f7);
        }catch (UserException e)
        {
            assertTrue(true);
        }
    }


    @Test
    void deleteObjektFromEmtyArrayList() {

        assertEquals(0, examListEmpty.size());
        try {
            examList.deleteExam(0);
        }catch (IllegalArgumentException e)
        {
           assertTrue(true);
        }
    }

    @Test
    void getUpcomingExams() {

        ObservableList<Exam> obserList = FXCollections.observableArrayList();
        obserList = examList.getExamWithSpecalProperties("upc");
        assertEquals(5, obserList.size());
    }

    @Test
    void getPassedExams() {

        ObservableList<Exam> obserList = FXCollections.observableArrayList();
        obserList = examList.getExamWithSpecalProperties("pas");
        assertEquals(3, obserList.size());
    }

    @Test
    void getFailedExams() {

        ObservableList<Exam> obserList = FXCollections.observableArrayList();
        obserList = examList.getExamWithSpecalProperties("fai");
        assertEquals(4, obserList.size());
    }
}