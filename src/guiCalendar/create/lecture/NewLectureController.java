package guiCalendar.create.lecture;

import config.Language;
import guiCalendar.IFacility;
import guiCalendar.ILecturer;
import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.facility.NewFacilityController;
import guiCalendar.create.lecturer.NewLecturerController;
import input.elements.combobox.ComboBox;
import input.elements.textfield.TextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import Main.Main;
import message.Notification;
import timetable.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class NewLectureController implements Initializable, IFacility, ILecturer {
    private final Timetable timetable = ControllerCalendar.getTimetable();

    @FXML
    private GridPane newLecture_grid;

    @FXML
    private Text newLectureTitle;

    @FXML
    private Label lectureTitle;

    @FXML
    private TextField lectureTitleTextField;

    @FXML
    private Label isElective;

    @FXML
    private CheckBox isElectiveBox;

    @FXML
    private Label facilityTitle;

    @FXML
    private ComboBox<Facility> facilityComboBox;

    @FXML
    private Button newFacility;

    @FXML
    private Label lecturerTitle;

    @FXML
    private ComboBox<Lecturer> lecturerComboBox;

    @FXML
    private Button create;

    @FXML
    private Button newLecturer;

    private Lectures unit;

    private Updatable parentController = null;

    /* ################## PRESERVED TEXT-FIELDS ########################### */
    private String preservedTitle = null;
    private boolean preservedElective = false;
    private Facility preservedFacility = null;
    private Lecturer preservedLecturer = null;
    /* ##################################################################### */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newLecture_grid.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
        setLanguage();
        populateFacilityComboBox();
        populateLecturerComboBox();
        update();
    }

    public void update() {
        setFields();
    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        newLectureTitle.setText(Main.getBundle().getString("NewLecture"));
        lectureTitle.setText(Main.getBundle().getString("Lecture") + ":");
        isElective.setText(Main.getBundle().getString("Elective") + ":");
        facilityTitle.setText(Main.getBundle().getString("Facility") + ":");
        lecturerTitle.setText(Main.getBundle().getString("Lecturer") + ":");
        newFacility.setText(Main.getBundle().getString("New"));
        create.setText(Main.getBundle().getString("Create"));
        newLecturer.setText(Main.getBundle().getString("New"));
    }

    private void setFields() {
        if (preservedTitle != null) lectureTitleTextField.setText(preservedTitle);
        isElectiveBox.setSelected(preservedElective);
        if (preservedFacility != null) facilityComboBox.getSelectionModel().select(preservedFacility);
        if (preservedLecturer != null) lecturerComboBox.getSelectionModel().select(preservedLecturer);
    }

    private void populateFacilityComboBox() {
        for (int i = 0; i < timetable.getFACILITIES().getSize(); i++) {                  // add already existing facilities
            Facility facility = timetable.getFACILITIES().getElement(i);                // as choices to the ComboBox
            facilityComboBox.getItems().add(facility);
        }
    }

    private void populateLecturerComboBox() {
        for (int i = 0; i < timetable.getLECTURERS().getSize(); i++) {                   // add already existing lecturers
            Lecturer lecturer = timetable.getLECTURERS().getElement(i);                 // as choices to the ComboBox
            lecturerComboBox.getItems().add(lecturer);
        }
    }

    @FXML
    public void handleCreateButtonAction(ActionEvent actionEvent) {
        String title;
        boolean isElective;
        Facility facility = null;
        Lecturer lecturer = null;

        if (lectureTitleTextField.getText().isEmpty()) {
            lectureTitleTextField.showError(Main.getBundle().getString("EnterTitle"));
            return;
        } else {
            title = lectureTitleTextField.getText();
        }

        isElective = isElectiveBox.isSelected();

        if (facilityComboBox.getValue() != null) {
            facility = facilityComboBox.getValue();
        }

        if (lecturerComboBox.getValue() != null) {
            lecturer = lecturerComboBox.getValue();
        }

        try {
            if(!unit.addLecture(timetable.newLecture(title, facility, lecturer, isElective, Color.WHITE, null))) {
                Notification.showInfo(Main.getBundle().getString("Info"),
                        Main.getBundle().getString("DoesAlreadyExist"),
                        Main.getPrimaryStage());
            }
            parentController.update();
        } catch (IllegalArgumentException exc) {
            MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage());
        } finally {
            Stage stage = (Stage) newLecture_grid.getScene().getWindow();
            stage.close();
        }
    }

    @FXML
    public void handleNewFacilityButtonAction(ActionEvent actionEvent) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/facility/layoutNewFacility.fxml"));
            Parent root = loader.load();

            /*------------- PRESERVE ALREADY ENTERED FIELDS ------------------------ */
            preservedTitle = lectureTitleTextField.getText();
            preservedElective = isElectiveBox.isSelected();
            preservedFacility = facilityComboBox.getValue();
            preservedLecturer = lecturerComboBox.getValue();

            NewFacilityController newFacilityController = loader.getController();
            newFacilityController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(Main.getBundle().getString("NewFacility"));

            // prevents interaction with the primary stage until the new window is closed.
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(newLecture_grid.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            MyLogger.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    @FXML
    public void handleNewLecturerButtonAction(ActionEvent actionEvent) {
        try {
            // load info-page scene
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/lecturer/layoutNewLecturer.fxml"));
            Parent root = loader.load();

            /*------------- PRESERVE ALREADY ENTERED FIELDS ------------------------ */
            preservedTitle = lectureTitleTextField.getText();
            preservedElective = isElectiveBox.isSelected();
            preservedFacility = facilityComboBox.getValue();
            preservedLecturer = lecturerComboBox.getValue();

            NewLecturerController newLecturerController = loader.getController();
            newLecturerController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(Main.getBundle().getString("NewLecturer"));

            // prevents interaction with the primary stage until the new window is closed.
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(newLecture_grid.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            MyLogger.LOGGER.log(Level.SEVERE, e.getMessage());
        }
    }

    public void setUnit(Lectures unit) {
        this.unit = unit;
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }

    @Override
    public void setFacility(Facility facility) {
        this.preservedFacility = facility;
        this.facilityComboBox.getItems().add(facility);
    }

    @Override
    public void setLecturer(Lecturer lecturer) {
        this.preservedLecturer = lecturer;
        this.lecturerComboBox.getItems().add(lecturer);
    }
}
