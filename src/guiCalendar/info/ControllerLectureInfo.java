package guiCalendar.info;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.lecture.NewLectureController;
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
import logging.MyLogger;
import timetable.Lecture;
import timetable.Lectures;
import timetable.Note;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLectureInfo implements Initializable, Updatable {

    @FXML
    private ScrollPane li_scrollPane;

    @FXML AnchorPane li_anchorPane;

    private Lectures lectures;

    private static final String colHeadlines[] = {"Facility", "Lecturer", "Is Elective", "Notes"};

    private Updatable parentController = null;

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

    private Button makeAddButton(Lectures unit) {
        Button button = new Button("new");

        button.getStyleClass().addAll("add-button", "add-button:hover");

        ControllerLectureInfo parent = this;
        button.setOnAction(new EventHandler<ActionEvent>() {
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
                    stage.setTitle("new");

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(li_scrollPane.getScene().getWindow());
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        return button;
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
        ColumnConstraints col30 = new ColumnConstraints();
        ColumnConstraints col10 = new ColumnConstraints();
        col30.setPercentWidth(30.0);
        col10.setPercentWidth(10.0);
        gridPane.getColumnConstraints().addAll(col30, col30, col10, col30);

        gridPane.setHgap(5);
        gridPane.setVgap(5);

        /*
        ################# ADD BUTTONS###################################
         */
        Button editButton = new Button("edit");
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
                    stage.setTitle("edit");

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(gridPane.getScene().getWindow());    // must be adjusted
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        HBox hButtonBox = new HBox();
        hButtonBox.setSpacing(5.0);
        hButtonBox.setPadding(new Insets(10, 10, 10, 0));

        Button deleteButton = new Button("delete");
        deleteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    if (!unit.removeLecture(lecture)) {
                        /*
                        error message
                         */
                    }
                } catch (IllegalArgumentException exc) {
                    /*
                    error message
                     */
                } finally {
                    update();
                }
            }
        });

        editButton.getStyleClass().addAll("edit-button", "edit-button:hover");
        deleteButton.getStyleClass().addAll("delete-button", "delete-button:hover");

        hButtonBox.getChildren().addAll(editButton, deleteButton);
        gridPane.add(hButtonBox, 0, 0, 4, 1);


        /*
        ################## ADD HEADING ###################################################
         */
        // set headings for the grid
        for (int i = 0; i < colHeadlines.length; i++) {
            Text t = new Text(colHeadlines[i]);
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
            TreeItem<String> t_building = new TreeItem<>("Building: " + lecture.getFacility().getBuilding());
            TreeItem<String> t_room = new TreeItem<>("Room: " + lecture.getFacility().getRoom());
            TreeItem<String> t_street = new TreeItem<>("Street: " + lecture.getFacility().getStreet());
            TreeItem<String> t_zipcode = new TreeItem<>("Zip-Code: " + lecture.getFacility().getZipcode());
            TreeItem<String> t_city = new TreeItem<>("City: " + lecture.getFacility().getCity());
            facilityRootItem.getChildren().addAll(t_building, t_room, t_street, t_zipcode, t_city);
            facility.setRoot(facilityRootItem);
        } else {
            TreeItem<String> facilityRootItem = new TreeItem<>("no facility assigned to this lecture");
            facility.setRoot(facilityRootItem);
        }

        // lecturer
        TreeView<String> lecturer = new TreeView<>();
        if (lecture.getLecturer() != null) {
            TreeItem<String> lecturerRootItem = new TreeItem<>(lecture.getLecturer().toString());
            TreeItem<String> t_firstName = new TreeItem<>("First Name: " + lecture.getLecturer().getFirstName());
            TreeItem<String> t_lastName = new TreeItem<>("Last Name: " + lecture.getLecturer().getLastName());
            TreeItem<String> t_email = new TreeItem<>("E-Mail: " + lecture.getLecturer().getEmail());

            TreeItem<String> t_facility;
            if(lecture.getLecturer().getFacility() != null) {
                t_facility = new TreeItem<>("Facility : " + lecture.getLecturer().getFacility().toString());
                TreeItem<String> tt_building = new TreeItem<>("Building: " + lecture.getLecturer().getFacility().getBuilding());
                TreeItem<String> tt_room = new TreeItem<>("Room: " + lecture.getLecturer().getFacility().getRoom());
                TreeItem<String> tt_street = new TreeItem<>("Street: " + lecture.getLecturer().getFacility().getStreet());
                TreeItem<String> tt_zipcode = new TreeItem<>("Zip-Code: " + lecture.getLecturer().getFacility().getZipcode());
                TreeItem<String> tt_city = new TreeItem<>("City: " + lecture.getLecturer().getFacility().getCity());
                t_facility.getChildren().addAll(tt_building, tt_room, tt_street, tt_zipcode, tt_city);
            } else {
                t_facility = new TreeItem<>("Facility :");
            }

            lecturerRootItem.getChildren().addAll(t_firstName, t_lastName, t_email, t_facility);
            lecturer.setRoot(lecturerRootItem);
        } else {
            TreeItem<String> lecturerRootItem = new TreeItem<>("no lecturer assigned to this lecture");
            lecturer.setRoot(lecturerRootItem);
        }

        // elective
        Text elective = (lecture.isElective() ? new Text("true") : new Text("false"));
        gridPane.setHalignment(elective, HPos.CENTER);

        // notes
        TreeView<String> notes = new TreeView<>();
        TreeItem<String> notesRootItem = new TreeItem<>("Notes");
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
