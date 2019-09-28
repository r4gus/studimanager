package guiCalendar.create.lecturer;

import config.Language;
import guiCalendar.IFacility;
import guiCalendar.ILecturer;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.facility.NewFacilityController;
import input.elements.textfield.TextField;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import Main.Main;
import message.Notification;
import timetable.Facility;
import timetable.Lecturer;
import timetable.Timetable;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class NewLecturerController implements Initializable, IFacility {
    private final Timetable timetable = ControllerCalendar.getTimetable();

    @FXML
    private GridPane newLecturer_grid;

    @FXML
    private Text newLecturerTitle;

    @FXML
    private Label firstNameTitle;

    @FXML
    private TextField firstNameTextField;

    @FXML
    private Label lastNameTitle;

    @FXML
    private TextField lastNameTextField;

    @FXML
    private Label eMailTitle;

    @FXML
    private TextField eMailTextField;

    @FXML
    private Label facilityTitle;

    @FXML
    public ComboBox<Facility> facilityComboBox;

    @FXML
    private Button newFacility;

    @FXML
    private Button create;

    private ILecturer parentController = null;

    /* ################## PRESERVED FIELDS ########################### */
    private String preservedFirstName = null;
    private String preservedLastName = null;
    private String preservedEmail = null;
    private Facility preservedFacility = null;
    /* ##################################################################### */

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        newLecturer_grid.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
        setLanguage();
        populateFacilityComboBox();
        update();
    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        newLecturerTitle.setText(Main.getBundle().getString("NewLecturer"));
        firstNameTitle.setText(Main.getBundle().getString("FirstName") + ":");
        lastNameTitle.setText(Main.getBundle().getString("LastName") + ":");
        eMailTitle.setText(Main.getBundle().getString("EMail") + ":");
        facilityTitle.setText(Main.getBundle().getString("Facility") + ":");
        newFacility.setText(Main.getBundle().getString("New"));
        create.setText(Main.getBundle().getString("Create"));
    }

    private void setFields() {
        if (preservedFirstName != null) firstNameTextField.setText(preservedFirstName);
        if (preservedLastName != null) lastNameTextField.setText(preservedLastName);
        if (preservedEmail != null) eMailTextField.setText(preservedEmail);
        if (preservedFacility != null) facilityComboBox.getSelectionModel().select(preservedFacility);
    }

    public void update() {
        setFields();
    }

    private void populateFacilityComboBox() {
        for (int i = 0; i < timetable.getFACILITIES().getSize(); i++) {                  // add already existing facilities
            Facility facility = timetable.getFACILITIES().getElement(i);                // as choices to the ComboBox

            facilityComboBox.getItems().add(facility);
        }
    }

    @FXML
    public void handleNewFacilityButtonAction() {
        /*------------- PRESERVE ALREADY ENTERED FIELDS ------------------------ */
        preservedFirstName = firstNameTextField.getText();
        preservedLastName = lastNameTextField.getText();
        preservedEmail = eMailTextField.getText();
        preservedFacility = facilityComboBox.getValue();

        FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/facility/layoutNewFacility.fxml"));
        try {
            Parent root = loader.load();
            NewFacilityController newFacilityController = loader.getController();
            newFacilityController.setParentController(this);

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle(Main.getBundle().getString("NewFacility"));

            // prevents interaction with the primary stage until the new window is closed.
            stage.initModality(Modality.WINDOW_MODAL);
            stage.initOwner(newLecturer_grid.getScene().getWindow());
            stage.setResizable(false);
            stage.show();
        } catch (IOException e) {
            MyLogger.LOGGER.log(Level.SEVERE, e.getMessage());
            Notification.showAlertWindow(Alert.AlertType.ERROR, Main.getPrimaryStage(),
                    "Error", "Severe Error! fxml file missing.");
        }
    }

    @FXML
    public void handleCreateButtonAction() {
        String lastName;
        String firstName;
        String email;
        Facility facility;

       if (lastNameTextField.getText().isEmpty()) {
            lastNameTextField.showError(Main.getBundle().getString("EnterLastName"));
            return;
        } else {
            lastName = lastNameTextField.getText();
        }

        firstName = firstNameTextField.getText();

        email = eMailTextField.getText();

        facility = facilityComboBox.getValue();

        Lecturer l = timetable.newLecturer(firstName, lastName, email, facility);

        Stage stage = (Stage) newLecturer_grid.getScene().getWindow();
        parentController.setLecturer(l);
        parentController.update();
        stage.close();
    }

    public void setParentController(ILecturer c) {
        this.parentController = c;
    }

    public void setFacility(Facility facility) {
        this.preservedFacility = facility;
        this.facilityComboBox.getItems().add(facility);
    }

}
