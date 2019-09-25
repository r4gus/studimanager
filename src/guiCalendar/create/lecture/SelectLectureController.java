package guiCalendar.create.lecture;

import config.Language;
import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.info.ControllerLectureInfo;
import input.elements.combobox.ComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import logging.MyLogger;
import message.Notification;
import Main.Main;
import timetable.*;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

public class SelectLectureController implements Initializable {
    private final Timetable timetable = ControllerCalendar.getTimetable();

    @FXML
    private GridPane selectLecture_grid;

    @FXML
    private Text sceneTitle;

    @FXML
    private ComboBox<Lecture> lectureComboBox;

    @FXML
    private Button select;

    private Lectures unit;

    private Updatable parentController = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectLecture_grid.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
        setLanguage();
        populateLectureComboBox();

    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        sceneTitle.setText(Main.getBundle().getString("SelectLecture") + ":");
        select.setText(Main.getBundle().getString("Select"));
    }

    private void populateLectureComboBox() {
        Lectures lectures = timetable.getLECTURES();
        for(int i = 0; i < lectures.getSize(); i++) {
            lectureComboBox.getItems().add(lectures.getElement(i));
        }
    }

    @FXML
    public void handleSelectButtonAction() {
        if(lectureComboBox.getSelectionModel().isEmpty()) {
            lectureComboBox.showError(Main.getBundle().getString("EnterLecture"));
        } else {
            try {
                if(!unit.addLecture(lectureComboBox.getSelectionModel().getSelectedItem())) {
                    ControllerLectureInfo window = (ControllerLectureInfo) parentController;

                    Notification.showInfo(Main.getBundle().getString("NotPossible"),
                            Main.getBundle().getString("DoesAlreadyExist"), window.getWindow());
                }
                parentController.update();
                Stage stage = (Stage) selectLecture_grid.getScene().getWindow();
                stage.close();
            } catch (IllegalArgumentException exc) {
                MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage());
            }
        }
    }

    public void setUnit(Lectures unit) {
        this.unit = unit;
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }
}
