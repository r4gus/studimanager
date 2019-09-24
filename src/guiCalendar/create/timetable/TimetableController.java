package guiCalendar.create.timetable;

import input.elements.combobox.ComboBox;
import guiCalendar.calendar.ControllerCalendar;
import input.elements.textfield.IntTextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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

    private Stage parent;

    public void setParent(Stage parent) {
        this.parent = parent;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newTimetable_grid.getStylesheets().add(getClass().getResource("../../../main.css").toExternalForm());

        adjustGridPane(newTimetable_grid);

        makeForm(newTimetable_grid);
    }

    /**
     * Setup the specified {@link GridPane}. All adjustments to the gridPane should be made within this method to
     * keep everything at one place.
     *
     * @param gridPane The {@link GridPane} to adjust.
     */
    private void adjustGridPane(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "adjustGridPane", gridPane);

        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new Insets(25, 25, 25, 25));
        gridPane.setAlignment(Pos.CENTER);

        MyLogger.LOGGER.exiting(getClass().toString(), "adjustGridPane");
    }

    private void makeForm(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "makeForm", gridPane);

        Text sceneTitle = new Text(Main.getBundle().getString("NewTimetable"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        sceneTitle.setStyle("-fx-font-weight: bold");
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- SEMESTER -------------------------------------
         */
        Label semesterTitle = new Label(Main.getBundle().getString("Semester") + ":");
        gridPane.add(semesterTitle, 0, 1);

        IntTextField semesterTextfield = new IntTextField(2, false);
        semesterTextfield.setPrefSize(80.0, 30.0);
        gridPane.add(semesterTextfield, 1, 1);

        /*
        -------------- DAYS --------------------------------------
         */
        Label daysLabel = new Label(Main.getBundle().getString("Days") + ":");
        gridPane.add(daysLabel, 0, 2);

        input.elements.combobox.ComboBox<Integer> daysComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 1; i <= 7; i++) {
            daysComboBox.getItems().add(i);
        }
        daysComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(daysComboBox, 1, 2);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        gridPane.add(separator1, 0, 3, 4, 1);


        /*
        -------------- LESSONS --------------------------------------
         */
        Label lessonsLabel = new Label(Main.getBundle().getString("Lessons") + ":");
        gridPane.add(lessonsLabel, 0, 4);

        input.elements.combobox.ComboBox<Integer> lessonsComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 1; i <= 7; i++) {
            lessonsComboBox.getItems().add(i);
        }
        lessonsComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lessonsComboBox, 1, 4);

         /*
        -------------- LESSONS --------------------------------------
         */
        Label beginLabel = new Label(Main.getBundle().getString("Begin") + ":");
        gridPane.add(beginLabel, 0, 5);

        input.elements.combobox.ComboBox<Integer> beginHourComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 6; i <= 15; i++) {
            beginHourComboBox.getItems().add(i);
        }
        beginHourComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(beginHourComboBox, 1, 5);

        input.elements.combobox.ComboBox<Integer> beginMinuteComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 0; i <= 59; i += 15) {
            beginMinuteComboBox.getItems().add(i);
        }
        beginMinuteComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(beginMinuteComboBox, 2, 5);

        Label hM = new Label(Main.getBundle().getString("Hour") + "/ " + Main.getBundle().getString("Minute"));
        gridPane.add(hM, 3, 5);

        /*
        --------------------- LESSON DURATION ------------------------
         */
        Label lessonDurationLabel = new Label(Main.getBundle().getString("Duration"));
        gridPane.add(lessonDurationLabel, 0, 6);

        input.elements.combobox.ComboBox<Integer> lessonDurationComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 45; i <= 120; i += 15) {
            lessonDurationComboBox.getItems().add(i);
        }
        lessonDurationComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lessonDurationComboBox, 1, 6);

        Label lessonDurationMiutesLabel = new Label(Main.getBundle().getString("Minute"));
        gridPane.add(lessonDurationMiutesLabel, 3, 6);

        /*
        -------------------- BREAK TIME ---------------------------------
         */
        Label breakTimeLabel = new Label(Main.getBundle().getString("Breaktime"));
        gridPane.add(breakTimeLabel, 0, 7);

        input.elements.combobox.ComboBox<Integer> breakTimeComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 5; i <= 30; i += 5) {
            breakTimeComboBox.getItems().add(i);
        }
        breakTimeComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(breakTimeComboBox, 1, 7);

        Label breakTimeMiutesLabel = new Label(Main.getBundle().getString("Minute"));
        gridPane.add(breakTimeMiutesLabel, 3, 7);

        Separator separator2 = new Separator(Orientation.HORIZONTAL);
        gridPane.add(separator2, 0, 8, 4, 1);

        /*
        ------------------- LUNCH TIME ----------------------------------
         */
        Label lunchLabel = new Label(Main.getBundle().getString("Lunchtime") + ":");
        gridPane.add(lunchLabel, 0, 9);

        input.elements.combobox.ComboBox<Integer> lunchHComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 11; i <= 14; i++) {
            lunchHComboBox.getItems().add(i);
        }
        lunchHComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lunchHComboBox, 1, 9);

        input.elements.combobox.ComboBox<Integer> lunchMComboBox = new input.elements.combobox.ComboBox<>();
        for(int i = 0; i <= 45; i += 15) {
            lunchMComboBox.getItems().add(i);
        }
        lunchMComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lunchMComboBox, 2, 9);

        Label lunchHM = new Label(Main.getBundle().getString("Hour") + "/ " + Main.getBundle().getString("Minute"));
        gridPane.add(lunchHM, 3, 9);

        /*
        ------------------ LUNCH DURATION ------------------------------------
         */
        Label lunchDuration = new Label(Main.getBundle().getString("Duration"));
        gridPane.add(lunchDuration, 0, 10);

        ComboBox<Integer> lunchDurationComboBox = new ComboBox<>();
        for(int i = 15; i <= 90; i += 15) {
            lunchDurationComboBox.getItems().add(i);
        }
        lunchDurationComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lunchDurationComboBox, 1, 10);

        Label lunchDurationHM = new Label(Main.getBundle().getString("Minute"));
        gridPane.add(lunchDurationHM, 3, 10);

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


        /*
        ----------------------- BUTTONS --------------------------------------------
         */
        Button createButton = new Button(Main.getBundle().getString("Create"));
        Button cancelButton = new Button(Main.getBundle().getString("Cancel"));

        cancelButton.setOnAction(e -> {
            Stage stage = (Stage) gridPane.getScene().getWindow();
            stage.close();
        });

        createButton.setOnAction(e -> {
            int semester = 0, days = 0, lessons = 0, beginH = 0, beginM = 0, duration = 0,
                    breakTime = 0, lunchTimeH = 0, lunchTimeM = 0, lunchTime = 0;
            boolean allValid = true;

            /* #################### VALIDATE/ GET INPUT ############################## */
            // SEMESTER
            if(!semesterTextfield.getText().isEmpty())
                semester = Integer.parseInt((String) semesterTextfield.getText());
            else {
                semesterTextfield.showError(Main.getBundle().getString("NeedInput"));
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
                    Parent root = FXMLLoader.load(getClass().getResource("../../../Main/" + Main.fxml));
                    primaryStage.setTitle(Main.TITLE);
                    primaryStage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
                    primaryStage.show();

                    /*
                    ---------------- Close this and the parent window --------------------------------------
                    */
                    Stage stage = (Stage) gridPane.getScene().getWindow();
                    parent.close();
                    stage.close();
                    Main.getConfig().setTimetablePath(""); // reset the timetable path
                } catch (IOException exc) {
                    MyLogger.LOGGER.log(Level.SEVERE, "Couldn't create new Timetable" + "\n" + exc.getMessage());
                    exc.printStackTrace();
                }
            }
        });


        gridPane.add(createButton, 1, 12);
        gridPane.add(cancelButton, 2, 12);



        MyLogger.LOGGER.exiting(getClass().toString(), "makeForm");
    }
}
