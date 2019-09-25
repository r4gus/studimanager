package guiCalendar.create.timetable;

import config.Language;
import input.elements.combobox.ComboBox;
import guiCalendar.calendar.ControllerCalendar;
import input.elements.textfield.IntTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logging.MyLogger;
import Main.Main;
import timetable.Timetable;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class TimetableController implements Initializable {

    @FXML
    private GridPane newTimetable_grid;

    @FXML
    private Text sceneTitle;

    @FXML
    private Label semesterTitle;

    @FXML
    private IntTextField semesterTextField;

    @FXML
    private Label daysLabel;

    @FXML
    private ComboBox<Integer> daysComboBox;

    @FXML
    private Label lessonsLabel;

    @FXML
    private ComboBox<Integer> lessonsComboBox;

    @FXML
    private Label beginLabel;

    @FXML
    private ComboBox<Integer> beginHourComboBox;

    @FXML
    private ComboBox<Integer> beginMinuteComboBox;

    @FXML
    private Label hm;

    @FXML
    private Label lessonDurationLabel;

    @FXML
    private ComboBox<Integer> lessonDurationComboBox;

    @FXML
    private Label lessonDurationMinutesLabel;

    @FXML
    private Label breakTimeLabel;

    @FXML
    private ComboBox<Integer> breakTimeComboBox;

    @FXML
    private Label breakTimeMinutesLabel;

    @FXML
    private Label lunchLabel;

    @FXML
    private ComboBox<Integer> lunchHComboBox;

    @FXML
    private ComboBox<Integer> lunchMComboBox;

    @FXML
    private Label lunchHM;

    @FXML
    private Label lunchDurationLabel;

    @FXML
    private ComboBox<Integer> lunchDurationComboBox;

    @FXML
    private Label lunchDurationHM;

    @FXML
    private Button createButton;

    @FXML
    private Button cancelButton;

    private Stage parent;

    public void setParent(Stage parent) {
        this.parent = parent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newTimetable_grid.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
        setLanguage();
        populateBegComboBoxes();
        populateBreakTimeComboBox();
        populateDaysComboBox();
        populateLessonDurationComboBox();
        populateLessonsComboBox();
        populateLunchComboBoxes();
        populateLunchDurationComboBox();
        setDefaultValues();
    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        sceneTitle.setText(Main.getBundle().getString("NewTimetable"));
        semesterTitle.setText(Main.getBundle().getString("Semester") + ":");
        daysLabel.setText(Main.getBundle().getString("Days") + ":");
        lessonsLabel.setText(Main.getBundle().getString("Lessons") + ":");
        beginLabel.setText(Main.getBundle().getString("Begin") + ":");
        hm.setText(Main.getBundle().getString("Hour") + "/ " + Main.getBundle().getString("Minute"));
        lessonDurationLabel.setText(Main.getBundle().getString("Duration") + ":");
        lessonDurationMinutesLabel.setText(Main.getBundle().getString("Minute"));
        breakTimeLabel.setText(Main.getBundle().getString("Breaktime"));
        breakTimeMinutesLabel.setText(Main.getBundle().getString("Minute"));
        lunchLabel.setText(Main.getBundle().getString("Lunchtime") + ":");
        lunchHM.setText(Main.getBundle().getString("Hour") + "/ " + Main.getBundle().getString("Minute"));
        lunchDurationLabel.setText(Main.getBundle().getString("Duration"));
        lunchDurationHM.setText(Main.getBundle().getString("Minute"));
        createButton.setText(Main.getBundle().getString("Create"));
        cancelButton.setText(Main.getBundle().getString("Cancel"));
    }

    private void populateDaysComboBox() {
        for(int i = 1; i <= 7; i++) {
            daysComboBox.getItems().add(i);
        }
    }

    private void populateLessonsComboBox() {
        for(int i = 1; i <= 7; i++) {
            lessonsComboBox.getItems().add(i);
        }
    }

    private void populateBegComboBoxes() {
        for(int i = 6; i <= 15; i++) {
            beginHourComboBox.getItems().add(i);
        }

        for(int i = 0; i <= 59; i += 15) {
            beginMinuteComboBox.getItems().add(i);
        }
    }

    private void populateLessonDurationComboBox() {
        for(int i = 45; i <= 120; i += 15) {
            lessonDurationComboBox.getItems().add(i);
        }
    }

    private void populateBreakTimeComboBox() {
        for(int i = 5; i <= 30; i += 5) {
            breakTimeComboBox.getItems().add(i);
        }
    }

    private void populateLunchComboBoxes() {
        for(int i = 11; i <= 14; i++) {
            lunchHComboBox.getItems().add(i);
        }

        for(int i = 0; i <= 45; i += 15) {
            lunchMComboBox.getItems().add(i);
        }
    }

    private void populateLunchDurationComboBox() {
        for(int i = 15; i <= 90; i += 15) {
            lunchDurationComboBox.getItems().add(i);
        }
    }

    private void setDefaultValues() {
        /*
        #############################################################################
        #                            DEFAULT VALUES                                 #
        #############################################################################
         */
        daysComboBox.setValue(6); // Mo - Sa
        lessonsComboBox.setValue(6);
        beginHourComboBox.setValue(8);
        beginMinuteComboBox.setValue(0);
        lessonDurationComboBox.setValue(90);
        breakTimeComboBox.setValue(15);
        lunchHComboBox.setValue(13);
        lunchMComboBox.setValue(0);
        lunchDurationComboBox.setValue(60);

    }

    @FXML
    public void handleCancelButtonAction() {
        Stage stage = (Stage) newTimetable_grid.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void handleCreateButtonAction() {
        int semester = 0, days = 0, lessons = 0, beginH = 0, beginM = 0, duration = 0,
                breakTime = 0, lunchTimeH = 0, lunchTimeM = 0, lunchTime = 0;
        boolean allValid = true;

        /* #################### VALIDATE/ GET INPUT ############################## */
        // SEMESTER
        if(!semesterTextField.getText().isEmpty())
            semester = Integer.parseInt(semesterTextField.getText());
        else {
            semesterTextField.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        // DAYS
        if(!daysComboBox.getSelectionModel().isEmpty())
            days = daysComboBox.getValue();
        else {
            daysComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        // LESSONS
        if(!lessonsComboBox.getSelectionModel().isEmpty())
            lessons = lessonsComboBox.getValue();
        else {
            lessonsComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        //BEGIN
        if(!beginHourComboBox.getSelectionModel().isEmpty())
            beginH = beginHourComboBox.getValue();
        else {
            beginHourComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        if(!beginMinuteComboBox.getSelectionModel().isEmpty())
            beginM = beginMinuteComboBox.getValue();
        else {
            beginMinuteComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        if(!lessonDurationComboBox.getSelectionModel().isEmpty())
            duration = lessonDurationComboBox.getValue();
        else {
            lessonDurationComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        // BREAK
        if(!breakTimeComboBox.getSelectionModel().isEmpty())
            breakTime = breakTimeComboBox.getValue();
        else {
            breakTimeComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        if(!lunchHComboBox.getSelectionModel().isEmpty())
            lunchTimeH = lunchHComboBox.getValue();
        else {
            lunchHComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        if(!lunchMComboBox.getSelectionModel().isEmpty())
            lunchTimeM = lunchMComboBox.getValue();
        else {
            lunchMComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        if(!lunchDurationComboBox.getSelectionModel().isEmpty())
            lunchTime = lunchDurationComboBox.getValue();
        else {
            lunchDurationComboBox.showError(Main.getBundle().getString("NeedInput"));
            allValid = false;
        }

        if(allValid) {
            Timetable timetable = new Timetable(lessons, semester, beginH, beginM, duration, breakTime, lunchTime,
                    lunchTimeH, lunchTimeM, days);
            ControllerCalendar.setTimetable(timetable);

            try {
                /*
                 --------------- Show primary stage ------------------------------------
                 */
                Stage primaryStage = new Stage();
                Parent root = FXMLLoader.load(getClass().getResource("/Main/" + Main.fxml));
                primaryStage.setTitle(Main.TITLE);
                primaryStage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
                primaryStage.show();

                    /*
                    ---------------- Close this and the parent window --------------------------------------
                    */
                Stage stage = (Stage) newTimetable_grid.getScene().getWindow();
                parent.close();
                stage.close();
                Main.getConfig().setTimetablePath(""); // reset the timetable path
            } catch (IOException exc) {
                MyLogger.LOGGER.log(Level.SEVERE, "Couldn't create new Timetable" + "\n" + exc.getMessage());
                exc.printStackTrace();
            }
        }
    }

}
