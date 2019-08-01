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
    public TableColumn colExamNumber;
    @FXML
    public TableColumn colLecture;
    @FXML
    public TableColumn colSemester;
    @FXML
    public TableColumn colDate;
    @FXML
    public TableColumn colStart;
    @FXML
    public TableColumn colDuration;
    @FXML
    public TableColumn colRoom;

    private ObservableList<Exam> exams = FXCollections.observableArrayList(new Exam("15304"));

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


    }
}
