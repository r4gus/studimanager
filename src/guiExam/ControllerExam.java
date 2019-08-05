package guiExam;

import custom_exceptions.UserException;
import exam.Exam;
import guiExam.EditWindow.ControllerEditWindow;
import guiExam.EditWindowExamResult.ControllerEditWindowExamResult;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
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
    @FXML
    public TableColumn colMarkInsisted;
    @FXML
    public TableColumn colModulMarkInsisted;
    @FXML
    public TableColumn colTrialsInsisted;


    @FXML
    public ChoiceBox<String> stringChoiceBoxTableView;

    private static final String choiceBoxValue1 = "Aktuelle Klausuren";
    private static final String choiceBoxValue2 = "Bestandene Klausuren";

    private static final String pathControllerEditWindowLesson = "EditWindow/layoutEditWindow.fxml";
    private static final String pathControllerEditWindowLessonInsisted = "EditWindowExamResult/layoutEditWindowExamResult.fxml";

    private ObservableList<Exam> exams = FXCollections.observableArrayList(new Exam("test", "testname", "3", "2019-04-12", "9.00", "2:00", "R0.23", "G1", "1", "2.3", "2.0", false, false)
            , new Exam("Analysis", "Mathe", "3", "2019-04-12", "9.00", "1:30", "R0.23", "G1", "1", "2.3", "3.0", false, false));


    private ObservableList<Exam> examsInsisted = FXCollections.observableArrayList(new Exam("GDM", "Mathe", "3", "2019-04-12", "9.00", "2:00", "R0.23", "G1", "1", "2.3", "2,5", true, false));
    private ObservableList<String> observableListChoiceBox = FXCollections.observableArrayList(ControllerExam.choiceBoxValue1, ControllerExam.choiceBoxValue2);


    /**
     * the method deletes element(s) Exam from selected TableView(s).
     *
     * @param actionEvent This event type is widely used to represent a variety of things, such as when a Button has been fired, when a KeyFrame has finished, and other such usages.
     */

    public void clickDeleteExam(ActionEvent actionEvent) {

        boolean tableViewExam;
        ObservableList<Exam> selectedItems = tableviewExams.getSelectionModel().getSelectedItems();
        if (selectedItems.isEmpty()) {
            selectedItems = tableviewExamsInsisted.getSelectionModel().getSelectedItems();
            tableViewExam = false;
        } else {
            tableViewExam = true;
        }

        if (!tableviewExams.getSelectionModel().getSelectedItems().isEmpty() && !tableviewExamsInsisted.getSelectionModel().getSelectedItems().isEmpty()) {
            ObservableList<Exam> selectedItems1 = tableviewExamsInsisted.getSelectionModel().getSelectedItems();
            deleteTableViewItems(selectedItems);
            deleteTableViewItemsInsisted(selectedItems1);
            return;
        }
        if (selectedItems.size() == 0) {
            try {
                throw new UserException("Bitte wählen Sie eine Klausur aus die Sie löschen möchten.");
            } catch (UserException e) {
                showInformationAltertForUser(e.getMessage());
                return;
            }
        }
        if (tableViewExam) {
            deleteTableViewItems(selectedItems);
        } else if (!tableViewExam) {
            deleteTableViewItemsInsisted(selectedItems);
        }
    }

    /**
     * the method removes element(s) Exam from TableView.
     *
     * @param selectedItems This parameter contains a list of Exam items to be deleted from TableView.
     */

    private void deleteTableViewItems(ObservableList<Exam> selectedItems) {
        exams.removeAll(selectedItems);
        tableviewExams.getSelectionModel().clearSelection();
    }


    /**
     * the method removes element(s) Exam from TableView.
     *
     * @param selectedItems This parameter contains a list of Exam items to be deleted from TableView.
     */

    private void deleteTableViewItemsInsisted(ObservableList<Exam> selectedItems) {
        examsInsisted.removeAll(selectedItems);
        tableviewExamsInsisted.getSelectionModel().clearSelection();
    }


    /**
     * the method adds an element Exam to TableView.
     *
     * @param actionEvent This event type is widely used to represent a variety of things, such as when a Button has been fired, when a KeyFrame has finished, and other such usages.
     */

    public void clickAddExam(ActionEvent actionEvent) {

        String examNumber = textfieldLectureNumber.getText();
        if (examNumber.trim().isEmpty()) {
            try {
                throw new UserException("Sie müssen eine Klausurnummer in das Textfeld eingeben");
            } catch (UserException e) {
                showInformationAltertForUser(e.getMessage());
            }

        } else {
            addExam(examNumber);
            textfieldLectureNumber.clear();
        }
    }


    /**
     * the method deletes all elements Exam from selected TableView
     *
     * @param actionEvent This event type is widely used to represent a variety of things, such as when a Button has been fired, when a KeyFrame has finished, and other such usages.
     */

    public void clickClearList(ActionEvent actionEvent) {

        try {

            if (stringChoiceBoxTableView.getValue() == null) {
                throw new UserException("Bitte wählen Sie im Dropdownmenü eine der verfügung stehenden Optionen aus!");
            }
            if (stringChoiceBoxTableView.getValue().equals(ControllerExam.choiceBoxValue1) && tableviewExams.getItems() != null) {
                tableviewExams.getItems().clear();
            } else if (stringChoiceBoxTableView.getValue().equals(ControllerExam.choiceBoxValue2) && tableviewExamsInsisted.getItems() != null) {
                tableviewExamsInsisted.getItems().clear();
            }
        } catch (UserException e) {
            /* remember Exception logs automatically */
            showInformationAltertForUser(e.getMessage());
        }
    }


    /**
     * This method displays an error message to the user with understandable content,
     * so that the user can correct the error if necessary.
     */

    private void showInformationAltertForUser(String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("User Information");
        alert.setHeaderText("Fehlermeldung:");
        alert.setContentText(content);

        // Das auszuwählende Element farblich hervorheben ??? oder anders kentlich machen ??
        alert.showAndWait();
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

        if (selectedItems.isEmpty()) {
            try {
                throw new UserException("Bitte wählen Sie eine Klausur aus, bei wecher die Versuchnummer erhöht werden soll.");
            } catch (UserException e) {
                showInformationAltertForUser(e.getMessage());
                return;
            }
        }

        for (int examIndex : selectedItems) {
            Exam exam = exams.get(examIndex);
            String trNr = exam.getTrialNumber();
            Integer trialNr = Integer.parseInt(trNr);
            trialNr++;
            trNr = "" + trialNr;
            exam.setTrialNumber(new SimpleStringProperty(trNr));
            tableviewExams.refresh();
            tableviewExams.getSelectionModel().clearSelection();
        }
    }

    /**
     * Deletes the element from the list exams and adds it to the list examsInsisted.
     */

    public void addElementToTableviewExamsInsisted() {

        ObservableList<Exam> selectedItems = tableviewExams.getSelectionModel().getSelectedItems();
        for (Exam e : selectedItems) {
            e.setInsisted(true);
        }
        if (selectedItems.isEmpty()) {
            try {
                throw new UserException("Bitte Wählen Sie eine Klausur aus");
            } catch (UserException ex) {
                showInformationAltertForUser(ex.getMessage());
            }
        }
        examsInsisted.addAll(selectedItems);
        exams.removeAll(selectedItems);
        tableviewExamsInsisted.refresh();
        tableviewExams.getSelectionModel().clearSelection();
    }


    /**
     * This method decides which Fxml file is to be loaded with the corresponding controller
     * based on various actions of the user.
     * If the selection is incorrect, the user will be informed about it.
     */

    public void editExamObject() {

        if (tableviewExams.getSelectionModel().getSelectedItem() != null && tableviewExamsInsisted.getSelectionModel().getSelectedItem() != null) {

            showInformationAltertForUser("Sie können immer nur eine Klausur bearbeiten! \nBitte wählen Sie jetzt die gewüschte Klausur aus, \ndie Sie bearbeiten möchten.");
            tableviewExams.getSelectionModel().clearSelection();
            tableviewExamsInsisted.getSelectionModel().clearSelection();
            return;
        }

        Exam exam;
        exam = tableviewExams.getSelectionModel().getSelectedItem();
        tableviewExams.getSelectionModel().clearSelection();
        if (exam == null) {
            exam = tableviewExamsInsisted.getSelectionModel().getSelectedItem();
            tableviewExamsInsisted.getSelectionModel().clearSelection();
        }
        if (exam == null) {
            try {
                throw new UserException("Bitte wählen Sie eine Klausur aus die Sie bearbeiten möchten.");
            } catch (UserException e) {
                showInformationAltertForUser(e.getMessage());
                return;
            }
        }
        if (!exam.isInsisted()) {
            ControllerEditWindow controllerLesson = new ControllerEditWindow(this, exam);
            loadWindowEditExams(exam, pathControllerEditWindowLesson, controllerLesson);

        } else if (exam.isInsisted()) {
            ControllerEditWindowExamResult controllerLessonInsisted = new ControllerEditWindowExamResult(this, exam);
            loadWindowEditExams(exam, pathControllerEditWindowLessonInsisted, controllerLessonInsisted);
        }
    }


    /**
     * This method creates new windows using the appropriate parameters.
     *
     * @param exam       This is the exam object to be displayed in the window.
     * @param path       This is the storage path to the corresponding FXML file.
     * @param controller This is the corresponding controller for FXML file.
     */

    private void loadWindowEditExams(Exam exam, String path, Object controller) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource(path));
            fxmlLoader.setController(controller);
            Parent root = fxmlLoader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setTitle("Klausurdaten anpassen");
            stage.show();
        } catch (IOException ex) {

            //.... loggen usw....
        }
    }


    /**
     * Called to initialize a controller after its root element has been completely processed
     *
     * @param url            The location used to resolve relative paths for the root object, or null if the location is not known.
     * @param resourceBundle The resources used to localize the root object, or null if the root object was not localized.
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

        tableviewExamsInsisted.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        tableviewExamsInsisted.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        colSubjectNumberInsisted.setCellValueFactory((new PropertyValueFactory<Exam, String>("subjectNumber")));
        colSubjectNumberInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        coltechnicalNameInsisted.setCellValueFactory(new PropertyValueFactory<Exam, String>("technicalName"));
        coltechnicalNameInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colMarkInsisted.setCellValueFactory(new PropertyValueFactory<Exam, String>("mark"));
        colMarkInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colModulMarkInsisted.setCellValueFactory(new PropertyValueFactory<Exam, String>("modulMark"));
        colModulMarkInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);
        colTrialsInsisted.setCellValueFactory(new PropertyValueFactory<Exam, String>("trialNumber"));
        colTrialsInsisted.setMaxWidth(1f * Integer.MAX_VALUE * 50);

        tableviewExams.setItems(exams);
        tableviewExamsInsisted.setItems(examsInsisted);
        stringChoiceBoxTableView.setItems(observableListChoiceBox);
    }
}
