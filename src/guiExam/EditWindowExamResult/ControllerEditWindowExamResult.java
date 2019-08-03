package guiExam.EditWindowExamResult;

import exam.Exam;
import guiExam.ControllerExam;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEditWindowExamResult implements Initializable {

    @FXML
    public TextField textFieldLectureNrResult;
    @FXML
    public TextField textFieldtechnicalNameResult;
    @FXML
    public TextField textFieldMarkResult;
    @FXML
    public TextField textFieldmodulMarkResult;
    @FXML
    public TextField textFieldtrialNumberResult;

    private ControllerExam controllerExam;
    private Exam exam;


    public ControllerEditWindowExamResult()
    {

    }

    public ControllerEditWindowExamResult(ControllerExam controllerExam, Exam exam)
    {
        this.controllerExam = controllerExam;
        this.exam = exam;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
