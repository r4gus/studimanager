package guiExam.EditWindow;

import exam.Exam;
import guiExam.ControllerExam;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * The <code>ControllerExam</code> object represents the controller of the Gui EditExamWindow.
 * ...
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class ControllerEditWindow implements Initializable {

    @FXML
    public TextField textFieldLectureNr;
    @FXML
    public TextField textFieldtechnicalName;
    @FXML
    public ComboBox comboBoxSemester;
    @FXML
    public DatePicker datePickerFieldDate;
    @FXML
    public TextField textFieldBegin;
    @FXML
    public TextField textFieldDuration;
    @FXML
    public TextField textFieldBuilding;
    @FXML
    public TextField textFieldRoomNumber;
    @FXML
    public ComboBox comboBoxTrialNumber;

    @FXML
    private Button buttonSaveExamChanges;

    private ControllerExam controllerExam;
    private Exam exam;


    public ControllerEditWindow() {

    }

    public ControllerEditWindow(ControllerExam controllerExam, Exam exam) {
        this.controllerExam = controllerExam;
        this.exam = exam;
    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        comboBoxSemester.getItems().addAll("1", "2", "3", "4", "5", "6", "7", "8", "9", "10");
        comboBoxTrialNumber.getItems().addAll("1", "2", "3", "4", "5");
        textFieldLectureNr.setText(exam.getSubjectNumber());
        textFieldtechnicalName.setText(exam.getTechnicalName());
        comboBoxSemester.getSelectionModel().select(returnIndex(exam.getSemester()));
        datePickerFieldDate.setValue(LocalDate.of(returnDateYear()[0], returnDateYear()[1], returnDateYear()[2]));
        textFieldBegin.setText(exam.getBegin());
        textFieldDuration.setText(exam.getDuration());
        textFieldBuilding.setText(exam.getBuilding());
        textFieldRoomNumber.setText(exam.getRoomNumber());
        comboBoxTrialNumber.getSelectionModel().select(returnIndex(exam.getTrialNumber()));
    }

    public int returnIndex(String string) {
        int value = 0;
        switch (string) {

            case "1":
                value = 0;
                break;
            case "2":
                value = 1;
                break;
            case "3":
                value = 2;
                break;
            case "4":
                value = 3;
                break;
            case "5":
                value = 4;
                break;
            case "6":
                value = 5;
                break;
            case "7":
                value = 6;
                break;
            case "8":
                value = 7;
                break;
            case "9":
                value = 8;
                break;
            case "10":
                value = 9;
                break;
        }
        return value;
    }

    public int[] returnDateYear() {
        int[] datumInt = new int[3];
        String[] datumString;

        datumString = exam.getDate().split("-");

        for (int i = 0; i < 3; i++) {
            Integer trialNr = Integer.parseInt(datumString[i]);
            datumInt[i] = trialNr;
        }
        return datumInt;
    }

    @FXML
    private void buttonSaveExamChanges() {

        SimpleStringProperty value = new SimpleStringProperty();
        SimpleStringProperty value1 = new SimpleStringProperty();
        SimpleStringProperty value2 = new SimpleStringProperty();
        SimpleStringProperty value3 = new SimpleStringProperty();
        SimpleStringProperty value4 = new SimpleStringProperty();
        SimpleStringProperty value5 = new SimpleStringProperty();
        SimpleStringProperty value6 = new SimpleStringProperty();
        SimpleStringProperty value7 = new SimpleStringProperty();
        SimpleStringProperty value8 = new SimpleStringProperty();

        value.setValue(textFieldLectureNr.getText());
        exam.setSubjectNumber(value);
        value1.setValue(textFieldtechnicalName.getText());
        exam.setTechnicalName(value1);
        value2.setValue(comboBoxSemester.getSelectionModel().getSelectedItem().toString());
        exam.setSemester(value2);
        value3.setValue(datePickerFieldDate.getValue().toString());
        exam.setDate(value3);
        value4.setValue(textFieldBegin.getText());
        exam.setBegin(value4);
        value5.setValue(textFieldDuration.getText());
        exam.setDuration(value5);
        value6.setValue(textFieldBuilding.getText());
        exam.setBuilding(value6);
        value7.setValue(textFieldRoomNumber.getText());
        exam.setRoomNumber(value7);
        value8.setValue(comboBoxTrialNumber.getSelectionModel().getSelectedItem().toString());
        exam.setTrialNumber(value8);

        controllerExam.tableviewExams.refresh();
        Stage stage = (Stage) this.buttonSaveExamChanges.getScene().getWindow();
        stage.close();
    }

}
