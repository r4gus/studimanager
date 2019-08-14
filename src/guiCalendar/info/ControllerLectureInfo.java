package guiCalendar.info;

import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.edit.ControllerLectureEdit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import sample.Main;
import timetable.Lecture;
import timetable.Lectures;
import timetable.Note;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ControllerLectureInfo implements Initializable {

    @FXML
    private ScrollPane li_scrollPane;

    @FXML
    private GridPane li_gridPane;

    private Lectures lectures;

    private static final String colHeadlines[] = {"Title", "Facility", "Lecturer", "Is Elective", "Notes"};

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane.setTopAnchor(li_scrollPane, 8.0);
        AnchorPane.setBottomAnchor(li_scrollPane, 8.0);
        AnchorPane.setLeftAnchor(li_scrollPane, 8.0);
        AnchorPane.setRightAnchor(li_scrollPane, 8.0);

        adjustGridPane(li_gridPane);

        /**
         * Run populateGrid() on the JavaFX Application Thread at some time in the future.
         * Gives {@link #setLectures(Lectures)} time to set the lectures member.
         */
        Platform.runLater(() -> {
            populateGrid(li_gridPane, this.lectures);
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

        gridPane.setVgap(3); // vertical space between elements
        gridPane.setHgap(5); // horizontal space between elements
        gridPane.setAlignment(Pos.TOP_LEFT); // alignment of the GridPane

        MyLogger.LOGGER.exiting(getClass().toString(), "adjustGridPane");
    }

    /**
     * Populates the specified GridPane based on the lectures object passed.
     * @param gridPane GridPane object to populate
     * @param lectures Object to get the information from
     */
    private void populateGrid(GridPane gridPane, Lectures lectures) {
        MyLogger.LOGGER.entering(getClass().toString(), "populateGrid", new Object[]{gridPane, lectures});

        if(lectures == null) {
            MyLogger.LOGGER.log(Level.SEVERE, "Null pointer passed to populateGrid().");
            /*
            handle exception
             */
            return;
        }

        /*
        Add headings to the grid
         */
        for(int i = 0; i < colHeadlines.length; i++) {
            Text t = new Text(colHeadlines[i]);
            t.setFont(new Font(ControllerCalendar.MEDIUM_FONT_SIZE));

            gridPane.setHalignment(t, HPos.CENTER);
            gridPane.add(t, i, 0);
        }

        for(int i = 0; i < lectures.getSize(); i++) {
            Lecture lecture = lectures.getElement(i);

            // title
            VBox titleVBox = new VBox();
            Text title = new Text(lecture.getTitle());
            title.setFont(new Font(ControllerCalendar.MEDIUM_FONT_SIZE));
            title.setStyle("-fx-font-weight: bold");

            Button editButton = new Button("edit");
            editButton.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent actionEvent) {
                    try {
                        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/edit/layoutLectureEdit.fxml"));
                        Parent root = loader.load();

                        // get controller
                        ControllerLectureEdit controllerLectureEdit = loader.getController();
                        // pass lecture object
                        /*
                        code goes here
                         */

                        // show edit-form
                        Stage stage = new Stage();
                        stage.setScene(new Scene(root));
                        stage.setTitle("edit");

                        // prevent interaction with the primary stage until the new window is closed
                        stage.initModality(Modality.WINDOW_MODAL);
                        stage.initOwner(gridPane.getScene().getWindow());    // must be adjusted
                        // show window
                        stage.show();
                    } catch (IOException exc) {
                        exc.printStackTrace();
                    }
                }
            });

            titleVBox.setAlignment(Pos.CENTER);
            titleVBox.getChildren().addAll(title, editButton);

            // facility
            TreeView<String> facility = new TreeView<String>();
            TreeItem<String> facilityRootItem = new TreeItem<>(lecture.getFacility().toString());
            TreeItem<String> t_building = new TreeItem<>("Building: " + lecture.getFacility().getBuilding());
            TreeItem<String> t_room = new TreeItem<>("Room: " + lecture.getFacility().getRoom());
            TreeItem<String> t_street = new TreeItem<>("Street: " + lecture.getFacility().getStreet());
            TreeItem<String> t_zipcode = new TreeItem<>("Zip-Code: " + lecture.getFacility().getZipcode());
            TreeItem<String> t_city = new TreeItem<>("City: " + lecture.getFacility().getCity());
            facilityRootItem.getChildren().addAll(t_building, t_room, t_street, t_zipcode, t_city);
            facility.setRoot(facilityRootItem);

            // lecturer
            TreeView<String> lecturer = new TreeView<>();
            TreeItem<String> lecturerRootItem = new TreeItem<>(lecture.getLecturer().toString());
            TreeItem<String> t_firstName = new TreeItem<>("First Name: " + lecture.getLecturer().getFirstName());
            TreeItem<String> t_lastName = new TreeItem<>("Last Name: " + lecture.getLecturer().getLastName());
            TreeItem<String> t_email = new TreeItem<>("E-Mail: " + lecture.getLecturer().getEmail());
            TreeItem<String> t_facility = new TreeItem<>("Facility : " + lecture.getLecturer().getFacility().toString());
            TreeItem<String> tt_building = new TreeItem<>("Building: " + lecture.getLecturer().getFacility().getBuilding());
            TreeItem<String> tt_room = new TreeItem<>("Room: " + lecture.getLecturer().getFacility().getRoom());
            TreeItem<String> tt_street = new TreeItem<>("Street: " + lecture.getLecturer().getFacility().getStreet());
            TreeItem<String> tt_zipcode = new TreeItem<>("Zip-Code: " + lecture.getLecturer().getFacility().getZipcode());
            TreeItem<String> tt_city = new TreeItem<>("City: " + lecture.getLecturer().getFacility().getCity());
            t_facility.getChildren().addAll(tt_building, tt_room, tt_street, tt_zipcode, tt_city);
            lecturerRootItem.getChildren().addAll(t_firstName, t_lastName, t_email, t_facility);
            lecturer.setRoot(lecturerRootItem);

            // elective
            Text elective = (lecture.isElective() ? new Text("true") : new Text("false"));
            gridPane.setHalignment(elective, HPos.CENTER);

            // notes
            TreeView<String> notes = new TreeView<>();
            TreeItem<String> notesRootItem = new TreeItem<>("Notes");
            for(int j = 0; j < lecture.getNotes().size(); j++) {
                Note note = lecture.getNotes().getElement(j);

                TreeItem<String> t_title = new TreeItem<>(note.getTitle());
                TreeItem<String> t_body = new TreeItem<>(note.getBody());
                t_title.getChildren().add(t_body);
                notesRootItem.getChildren().add(t_title);
            }
            notes.setRoot(notesRootItem);
            notes.setShowRoot(false);

            gridPane.addRow(i+1, titleVBox, facility, lecturer, elective, notes);
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "populateGrid");
    }

    /**
     * Used to pass the specified {@link Lectures} object to the controller. This method is invoked
     * after the call to initialize() hence initialize() must wait for this method to complete before
     * it can continue populating the grid.
     * @param lectures Lectures object to assign
     */
    public void setLectures(Lectures lectures) {
        this.lectures = lectures;
    }
}
