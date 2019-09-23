package guiCalendar.info;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.lecture.NewLectureController;
import guiCalendar.create.lecture.SelectLectureController;
import guiCalendar.edit.ControllerLectureEdit;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import logging.MyLogger;
import message.Notification;
import sample.Main;
import timetable.Lecture;
import timetable.Lectures;
import timetable.Note;

import javax.management.NotificationFilter;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class ControllerLectureInfo implements Initializable, Updatable {

    @FXML
    private ScrollPane li_scrollPane;

    @FXML AnchorPane li_anchorPane;

    private Lectures lectures;

    private static final String colHeadlines[] = {"Facility", "Lecturer"};

    private Updatable parentController = null;

    public Window getWindow() {
        return li_anchorPane.getScene().getWindow();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane.setTopAnchor(li_scrollPane, 8.0);
        AnchorPane.setBottomAnchor(li_scrollPane, 8.0);
        AnchorPane.setLeftAnchor(li_scrollPane, 8.0);
        AnchorPane.setRightAnchor(li_scrollPane, 8.0);

        li_scrollPane.getStylesheets().add(getClass().getResource("../../main.css").toExternalForm());

        li_anchorPane.getStyleClass().add("background-color");

        /**
         * Run makeAddButton and makeLectureAccordion on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@code #setLectures(Lectures)}.
         */
        Platform.runLater(() -> {
            update();
        });
    }

    /**
     * Add all content to the scenes root node.
     * <p>
     * Every time the scene is updated, the ControllerCalender scene is also updated.
     */
    public void update() {
        MyLogger.LOGGER.entering(getClass().toString(), "update");

        if (this.lectures != null) {
            VBox vBox = new VBox();
            vBox.setSpacing(10);

            vBox.getChildren().addAll(makeLectureAccordion(lectures), makeAddButton(lectures));
            li_scrollPane.setContent(vBox);

            /*
            -------------- UPDATE MAIN WINDOW -----------------------------------------
             */
            parentController.update();
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "update");
    }

    private MenuButton makeAddButton(Lectures unit) {
        MenuItem newButton = new MenuItem(Main.getBundle().getString("New"));
        MenuItem existingButton = new MenuItem(Main.getBundle().getString("Existing"));

        ControllerLectureInfo parent = this;
        newButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/lecture/layoutNewLecture.fxml"));
                    Parent root = loader.load();

                    // get controller
                    NewLectureController newLectureController = loader.getController();
                    // pass lecture object
                    newLectureController.setUnit(unit);
                    newLectureController.setParentController(parent);

                    // show edit-form
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("New"));

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(li_scrollPane.getScene().getWindow());
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException exc) {
                    MyLogger.LOGGER.log(Level.SEVERE, "Couldn't open 'New Lecture' window.\n" + exc.getMessage());
                    Notification.showAlertWindow(Alert.AlertType.ERROR, li_anchorPane.getScene().getWindow(),
                            "Oops", Main.getBundle().getString("Oops"));
                }
            }
        });

        existingButton.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/lecture/layoutSelectLecture.fxml"));
                Parent root = loader.load();

                // get controller
                SelectLectureController selectLectureController = loader.getController();
                // pass lecture object
                selectLectureController.setUnit(unit);
                selectLectureController.setParentController(parent);

                // show edit-form
                Stage stage = new Stage();
                stage.setScene(new Scene(root));
                stage.setTitle(Main.getBundle().getString("Select"));

                // prevent interaction with the primary stage until the new window is closed
                stage.initModality(Modality.WINDOW_MODAL);
                stage.initOwner(li_scrollPane.getScene().getWindow());
                stage.setResizable(false);
                // show window
                stage.show();
            } catch (IOException exc) {
                MyLogger.LOGGER.log(Level.SEVERE, "Couldn't open 'Select Lecture' window.\n" + exc.getMessage());
                Notification.showAlertWindow(Alert.AlertType.ERROR, li_anchorPane.getScene().getWindow(),
                        "Oops", Main.getBundle().getString("Oops"));
            }
        });

        MenuButton menuButton = new MenuButton(Main.getBundle().getString("Add"), null, newButton, existingButton);

        menuButton.getStyleClass().addAll("add-button", "add-button:hover");

        return menuButton;
    }

    /**
     * Creates and returns an JavaFx {@code Accordion} object that holds all lectures of a unit and information
     * about them.
     * @param unit The {@link Lectures} object to process.
     * @return An {@code Accordion} object containing all information about the specified unit.
     */
    private Accordion makeLectureAccordion(Lectures unit) {
        MyLogger.LOGGER.entering(getClass().toString(), "makeLectureAccordion", unit);

        Accordion accordion = new Accordion();

        for (Lecture lecture : lectures.getContainer()) {
            TitledPane pane = new TitledPane(lecture.getTitle(), makeLectureGrid(lecture, unit));
            accordion.getPanes().add(pane);
        }

        MyLogger.LOGGER.exiting(getClass().toString(), "makeLectureAccordion", accordion);
        return accordion;
    }

    /**
     * Creates and returns a JavaFx {@cod GridPane} object that contains all relevant information about a {@link Lecture}.
     * @param lecture The {@code Lecture} object to process.
     * @return GridPane object.
     */
    private GridPane makeLectureGrid(Lecture lecture, Lectures unit) {
        MyLogger.LOGGER.entering(getClass().toString(), "makeLectureGrid", lecture);

        GridPane gridPane = new GridPane();

        /*
        ################### CONSTRAINTS ################################
         */

        // sets the specified constraints and also automatically resize the grid
        ColumnConstraints col50 = new ColumnConstraints();
        col50.setPercentWidth(50.0);
        gridPane.getColumnConstraints().addAll(col50, col50);

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        /*
        ################# ADD BUTTONS###################################
         */
        Button editButton = new Button(Main.getBundle().getString("Edit"));
        ControllerLectureInfo parent = this;
        editButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/edit/layoutLectureEdit.fxml"));
                    Parent root = loader.load();

                    // get controller
                    ControllerLectureEdit controllerLectureEdit = loader.getController();
                    // pass lecture object
                    controllerLectureEdit.setLecture(lecture);
                    controllerLectureEdit.setUnit(unit);
                    controllerLectureEdit.setParentController(parent);

                    // show edit-form
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("Edit"));

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(gridPane.getScene().getWindow());    // must be adjusted
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException exc) {
                    MyLogger.LOGGER.log(Level.SEVERE, "Couldn't open 'Select Lecture' window.\n" + exc.getMessage());
                    Notification.showAlertWindow(Alert.AlertType.ERROR, li_anchorPane.getScene().getWindow(),
                            "Oops", Main.getBundle().getString("Oops"));
                }
            }
        });

        HBox hButtonBox = new HBox();
        hButtonBox.setSpacing(5.0);
        hButtonBox.setPadding(new Insets(10, 10, 10, 0));

        Button deleteButton = new Button(Main.getBundle().getString("Delete"));
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!unit.removeLecture(lecture)) {
                        Notification.showInfo(Main.getBundle().getString("NotAbleToDelete"), "",
                                li_anchorPane.getScene().getWindow());
                    }
                } catch (IllegalArgumentException exc) {
                    MyLogger.LOGGER.log(Level.SEVERE, "Bad argument passed to removeLecure()!\n" + exc.getMessage());
                } finally {
                    update();
                }
            }
        });
        deleteButton.getStyleClass().addAll("delete-button", "delete-button:hover");

        Button setAsHeadButton = new Button(Main.getBundle().getString("Display"));
        if(unit.getHead().equals(lecture)) setAsHeadButton.setDisable(true);
        setAsHeadButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                unit.setHead(lecture);
                update();
            }
        });

        hButtonBox.getChildren().addAll(editButton, deleteButton, setAsHeadButton);
        gridPane.add(hButtonBox, 0, 0, 4, 1);


        /*
        ################## ADD HEADING ###################################################
         */
        // set headings for the grid
        for (int i = 0; i < colHeadlines.length; i++) {
            Text t = new Text(Main.getBundle().getString(colHeadlines[i]));
            Pane p = new Pane();

            t.setFont(new Font(ControllerCalendar.MEDIUM_FONT_SIZE));
            p.getStyleClass().add("heading-background");

            gridPane.setHalignment(t, HPos.CENTER);
            gridPane.add(p, i, 1);
            gridPane.add(t, i, 1);
        }

        /*
        ########################## ADD LECTURE INFO ######################################################
         */
        // facility
        TreeView<String> facility = new TreeView<String>();
        if (lecture.getFacility() != null) {
            TreeItem<String> facilityRootItem = new TreeItem<>(lecture.getFacility().toString());
            TreeItem<String> t_building = new TreeItem<>(Main.getBundle().getString("Building") + ": " + lecture.getFacility().getBuilding());
            TreeItem<String> t_room = new TreeItem<>(Main.getBundle().getString("Room") + ": " + lecture.getFacility().getRoom());
            TreeItem<String> t_street = new TreeItem<>(Main.getBundle().getString("Street") + ": " + lecture.getFacility().getStreet());
            TreeItem<String> t_zipcode = new TreeItem<>(Main.getBundle().getString("ZipCode") + ": " + lecture.getFacility().getZipcode());
            TreeItem<String> t_city = new TreeItem<>(Main.getBundle().getString("City") + ": " + lecture.getFacility().getCity());
            facilityRootItem.getChildren().addAll(t_building, t_room, t_street, t_zipcode, t_city);
            facility.setRoot(facilityRootItem);
        } else {
            TreeItem<String> facilityRootItem = new TreeItem<>(Main.getBundle().getString("NoFacilityMsg"));
            facility.setRoot(facilityRootItem);
        }

        // lecturer
        TreeView<String> lecturer = new TreeView<>();
        if (lecture.getLecturer() != null) {
            TreeItem<String> lecturerRootItem = new TreeItem<>(lecture.getLecturer().toString());
            TreeItem<String> t_firstName = new TreeItem<>(Main.getBundle().getString("FirstName") + ": " + lecture.getLecturer().getFirstName());
            TreeItem<String> t_lastName = new TreeItem<>(Main.getBundle().getString("LastName") + ": " + lecture.getLecturer().getLastName());
            TreeItem<String> t_email = new TreeItem<>(Main.getBundle().getString("EMail") + ": " + lecture.getLecturer().getEmail());

            TreeItem<String> t_facility;
            if(lecture.getLecturer().getFacility() != null) {
                t_facility = new TreeItem<>(Main.getBundle().getString("Facility") + ": " + lecture.getLecturer().getFacility().toString());
                TreeItem<String> tt_building = new TreeItem<>(Main.getBundle().getString("Building")  + ": " + lecture.getLecturer().getFacility().getBuilding());
                TreeItem<String> tt_room = new TreeItem<>(Main.getBundle().getString("Room") + ": " + lecture.getLecturer().getFacility().getRoom());
                TreeItem<String> tt_street = new TreeItem<>(Main.getBundle().getString("Street") + ": " + lecture.getLecturer().getFacility().getStreet());
                TreeItem<String> tt_zipcode = new TreeItem<>(Main.getBundle().getString("ZipCode") + ": " + lecture.getLecturer().getFacility().getZipcode());
                TreeItem<String> tt_city = new TreeItem<>(Main.getBundle().getString("City") + ": " + lecture.getLecturer().getFacility().getCity());
                t_facility.getChildren().addAll(tt_building, tt_room, tt_street, tt_zipcode, tt_city);
            } else {
                t_facility = new TreeItem<>(Main.getBundle().getString("Facility") + ":");
            }

            lecturerRootItem.getChildren().addAll(t_firstName, t_lastName, t_email, t_facility);
            lecturer.setRoot(lecturerRootItem);
        } else {
            TreeItem<String> lecturerRootItem = new TreeItem<>(Main.getBundle().getString("NoLecturerMsg"));
            lecturer.setRoot(lecturerRootItem);
        }

