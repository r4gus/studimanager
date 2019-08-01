package guiExam;

import exam.Exam;
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

public class ControllerExam implements Initializable {

    @FXML
    public TextField textfieldLectureNumber;
    @FXML
    public TableView<Exam> tableviewExams;
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


    private ObservableList<Exam> exams = FXCollections.observableArrayList(new Exam("test" , "testname", "3" , "2019-04-12", "9.00" , "2:00" , "R0.23" , "G1", "1" , "2.3" , false ,false ));

    public void clickDeleteExam(ActionEvent actionEvent) {

        ObservableList<Exam> selectedItems = tableviewExams.getSelectionModel().getSelectedItems();
        exams.removeAll(selectedItems);
    }

    public void clickAddExam(ActionEvent actionEvent) {

        String examNumber = textfieldLectureNumber.getText();
        addExam(examNumber);
        textfieldLectureNumber.clear();
    }

    public void clickClearList(ActionEvent actionEvent) {

        tableviewExams.getItems().clear();
    }

    private void addExam(String examNumber) {

        exams.add(new Exam(examNumber));
    }


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

        tableviewExams.setItems(exams);


    }
}
