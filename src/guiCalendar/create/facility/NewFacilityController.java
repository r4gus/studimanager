package guiCalendar.create.facility;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.Window;
import logging.MyLogger;
import sample.Main;
import timetable.Lecture;
import timetable.Timetable;

import java.net.URL;
import java.util.ResourceBundle;

public class NewFacilityController implements Initializable {
    private final Timetable timetable = ControllerCalendar.getTimetable();
    @FXML
    private GridPane newFacility_grid;
    private Stage ownStage = null;
    private Updatable parentController = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newFacility_grid.getStylesheets().add(getClass().getResource("../../../main.css").toExternalForm());

        adjustGridPane(newFacility_grid);

        /**
         * Run {@code populateGrid} on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@link #setLecture(Lecture)}
         */
        Platform.runLater(() -> {

            makeForm(newFacility_grid);
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

    private void makeForm(GridPane gridPane) {

        MyLogger.LOGGER.entering(getClass().toString(), "makeForm", gridPane);

        Text sceneTitle = new Text(Main.getTimetableBundle().getString("New") + " " + Main.getTimetableBundle().getString("Facility"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- BUILDING -------------------------------------
         */
        Label buildingTitle = new Label(Main.getTimetableBundle().getString("Building") + ":");
        gridPane.add(buildingTitle, 0, 1);
        TextField buildingTextfield = new TextField();
        gridPane.add(buildingTextfield, 1, 1);

        /*
        -------------- ROOM --------------------------------
         */
        Label roomTitle = new Label(Main.getTimetableBundle().getString("Room") + ":");
        gridPane.add(roomTitle, 0, 2);
        TextField roomTextfield = new TextField();
        gridPane.add(roomTextfield, 1, 2);

        /*
        ------------- STREET ---------------------------------
         */
        Label streetTitle = new Label(Main.getTimetableBundle().getString("Street") + ":");
        gridPane.add(streetTitle, 0, 3);
        TextField streetTextfield = new TextField();
        gridPane.add(streetTextfield, 1, 3);

        /*
        ---------------- Zip-Code -----------------------------
         */
        Label zipCodeTitle = new Label(Main.getTimetableBundle().getString("ZipCode") + ":");
        gridPane.add(zipCodeTitle, 0, 4);
        TextField zipCodeTextfield = new TextField();
        gridPane.add(zipCodeTextfield, 1, 4);

        /*
        ---------------- CITY -----------------------------
         */
        Label cityTitle = new Label(Main.getTimetableBundle().getString("City") + ":");
        gridPane.add(cityTitle, 0, 5);
        TextField cityTextfield = new TextField();
        gridPane.add(cityTextfield, 1, 5);

        /*
        ------------- SUBMIT BUTTON --------------------------------
         */

        Button createButton = new Button(Main.getTimetableBundle().getString("Create"));

        createButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                String building = null;
                String room = null;
                String street = null;
                String zipCode = null;
                String city = null;


                /*
                ------------- GET VALUES ----------------------------------------------
                 */
                if (buildingTextfield.getText().isEmpty() || roomTextfield.getText().isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, gridPane.getScene().getWindow(), "Form Error",
                            "Please enter a building and room number");
                    return;
                } else {
                    building = buildingTextfield.getText();
                    room = roomTextfield.getText();
                }

                street = streetTextfield.getText();

                zipCode = zipCodeTextfield.getText();

                city = cityTextfield.getText();

                /*
                ------------------- CREATE FACILITY --------------------------------
                 */

                timetable.newFacility(building, room, street, zipCode, city);

                /*
                ------------------ CLOSE WINDOW -----------------------------------
                 */

                Stage stage = (Stage) gridPane.getScene().getWindow();
                parentController.update();
                stage.close();
            }
        });

        gridPane.add(createButton, 0, 6, 2, 1);

        MyLogger.LOGGER.exiting(getClass().toString(), "makeForm");
    }

    private void showAlert(Alert.AlertType alertType, Window owner, String title, String message) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.initOwner(owner);
        alert.show();
    }


    public void setParentController(Updatable c) {
        this.parentController = c;
    }

    public void setOwnStage(Stage s) {
        this.ownStage = s;
    }
}
