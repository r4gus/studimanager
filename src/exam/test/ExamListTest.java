package exam.test;

import exam.Exam;
import exam.ExamList;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

class ExamListTest {

    private ExamList examList = new ExamList();
    private Exam f1, f2, f3, f4, f5, f6;

    @BeforeEach
    void setUp() {
        examList = new ExamList();
        f1 = new Exam();
        f2 = new Exam("36534");
        f3 = new Exam("56734", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                true, true);
        f4 = new Exam("56734", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                true, true);
        f5 = new Exam("56734", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                true, false);
        f6 = new Exam("56734", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                false, true);
        f3 = new Exam("56734", "Analysis", 2, LocalDate.of(2019, 4, 13),
                "9:00", "2:00", "G1", "0.23", 1, 2.3,
                false, false);


        examList.(f1);
        //  facilities.add(f2);
    }
}