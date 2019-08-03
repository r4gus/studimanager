package guiExam.EditWindow;

import guiExam.ControllerExam;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerEditWindow implements Initializable {

    @FXML
    public TextField textFieldLectureNr;
    @FXML
    public TextField textFieldtechnicalName;
    @FXML
    public ComboBox ComboBoxSemester;
    @FXML
    public DatePicker DatePickerFieldDate;
    @FXML
    public TextField textFieldBegin;
    @FXML
    public TextField textFieldDuration;
    @FXML
    public TextField textFieldBuilding;
    @FXML
    public TextField textFieldRoomNumber;
    @FXML
    public TextField textFieldTrialNumber;


    ControllerExam controllerExam;

    public ControllerEditWindow()
    {

    }

    public ControllerEditWindow(ControllerExam controllerExam)
    {
        this.controllerExam = controllerExam;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
