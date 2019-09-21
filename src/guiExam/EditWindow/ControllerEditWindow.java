package guiExam.EditWindow;

import exam.Exam;
import guiExam.ControllerExam;
import javafx.beans.property.SimpleStringProperty;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.stage.Stage;
import sample.Main;

import java.net.URL;
import java.time.LocalDate;
import java.util.ResourceBundle;

/**
 * The <code>ControllerEditWindow</code> object represents the controller of the Gui layoutEditWindow.
 *
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
    public Label labelHeading3;

    @FXML
    public Label labelLectureNr;
    @FXML
    public Label labeltechnicalName;
    @FXML
    public Label labelSemester;
    @FXML
    public Label labelPickerFieldDate;
    @FXML
    public Label labelBegin;
    @FXML
    public Label labelDuration;
    @FXML
    public Label labelBuilding;
    @FXML
    public Label labelRoomNumber;
    @FXML
    public Label labelTrialNumber;


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


    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        labelHeading3.setText(Main.getBundle().getString("heading3"));
        labelLectureNr.setText(Main.getBundle().getString("colSubjectNumber"));
        labeltechnicalName.setText(Main.getBundle().getString("coltechnicalName"));
        labelSemester.setText(Main.getBundle().getString("colSemester"));
        labelPickerFieldDate.setText(Main.getBundle().getString("colDate"));
        labelBegin.setText(Main.getBundle().getString("colBegin"));
         labelDuration.setText(Main.getBundle().getString("colDuration"));
        labelBuilding.setText(Main.getBundle().getString("colBuilding"));
        labelRoomNumber.setText(Main.getBundle().getString("colRoomNumber"));
        labelTrialNumber.setText(Main.getBundle().getString("colTrialNumber"));


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

        buttonSaveExamChanges.setText(Main.getBundle().getString("buttonSave"));
    }


    /**
     * The method converts a variable of the data type String into an int.
     */

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


    /**
     * The method turns a string into an int array, which is needed to create a date object.
     */

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

    /**
     * The method loads the user data from the interface and stores it in the corresponding Exam object.
     */

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
