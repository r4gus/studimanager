package guiCalendar.create.lecturer;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.facility.NewFacilityController;
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
import timetable.Facility;
import timetable.Lecture;
import timetable.Timetable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import static message.Alert.showAlert;

public class NewLecturerController implements Initializable, Updatable {
    private final Timetable timetable = ControllerCalendar.getTimetable();
    @FXML
    private GridPane newLecturer_grid;
    private Updatable parentController = null;

    /* ################## PRESERVED FIELDS ########################### */
    private String preservedFirstName = null;
    private String preservedLastName = null;
    private String preservedEmail = null;
    private Facility preservedFacility = null;
    /* ##################################################################### */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newLecturer_grid.getStylesheets().add(getClass().getResource("../../../main.css").toExternalForm());

        adjustGridPane(newLecturer_grid);

        /**
         * Run {@code populateGrid} on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@link #setLecture(Lecture)}
         */
        Platform.runLater(() -> {
            update();
        });


    }

    public void update() {
        makeForm(newLecturer_grid);
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

        Text sceneTitle = new Text(Main.getTimetableBundle().getString("New") + " " + Main.getTimetableBundle().getString("Lecturer"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- FIRST NAME -------------------------------------
         */
        Label firstNameTitle = new Label(Main.getTimetableBundle().getString("FirstName") + ":");
        gridPane.add(firstNameTitle, 0, 1);
        TextField firstNameTextfield = new TextField();
        if (preservedFirstName != null) firstNameTextfield.setText(preservedFirstName);
        gridPane.add(firstNameTextfield, 1, 1);

        /*
        -------------- LAST NAME --------------------------------
         */
        Label lastNameTitle = new Label(Main.getTimetableBundle().getString("LastName") + ":");
        gridPane.add(lastNameTitle, 0, 2);
        TextField lastNameTextfield = new TextField();
        if (preservedLastName != null) lastNameTextfield.setText(preservedLastName);
        gridPane.add(lastNameTextfield, 1, 2);

        /*
        -------------- E-MAIL --------------------------------
         */
        Label emailTitle = new Label(Main.getTimetableBundle().getString("EMail") + ":");
        gridPane.add(emailTitle, 0, 3);
        TextField emailTextfield = new TextField();
        if (preservedEmail != null) emailTextfield.setText(preservedEmail);
        gridPane.add(emailTextfield, 1, 3);


        /*
        ------------- FACILITY ---------------------------------
         */
        Label facilityTitle = new Label(Main.getTimetableBundle().getString("Facility") + ":");
        gridPane.add(facilityTitle, 0, 4);

        ComboBox<Facility> facilityComboBox = new ComboBox<>();
        for (int i = 0; i < timetable.getFACILITIES().getSize(); i++) {                  // add already existing facilities
            Facility facility = timetable.getFACILITIES().getElement(i);                // as choices to the ComboBox

            facilityComboBox.getItems().add(facility);
        }
        if (preservedFacility != null) facilityComboBox.getSelectionModel().select(preservedFacility);
        gridPane.add(facilityComboBox, 1, 4);




        /*
        ------------- SUBMIT BUTTON --------------------------------
         */

        Button createButton = new Button(Main.getTimetableBundle().getString("Create"));

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String lastName = null;
                String firstName = null;
                String email = null;
                Facility facility = null;

                /*
                ------------- GET VALUES ----------------------------------------------
                 */
                if (lastNameTextfield.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error",
                            "Please enter a last name");
                    return;
                } else {
                    lastName = lastNameTextfield.getText();
                }

                firstName = firstNameTextfield.getText();

                email = emailTextfield.getText();

                facility = facilityComboBox.getValue();

                /*
                ------------------- CREATE LECTURE --------------------------------
                 */

                timetable.newLecturer(firstName, lastName, email, facility);

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
                    preservedFirstName = firstNameTextfield.getText();
                    preservedLastName = lastNameTextfield.getText();
                    preservedEmail = emailTextfield.getText();
                    preservedFacility = facilityComboBox.getValue();

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
        gridPane.add(addFacilityButton, 2, 4);

        MyLogger.LOGGER.exiting(getClass().toString(), "makeForm");
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }
}
