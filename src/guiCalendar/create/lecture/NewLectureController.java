package guiCalendar.create.lecture;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.facility.NewFacilityController;
import guiCalendar.create.lecturer.NewLecturerController;
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
import javafx.stage.Window;
import logging.MyLogger;
import sample.Main;
import timetable.*;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static message.Alert.showAlert;

public class NewLectureController implements Initializable, Updatable {
    private final Timetable timetable = ControllerCalendar.getTimetable();
    @FXML
    private GridPane newLecture_grid;
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

        newLecture_grid.getStylesheets().add(getClass().getResource("../../../main.css").toExternalForm());

        adjustGridPane(newLecture_grid);

        /**
         * Run {@code populateGrid} on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@link #setLecture(Lecture)}
         */
        Platform.runLater(() -> {
            update();
        });


    }

    public void update() {
        makeForm(newLecture_grid, unit);
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

    private void makeForm(GridPane gridPane, Lectures unit) {
        MyLogger.LOGGER.entering(getClass().toString(), "makeForm", new Object[]{gridPane, unit});

        Text sceneTitle = new Text(Main.getTimetableBundle().getString("New") + " " + Main.getTimetableBundle().getString("Lecture"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- TITLE -------------------------------------
         */
        Label lectureTitle = new Label(Main.getTimetableBundle().getString("Title") + ":");
        gridPane.add(lectureTitle, 0, 1);
        TextField titleField = new TextField();
        if (preservedTitle != null) titleField.setText(preservedTitle);
        gridPane.add(titleField, 1, 1);

        /*
        -------------- ELECTIVE --------------------------------
         */
        Label lectureIsElective = new Label(Main.getTimetableBundle().getString("Elective") + ":");
        gridPane.add(lectureIsElective, 0, 2);
        CheckBox isElectiveBox = new CheckBox(Main.getTimetableBundle().getString("Yes"));
        isElectiveBox.setSelected(preservedElective);
        gridPane.add(isElectiveBox, 1, 2);

        /*
        ------------- FACILITY ---------------------------------
         */
        Label lectureFacility = new Label(Main.getTimetableBundle().getString("Facility") + ":");
        gridPane.add(lectureFacility, 0, 3);

        ComboBox<Facility> facilityComboBox = new ComboBox<>();
        for (int i = 0; i < timetable.getFACILITIES().getSize(); i++) {                  // add already existing facilities
            Facility facility = timetable.getFACILITIES().getElement(i);                // as choices to the ComboBox

            facilityComboBox.getItems().add(facility);
        }
        if (preservedFacility != null) facilityComboBox.getSelectionModel().select(preservedFacility);
        gridPane.add(facilityComboBox, 1, 3);



        /*
        -------------- LECTURER --------------------------------
         */
        Label lectureLecturer = new Label(Main.getTimetableBundle().getString("Lecturer") + ":");
        gridPane.add(lectureLecturer, 0, 4);

        ComboBox<Lecturer> lecturerComboBox = new ComboBox<>();
        for (int i = 0; i < timetable.getLECTURERS().getSize(); i++) {                   // add already existing lecturers
            Lecturer lecturer = timetable.getLECTURERS().getElement(i);                 // as choices to the ComboBox

            lecturerComboBox.getItems().add(lecturer);
        }
        if (preservedLecturer != null) lecturerComboBox.getSelectionModel().select(preservedLecturer);
        gridPane.add(lecturerComboBox, 1, 4);


        /*
        ------------- SUBMIT BUTTON --------------------------------
         */

        Button createButton = new Button(Main.getTimetableBundle().getString("Create"));

        createButton.setOnAction(new EventHandler<ActionEvent>() {
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
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error",
                            "Please enter a title");
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
                ------------------- CREATE LECTURE --------------------------------
                 */

                try {
                    unit.addLecture(timetable.newLecture(title, facility, lecturer, isElective, null));
                } catch (IllegalArgumentException exc) {
                    /*
                    error message
                     */
                }

                /*
                ------------------ CLOSE WINDOW -----------------------------------
                 */

                Stage stage = (Stage) gridPane.getScene().getWindow();
                parentController.update();
                stage.close();
            }
        });
        gridPane.add(createButton, 0, 5, 3, 1);

        /*
        ---------------------------- NEW FACILITY BUTTON ---------------------------------
         */
        Button addFacilityButton = new Button(Main.getTimetableBundle().getString("New"));
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
                    stage.setTitle(Main.getTimetableBundle().getString("New") + " " + Main.getTimetableBundle().getString("Facility"));

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
        --------------------- NEW LECTURER BUTTON --------------------------------
         */

        Button addLecturerButton = new Button(Main.getTimetableBundle().getString("New"));
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
                    stage.setTitle(Main.getTimetableBundle().getString("New") + " " + Main.getTimetableBundle().getString("Lecturer"));

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


        MyLogger.LOGGER.exiting(getClass().toString(), "makeForm");
    }

    public void setUnit(Lectures unit) {
        this.unit = unit;
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }
}
