package guiCalendar.edit;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.facility.NewFacilityController;
import guiCalendar.create.lecturer.NewLecturerController;
import input.elements.combobox.ComboBox;
import input.elements.textfield.TextField;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import Main.Main;
import timetable.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author David Sugar
 */
public class ControllerLectureEdit implements Initializable, Updatable {

    @FXML
    private GridPane edit_grid;

    private Lecture lecture;
    private final Timetable timetable = ControllerCalendar.getTimetable();
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

        edit_grid.getStylesheets().add(getClass().getResource("../../main.css").toExternalForm());

        adjustGridPane(edit_grid);

        /**
         * Run {@code populateGrid} on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@link #setLecture(Lecture)}
         */
        Platform.runLater(() -> {
            preservedTitle = lecture.getTitle();
            preservedElective = lecture.isElective();
            preservedFacility = lecture.getFacility();
            preservedLecturer = lecture.getLecturer();
            update();
        });


    }

    public void update() {
        makeForm(edit_grid, lecture);
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

    private void makeForm(GridPane gridPane, Lecture lecture) {

        Text sceneTitle = new Text(Main.getBundle().getString("Edit") + ":");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- TITLE -------------------------------------
         */
        Label lectureTitle = new Label(Main.getBundle().getString("Title") + ":");
        gridPane.add(lectureTitle, 0, 1);

        TextField titleField = new TextField();
        titleField.setText(preservedTitle);
        gridPane.add(titleField, 1, 1);

        /*
        -------------- ELECTIVE --------------------------------
         */
        Label lectureIsElective = new Label(Main.getBundle().getString("Elective") + ":");
        gridPane.add(lectureIsElective, 0, 2);
        CheckBox isElectiveBox = new CheckBox(Main.getBundle().getString("Yes"));
        isElectiveBox.setSelected(preservedElective);
        gridPane.add(isElectiveBox, 1, 2);

        /*
        ------------- FACILITY ---------------------------------
         */
        Label lectureFacility = new Label(Main.getBundle().getString("Facility") + ":");
        gridPane.add(lectureFacility, 0, 3);

        input.elements.combobox.ComboBox<Facility> facilityComboBox = new input.elements.combobox.ComboBox<>();
        for (int i = 0; i < timetable.getFACILITIES().getSize(); i++) {                  // add already existing facilities
            Facility facility = timetable.getFACILITIES().getElement(i);                // as choices to the ComboBox

            facilityComboBox.getItems().add(facility);

        }
        facilityComboBox.getSelectionModel().select(preservedFacility);
        gridPane.add(facilityComboBox, 1, 3);


        /*
        -------------- LECTURER --------------------------------
         */
        Label lectureLecturer = new Label(Main.getBundle().getString("Lecturer") + ":");
        gridPane.add(lectureLecturer, 0, 4);

        ComboBox<Lecturer> lecturerComboBox = new ComboBox<>();
        for (int i = 0; i < timetable.getLECTURERS().getSize(); i++) {                   // add already existing lecturers
            Lecturer lecturer = timetable.getLECTURERS().getElement(i);                 // as choices to the ComboBox

            lecturerComboBox.getItems().add(lecturer);

        }
        lecturerComboBox.getSelectionModel().select(preservedLecturer);
        gridPane.add(lecturerComboBox, 1, 4);


        /*
        ------------- SUBMIT BUTTON --------------------------------
         */

        Button submitButton = new Button(Main.getBundle().getString("Submit"));

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String title = null;
                boolean isElective;
                Facility facility = null;
                Lecturer lecturer = null;

                /*
                ------------- GET VALUES ----------------------------------------------
                 */
                if (titleField.getText().isEmpty()) {
                    titleField.showError(Main.getBundle().getString("EnterTitle"));
                    return;
                } else {
                    title = titleField.getText();
                }

                isElective = isElectiveBox.isSelected();

                if (facilityComboBox.getValue() != null) {
                    facility = facilityComboBox.getValue();
                }

                if (lecturerComboBox.getValue() != null) {
                    lecturer = lecturerComboBox.getValue();
                }

                /*
                ------------------- UPDATE LECTURE --------------------------------
                 */

                lecture.setTitle(title);

                lecture.setFacility(facility);

                lecture.setLecturer(lecturer);

                lecture.setElective(isElective);

                /*
                ------------------ CLOSE WINDOW -----------------------------------
                 */

                Stage stage = (Stage) gridPane.getScene().getWindow();
                parentController.update();
                stage.close();
            }
        });

        gridPane.add(submitButton, 0, 5, 3, 1);

        /*
        -------------------------- NEW FACILITY BUTTON ------------------------------
         */
        Button addFacilityButton = new Button(Main.getBundle().getString("New"));
        addFacilityButton.getStyleClass().addAll("add-button", "add-button:hover");
        Updatable parent = this;
        addFacilityButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // load info-page scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/facility/layoutNewFacility.fxml"));
                    Parent root = loader.load();

                    /*------------- PRESERVE ALREADY ENTERED FIELDS ------------------------ */
                    preservedTitle = titleField.getText();
                    preservedElective = isElectiveBox.isSelected();
                    preservedFacility = facilityComboBox.getValue();
                    preservedLecturer = lecturerComboBox.getValue();

                    // get controller
                    NewFacilityController newFacilityController = loader.getController();
                    // pass Lectures object
                    newFacilityController.setParentController(parent);

                    // show info-page scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("New") + " " + Main.getBundle().getString("Facility"));

                    // prevents interaction with the primary stage until the new window is closed.
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(gridPane.getScene().getWindow());
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });
        gridPane.add(addFacilityButton, 2, 3);

        /*
        ------------------------ NEW LECTURER BUTTON --------------------------------------
         */
        Button addLecturerButton = new Button(Main.getBundle().getString("New"));
        addLecturerButton.getStyleClass().addAll("add-button", "add-button:hover");
        addLecturerButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    // load info-page scene
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/lecturer/layoutNewLecturer.fxml"));
                    Parent root = loader.load();

                    /*------------- PRESERVE ALREADY ENTERED FIELDS ------------------------ */
                    preservedTitle = titleField.getText();
                    preservedElective = isElectiveBox.isSelected();
                    preservedFacility = facilityComboBox.getValue();
                    preservedLecturer = lecturerComboBox.getValue();

                    // get controller
                    NewLecturerController newLecturerController = loader.getController();
                    // pass Lectures object
                    newLecturerController.setParentController(parent);

                    // show info-page scene
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("New") + " " + Main.getBundle().getString("Lecturer"));

                    // prevents interaction with the primary stage until the new window is closed.
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(gridPane.getScene().getWindow());
                    stage.setResizable(false);
                    stage.show();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        });

        gridPane.add(addLecturerButton, 2, 4);


    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

    public void setUnit(Lectures unit) {
        this.unit = unit;
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }
}