/*
        // elective
        Text elective = (lecture.isElective() ? new Text(Main.getBundle().getString("True")) : new Text(Main.getBundle().getString("False")));
        gridPane.setHalignment(elective, HPos.CENTER);

        // notes
        TreeView<String> notes = new TreeView<>();
        TreeItem<String> notesRootItem = new TreeItem<>(Main.getBundle().getString("Notes"));
        for (int j = 0; j < lecture.getNotes().size(); j++) {
            Note note = lecture.getNotes().getElement(j);

            TreeItem<String> t_title = new TreeItem<>(note.getTitle());
            TreeItem<String> t_body = new TreeItem<>(note.getBody());
            t_title.getChildren().add(t_body);
            notesRootItem.getChildren().add(t_title);
        }
        notes.setRoot(notesRootItem);
        notes.setShowRoot(false);

        gridPane.addRow(2, facility, lecturer, elective, notes);
*/

        gridPane.addRow(2, facility, lecturer);

        MyLogger.LOGGER.exiting(getClass().toString(), "makeLectureGrid", gridPane);
        return gridPane;
    }

    /**
     * Used to pass the specified {@code Lectures} object to the controller. This method is invoked
     * after the call to {@code initialize()} hence initialize() must wait for this method to complete before
     * it can continue populating the grid.
     * @param lectures Lectures object to assign
     */
    public void setLectures(Lectures lectures) {
        this.lectures = lectures;
    }

    public void setParentController(ControllerCalendar c) {
        this.parentController = c;
    }
}
