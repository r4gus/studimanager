package guiExam;

import exam.Exam;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * The <code>ControllerExam</code> object represents the controller of the Gui layoutExam.
 * This is one of the tabs of the TabPane.
 * In the controller the logic is separated from the Gui and its elements.
 *
 * @author Lukas Mendel
 */

public class ControllerExam implements Initializable {

    @FXML
    public TextField textfieldLectureNumber;
    @FXML
    public TableView<Exam> tableviewExams;
    @FXML
    public TableView<Exam> tableviewExamsInsisted;
    @FXML
    public TableColumn colSubjectNumber;
    @FXML
    public TableColumn coltechnicalName;
    @FXML
    public TableColumn colSemester;
    @FXML
    public TableColumn colDate;
    @FXML
    public TableColumn colBegin;
    @FXML
    public TableColumn colDuration;
    @FXML
    public TableColumn colBuilding;
    @FXML
    public TableColumn colRoomNumber;
    @FXML
    public TableColumn colTrialNumber;
    @FXML
    public TableColumn colSubjectNumberInsisted;
    @FXML
    public TableColumn coltechnicalNameInsisted;


    private ObservableList<Exam> exams = FXCollections.observableArrayList(new Exam("test", "testname", "3", "2019-04-12", "9.00", "2:00", "R0.23", "G1", "1", "2.3", false, false)
            , new Exam("Analysis", "Mathe", "3", "2019-04-12", "9.00", "2:00", "R0.23", "G1", "1", "2.3", false, false));

    private ObservableList<Exam> examsInsisted = FXCollections.observableArrayList(new Exam("GDM", "Mathe", "3", "2019-04-12", "9.00", "2:00", "R0.23", "G1", "1", "2.3", false, false));

    /**
     * the method deletes an element Exam from selected TableView.
     *
     * @param actionEvent Element Exam which should be added to the ArrayList
     */

    public void clickDeleteExam(ActionEvent actionEvent) {

        ObservableList<Exam> selectedItems = tableviewExams.getSelectionModel().getSelectedItems();
        exams.removeAll(selectedItems);
    }


    /**
     * the method adds an element Exam to TableView.
     *
     * @param actionEvent Element Exam which should be added to the ArrayList
     */

    public void clickAddExam(ActionEvent actionEvent) {

        String examNumber = textfieldLectureNumber.getText();
        addExam(examNumber);
        textfieldLectureNumber.clear();
    }


    /**
     * the method deletes all elements Exam from selected TableView
     *
     * @param actionEvent Element Exam which should be added to the ArrayList
     */

    public void clickClearList(ActionEvent actionEvent) {

        tableviewExams.getItems().clear();
    }


    /**
     * the method adds an element Exam to examList.
     */

    private void addExam(String examNumber) {

        exams.add(new Exam(examNumber));
    }


    /**
     * Increases the value of the TrialNumber property of the Exam class by 1.
     */

    public void secondTrialExam() {

        ObservableList<Integer> selectedItems = tableviewExams.getSelectionModel().getSelectedIndices();
        for (int examIndex : selectedItems) {
            Exam exam = exams.get(examIndex);
            String trNr = exam.getTrialNumber();
            Integer trialNr = Integer.parseInt(trNr);
            trialNr++;
            trNr = "" + trialNr;
            exam.setTrialNumber(new SimpleStringProperty(trNr));
            tableviewExams.refresh();
        }
    }

    /**
     * Deletes the element from the list exams and adds it to the list examsInsisted.
     */

    public void addElementToTableviewExamsInsisted() {
        ObservableList<Exam> selectedItems = tableviewExams.getSelectionModel().getSelectedItems();
        examsInsisted.addAll(selectedItems);
        exams.removeAll(selectedItems);
        tableviewExamsInsisted.refresh();
    }


    /**
     * the method deletes all elements Exam from selected TableView
     *
     * @param url            Element Exam which should be added to the ArrayList
     * @param resourceBundle Element Exam which should be added to the ArrayList
     */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {


        tableviewExams.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableviewExams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colSubjectNumber.setCellValueFactory((new PropertyValueFactory<Exam, String>("subjectNumber")));
        colSubjectNumber.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        coltechnicalName.setCellValueFactory(new PropertyValueFactory<Exam, String>("technicalName"));
        coltechnicalName.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colSemester.setCellValueFactory(new PropertyValueFactory<Exam, String>("semester"));
        colSemester.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colDate.setCellValueFactory(new PropertyValueFactory<Exam, String>("date"));
        colDate.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colBegin.setCellValueFactory(new PropertyValueFactory<Exam, String>("begin"));
        colBegin.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colDuration.setCellValueFactory(new PropertyValueFactory<Exam, String>("duration"));
        colDuration.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colBuilding.setCellValueFactory(new PropertyValueFactory<Exam, String>("building"));
        colBuilding.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colRoomNumber.setCellValueFactory(new PropertyValueFactory<Exam, String>("roomNumber"));
        colRoomNumber.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colTrialNumber.setCellValueFactory(new PropertyValueFactory<Exam, String>("trialNumber"));
        colTrialNumber.setMaxWidth(1f * Integer.MAX_VALUE * 50);

        colSubjectNumberInsisted.setCellValueFactory((new PropertyValueFactory<Exam, String>("subjectNumber")));
        colSubjectNumberInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        coltechnicalNameInsisted.setCellValueFactory(new PropertyValueFactory<Exam, String>("technicalName"));
        coltechnicalNameInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);

        tableviewExams.setItems(exams);
        tableviewExamsInsisted.setItems(examsInsisted);
    }
}
