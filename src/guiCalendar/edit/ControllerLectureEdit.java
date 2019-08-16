package guiCalendar.edit;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Window;
import logging.MyLogger;
import timetable.Facility;
import timetable.Lecture;
import timetable.Lecturer;
import timetable.Timetable;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * @author David Sugar
 */
public class ControllerLectureEdit implements Initializable {

    @FXML
    private GridPane edit_grid;

    private Lecture lecture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        edit_grid.getStylesheets().add(getClass().getResource("edit.css").toExternalForm());

        adjustGridPane(edit_grid);

        /**
         * Run {@code populateGrid} on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@link #setLecture(Lecture)}
         */
        Platform.runLater(() -> {

            GridPane gridPane = makeForm(edit_grid, lecture);
        });


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

    private GridPane makeForm(GridPane gridPane, Lecture lecture) {

        Text sceneTitle = new Text("Edit: ...");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- TITLE -------------------------------------
         */
        Label lectureTitle = new Label("Title:");
        gridPane.add(lectureTitle, 0, 1);
        TextField titleField = new TextField();
        titleField.setText(lecture.getTitle());
        gridPane.add(titleField, 1, 1);

        /*
        -------------- ELECTIVE --------------------------------
         */
        Label lectureIsElective = new Label("Elective:");
        gridPane.add(lectureIsElective, 0, 2);
        CheckBox isElectiveBox = new CheckBox("yes");
        isElectiveBox.setSelected(lecture.isElective());
        gridPane.add(isElectiveBox, 1, 2);

        /*
        ------------- FACILITY ---------------------------------
         */
        Label lectureFacility = new Label("Facility:");
        gridPane.add(lectureFacility, 0, 3);

        ComboBox<Facility> facilityComboBox = new ComboBox<>();
        for (int i = 0; i < Timetable.getFACILITIES().getSize(); i++) {                  // add already existing facilities
            Facility facility = Timetable.getFACILITIES().getElement(i);                // as choices to the ComboBox

            facilityComboBox.getItems().add(facility);

            // select the currently assigned facility
            if (lecture.getFacility() != null && lecture.getFacility().equals(facility)) {
                facilityComboBox.getSelectionModel().select(i);
            }
        }
        gridPane.add(facilityComboBox, 1, 3);

        Button addFacilityButton = new Button("add");
        addFacilityButton.getStyleClass().addAll("add-button", "add-button:hover");
        gridPane.add(addFacilityButton, 2, 3);

        /*
        -------------- LECTURER --------------------------------
         */
        Label lectureLecturer = new Label("Lecturer:");
        gridPane.add(lectureLecturer, 0, 4);

        ComboBox<Lecturer> lecturerComboBox = new ComboBox<>();
        for (int i = 0; i < Timetable.getLECTURERS().getSize(); i++) {                   // add already existing lecturers
            Lecturer lecturer = Timetable.getLECTURERS().getElement(i);                 // as choices to the ComboBox

            lecturerComboBox.getItems().add(lecturer);

            if (lecture.getLecturer() != null && lecture.getLecturer().equals(lecturer)) {
                lecturerComboBox.getSelectionModel().select(i);
            }
        }
        gridPane.add(lecturerComboBox, 1, 4);

        Button addLecturerButton = new Button("add");
        addLecturerButton.getStyleClass().addAll("add-button", "add-button:hover");
        gridPane.add(addLecturerButton, 2, 4);

        /*
        ------------- SUBMIT BUTTON --------------------------------
         */

        Button submitButton = new Button("submit");

        submitButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String title;
                boolean isElective;
                Facility facility = null;
                Lecturer lecturer = null;

                /*
                ------------- GET VALUES ----------------------------------------------
                 */
                if (titleField.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error",
                            "Please enter a title");
                }

                isElective = isElectiveBox.isSelected();

                if (facilityComboBox.getValue() != null) {
                    facility = facilityComboBox.getValue();
                }

                if (lecturerComboBox.getValue() != null) {
                    lecturer = lecturerComboBox.getValue();
                }

                /*
                ------------------- INSTANTIATE LECTURE --------------------------------
                 */

            }
        });

        gridPane.add(submitButton, 0, 5, 3, 1);


        return gridPane;
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }

    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }
}
