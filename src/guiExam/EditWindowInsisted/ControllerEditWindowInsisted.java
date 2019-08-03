package guiExam.EditWindowInsisted;

import exam.Exam;
import guiExam.ControllerExam;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.awt.*;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEditWindowInsisted implements Initializable {

    @FXML
    public TextField textFieldLectureNrInsisted;
    @FXML
    public TextField textFieldtechnicalNameInsisted;
    @FXML
    public TextField textFieldMarkInsisted;
    @FXML
    public TextField textFieldModulMarkInsisted;

    @FXML
    private javafx.scene.control.Button buttonSaveExamChangesInsisted;


    private ControllerExam controllerExam;
    private Exam exam;

    public ControllerEditWindowInsisted(ControllerExam controllerExam, Exam exam) {
        this.controllerExam = controllerExam;
        this.exam = exam;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        textFieldLectureNrInsisted.setText(exam.getSubjectNumber());
        textFieldtechnicalNameInsisted.setText(exam.getTechnicalName());
        textFieldMarkInsisted.setText(exam.getMark());
        textFieldModulMarkInsisted.setText(exam.getModulMark());

    }

    public void buttonSaveExamChangesInsisted()
    {

    }
}
