package guiCalendar.create.facility;

import config.Language;
import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import input.elements.textfield.AlphaNumTextField;
import input.elements.textfield.IntTextField;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import Main.Main;
import timetable.Timetable;

import java.net.URL;
import java.util.ResourceBundle;

public class NewFacilityController implements Initializable {
    private final Timetable timetable = ControllerCalendar.getTimetable();
    @FXML
    private GridPane newFacility_grid;

    @FXML
    private Text sceneTitle;

    @FXML
    private Label buildingTitle;

    @FXML
    private AlphaNumTextField buildingTextfield;

    @FXML
    private Label roomTitle;

    @FXML
    private AlphaNumTextField roomTextfield;

    @FXML
    private Label streetTitle;

    @FXML
    private AlphaNumTextField streetTextfield;

    @FXML
    private Label zipCodeTitle;

    @FXML
    private IntTextField zipCodeTextfield;

    @FXML
    private Label cityTitle;

    @FXML
    private AlphaNumTextField cityTextfield;

    @FXML
    private Button submit;

    private Updatable parentController = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        newFacility_grid.getStylesheets().add(getClass().getResource("../../../main.css").toExternalForm());
        setLanguage();
    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        sceneTitle.setText(Main.getBundle().getString("NewFacility"));
        buildingTitle.setText(Main.getBundle().getString("Building") + ":");
        roomTitle.setText(Main.getBundle().getString("Room") + ":");
        streetTitle.setText(Main.getBundle().getString("Street") + ":");
        zipCodeTitle.setText(Main.getBundle().getString("ZipCode") + ":");
        cityTitle.setText(Main.getBundle().getString("City") + ":");
        submit.setText(Main.getBundle().getString("Submit"));
    }

    @FXML
    public void handleSubmitButtonAction(ActionEvent actionEvent) {
        String building = null, room = null, street, zipCode, city;
        boolean valid = true;

        if (buildingTextfield.getText().isEmpty()) {
            buildingTextfield.showError(Main.getBundle().getString("NeedInput"));
            valid = false;
        } else {
            building = buildingTextfield.getText();
        }

        if(roomTextfield.getText().isEmpty()) {
            roomTextfield.showError(Main.getBundle().getString("NeedInput"));
            valid = false;
        } else {
            room = roomTextfield.getText();
        }

        street = streetTextfield.getText();

        zipCode = zipCodeTextfield.getText();

        city = cityTextfield.getText();

        if(valid)
            timetable.newFacility(building, room, street, zipCode, city);
        else
            return;
        /* ------------------ CLOSE WINDOW ----------------------------------- */

        Stage stage = (Stage) newFacility_grid.getScene().getWindow();
        parentController.update();
        stage.close();
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }

}
