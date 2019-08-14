package guiCalendar.info;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import sample.Main;
import timetable.Facility;
import timetable.Lecture;
import timetable.Lectures;
import timetable.Note;

import java.util.logging.Level;

/**
 * @author David Sugar
 */
public class LectureInfo extends Application {

    private Lectures lectures;

    /**
     * Creates a new window that displays information about all {@link Lectures} assigned to
     * a specific unit. The main window isn't responsive while this window is open.
     * @param infoStage
     */
    public void start(Stage infoStage) {
        MyLogger.LOGGER.entering(getClass().toString(), "start", infoStage);
        // prevents interaction with the primary stage
        // until the new window is closed.
        infoStage.initModality(Modality.WINDOW_MODAL);
        infoStage.initOwner(Main.getPrimaryStage());

        // set position of the second window, relative to the position of
        // the primary window.
        infoStage.setX(Main.getPrimaryStage().getX() + 200);
        infoStage.setY(Main.getPrimaryStage().getY() + 200);

        AnchorPane root = new AnchorPane();
        ScrollPane scrollPane = new ScrollPane();
        GridPane gridPane = new GridPane();

        scrollPane.fitToWidthProperty().set(true);
        scrollPane.fitToHeightProperty().set(true);


        AnchorPane.setTopAnchor(scrollPane, 8.0);
        AnchorPane.setBottomAnchor(scrollPane, 8.0);
        AnchorPane.setLeftAnchor(scrollPane, 8.0);
        AnchorPane.setRightAnchor(scrollPane, 8.0);

        scrollPane.setContent(gridPane);
        root.getChildren().add(scrollPane);

        showLectures(gridPane);

        infoStage.setScene(new Scene(root, 500, 300));

        MyLogger.LOGGER.exiting(getClass().toString(), "start");
    }

    private void showLectures(GridPane gridPane) {
        MyLogger.LOGGER.entering(getClass().toString(), "showLectures", gridPane);
        if( gridPane == null || lectures == null ) {
            MyLogger.LOGGER.log(Level.WARNING, "showLectures can't reference null.");
            return;
        }

        gridPane.setAlignment(Pos.TOP_CENTER);

        Text titleColumn = new Text("Title");
        Text facilityColumn = new Text("Facility");
        Text lecturerColumn = new Text("Lecturer");
        Text electiveColumn = new Text("Is Elective");
        Text notesColumn = new Text("Notes");

        gridPane.addRow(0, titleColumn, facilityColumn, lecturerColumn, electiveColumn, notesColumn);

        for(int i = 0; i < lectures.getSize(); i++) {
            Lecture lecture = lectures.getElement(i);

            // title
            Text title = new Text(lecture.getTitle());

            // facility
            TreeView facility = new TreeView();
            TreeItem facilityRootItem = new TreeItem(lecture.getFacility().toString());
            TreeItem t_building = new TreeItem("Building: " + lecture.getFacility().getBuilding());
            TreeItem t_room = new TreeItem("Room: " + lecture.getFacility().getRoom());
            TreeItem t_street = new TreeItem("Street: " + lecture.getFacility().getStreet());
            TreeItem t_zipcode = new TreeItem("Zip-Code: " + lecture.getFacility().getZipcode());
            TreeItem t_city = new TreeItem("City: " + lecture.getFacility().getCity());
            facilityRootItem.getChildren().addAll(t_building, t_room, t_street, t_zipcode, t_city);
            facility.setRoot(facilityRootItem);

            // lecturer
            TreeView lecturer = new TreeView();
            TreeItem lecturerRootItem = new TreeItem(lecture.getLecturer().toString());
            TreeItem t_firstName = new TreeItem("First Name: " + lecture.getLecturer().getFirstName());
            TreeItem t_lastName = new TreeItem("Last Name: " + lecture.getLecturer().getLastName());
            TreeItem t_email = new TreeItem("E-Mail: " + lecture.getLecturer().getEmail());
            TreeItem t_facility = new TreeItem("Facility : " + lecture.getLecturer().getFacility().toString());
            TreeItem tt_building = new TreeItem("Building: " + lecture.getLecturer().getFacility().getBuilding());
            TreeItem tt_room = new TreeItem("Room: " + lecture.getLecturer().getFacility().getRoom());
            TreeItem tt_street = new TreeItem("Street: " + lecture.getLecturer().getFacility().getStreet());
            TreeItem tt_zipcode = new TreeItem("Zip-Code: " + lecture.getLecturer().getFacility().getZipcode());
            TreeItem tt_city = new TreeItem("City: " + lecture.getLecturer().getFacility().getCity());
            t_facility.getChildren().addAll(tt_building, tt_room, tt_street, tt_zipcode, tt_city);
            lecturerRootItem.getChildren().addAll(t_firstName, t_lastName, t_email, t_facility);
            lecturer.setRoot(lecturerRootItem);

            // elective
            Text elective = (lecture.isElective() ? new Text("true") : new Text("false"));

            // notes
            TreeView notes = new TreeView();
            TreeItem notesRootItem = new TreeItem("Notes");
            for(int j = 0; j < lecture.getNotes().size(); j++) {
                Note note = lecture.getNotes().getElement(j);

                TreeItem t_title = new TreeItem(note.getTitle());
                TreeItem t_body = new TreeItem(note.getBody());
                t_title.getChildren().add(t_body);
                notesRootItem.getChildren().add(t_title);
            }
            notes.setRoot(notesRootItem);
            notes.setShowRoot(false);

            gridPane.addRow(i+1, title, facility, lecturer, elective, notes);
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "showLectures");
    }

    /**
     * Must be invoked before {@link #start(Stage)} can be called. Otherwise start can lead
     * to undefined behaviour.
     * @param lectures
     */
    public void setLectures(Lectures lectures) {
        MyLogger.LOGGER.entering(getClass().toString(), "setLectures", lectures);

        this.lectures = lectures;

        MyLogger.LOGGER.exiting(getClass().toString(), "setLectures");
    }
}
