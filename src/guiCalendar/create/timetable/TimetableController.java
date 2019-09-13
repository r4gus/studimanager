package guiCalendar.create.timetable;

import input.elements.textfield.AlphaNumTextField;
import guiCalendar.calendar.ControllerCalendar;
import input.elements.ComboBoxFactory;
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
import sample.Main;
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

        Text sceneTitle = new Text(Main.getBundle().getString("New") + " " + Main.getBundle().getString("Timetable"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        sceneTitle.setStyle("-fx-font-weight: bold");
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- SEMESTER -------------------------------------
         */
        Label semesterTitle = new Label(Main.getBundle().getString("Semester") + ":");
        gridPane.add(semesterTitle, 0, 1);

//        TextField semesterTextfield = TextFieldFactory.getIntTextField(3, true);
        AlphaNumTextField semesterTextfield = new AlphaNumTextField(5);

        semesterTextfield.setPrefSize(80.0, 30.0);
        gridPane.add(semesterTextfield, 1, 1);

        /*
        -------------- DAYS --------------------------------------
         */
        Label daysLabel = new Label(Main.getBundle().getString("Days") + ":");
        gridPane.add(daysLabel, 0, 2);

        input.elements.combobox.ComboBox<Integer> daysComboBox = new input.elements.combobox.ComboBox<>();
        daysComboBox.getItems().add(3);
        daysComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(daysComboBox, 1, 2);

        Separator separator1 = new Separator(Orientation.HORIZONTAL);
        gridPane.add(separator1, 0, 3, 4, 1);


        /*
        -------------- LESSONS --------------------------------------
         */
        Label lessonsLabel = new Label(Main.getBundle().getString("Lessons") + ":");
        gridPane.add(lessonsLabel, 0, 4);

        ComboBox<Integer> lessonsComboBox = ComboBoxFactory.getIntComboBox(1, Timetable.MAX_UNITS);
        lessonsComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lessonsComboBox, 1, 4);

         /*
        -------------- LESSONS --------------------------------------
         */
        Label beginLabel = new Label(Main.getBundle().getString("Begin") + ":");
        gridPane.add(beginLabel, 0, 5);

        ComboBox<Integer> beginHourComboBox = ComboBoxFactory.getIntComboBox(0, 23);
        beginHourComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(beginHourComboBox, 1, 5);

        ComboBox<Integer> beginMinuteComboBox = ComboBoxFactory.getIntComboBox(0, 59);
        beginMinuteComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(beginMinuteComboBox, 2, 5);

        Label hM = new Label(Main.getBundle().getString("Hour") + "/ " + Main.getBundle().getString("Minute"));
        gridPane.add(hM, 3, 5);

        /*
        --------------------- LESSON DURATION ------------------------
         */
        Label lessonDurationLabel = new Label(Main.getBundle().getString("Duration"));
        gridPane.add(lessonDurationLabel, 0, 6);

        ComboBox<Integer> lessonDurationComboBox = ComboBoxFactory.getIntComboBox(1, 180);
        lessonDurationComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lessonDurationComboBox, 1, 6);

        Label lessonDurationMiutesLabel = new Label(Main.getBundle().getString("Minute"));
        gridPane.add(lessonDurationMiutesLabel, 3, 6);

        /*
        -------------------- BREAK TIME ---------------------------------
         */
        Label breakTimeLabel = new Label(Main.getBundle().getString("Breaktime"));
        gridPane.add(breakTimeLabel, 0, 7);

        ComboBox<Integer> breakTimeComboBox = ComboBoxFactory.getIntComboBox(1, 180);
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

        ComboBox<Integer> lunchHComboBox = ComboBoxFactory.getIntComboBox(0, 23);
        lunchHComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lunchHComboBox, 1, 9);

        ComboBox<Integer> lunchMComboBox = ComboBoxFactory.getIntComboBox(0, 59);
        lunchMComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lunchMComboBox, 2, 9);

        Label lunchHM = new Label(Main.getBundle().getString("Hour") + "/ " + Main.getBundle().getString("Minute"));
        gridPane.add(lunchHM, 3, 9);

        /*
        ------------------ LUNCH DURATION ------------------------------------
         */
        Label lunchDuration = new Label(Main.getBundle().getString("Duration"));
        gridPane.add(lunchDuration, 0, 10);

        ComboBox<Integer> lunchDurationComboBox = ComboBoxFactory.getIntComboBox(1, 180);
        lunchDurationComboBox.setPrefSize(80.0, 30.0);
        gridPane.add(lunchDurationComboBox, 1, 10);

        Label lunchDurationHM = new Label(Main.getBundle().getString("Minute"));
        gridPane.add(lunchDurationHM, 3, 10);

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
                semesterTextfield.showError("Lol du Dödel");
                allValid = false;
            }

            // DAYS
            if(!daysComboBox.getSelectionModel().isEmpty())
                days = daysComboBox.getValue();
            else {
                daysComboBox.showError("hello world");
                allValid = false;
            }

            // LESSONS
            if(!lessonsComboBox.getSelectionModel().isEmpty())
                lessons = lessonsComboBox.getValue();
            else {
                lessonsComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            //BEGIN
            if(!beginHourComboBox.getSelectionModel().isEmpty())
                beginH = beginHourComboBox.getValue();
            else {
                beginHourComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            if(!beginMinuteComboBox.getSelectionModel().isEmpty())
                beginM = beginMinuteComboBox.getValue();
            else {
                beginMinuteComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            if(!lessonDurationComboBox.getSelectionModel().isEmpty())
                duration = lessonDurationComboBox.getValue();
            else {
                lessonDurationComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            // BREAK
            if(!breakTimeComboBox.getSelectionModel().isEmpty())
                breakTime = breakTimeComboBox.getValue();
            else {
                breakTimeComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            if(!lunchHComboBox.getSelectionModel().isEmpty())
                lunchTimeH = lunchHComboBox.getValue();
            else {
                lunchHComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            if(!lunchMComboBox.getSelectionModel().isEmpty())
                lunchTimeM = lunchMComboBox.getValue();
            else {
                lunchMComboBox.setStyle("-fx-border-color: red");
                allValid = false;
            }

            if(!lunchDurationComboBox.getSelectionModel().isEmpty())
                lunchTime = lunchDurationComboBox.getValue();
            else {
                lunchDurationComboBox.setStyle("-fx-border-color: red");
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
                    Parent root = FXMLLoader.load(getClass().getResource("../../../sample/" + Main.fxml));
                    primaryStage.setTitle(Main.TITLE);
                    primaryStage.setScene(new Scene(root, Main.WIDTH, Main.HEIGHT));
                    primaryStage.show();

                    /*
                    ---------------- Close this and the parent window --------------------------------------
                    */
                    Stage stage = (Stage) gridPane.getScene().getWindow();
                    parent.close();
                    stage.close();
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
