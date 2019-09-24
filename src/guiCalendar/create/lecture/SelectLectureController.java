package guiCalendar.create.lecture;

import guiCalendar.Updatable;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.info.ControllerLectureInfo;
import input.elements.combobox.ComboBox;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
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
    private Lectures unit;
    private Updatable parentController = null;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        selectLecture_grid.getStylesheets().add(getClass().getResource("../../../main.css").toExternalForm());

        adjustGridPane(selectLecture_grid);

        /**
         * Run {@code populateGrid} on the JavaFX Application Thread at some time in the future.
         * Gives the calling method time to set the lectures member using {@link #setLecture(Lecture)}
         */
        Platform.runLater(() -> {
            makeForm(selectLecture_grid, unit);
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

    private void makeForm(GridPane gridPane, Lectures unit) {
        MyLogger.LOGGER.entering(getClass().toString(), "makeForm", new Object[]{gridPane, unit});

        Text sceneTitle = new Text(Main.getBundle().getString("SelectLecture") + ":");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        /*
        -------------- LECTURES -------------------------------------
         */
        ComboBox<Lecture> lectureComboBox = new ComboBox<>();
        lectureComboBox.setPrefSize(200.0, 65.0);
        Lectures lectures = timetable.getLECTURES();
        for(int i = 0; i < lectures.getSize(); i++) {
            lectureComboBox.getItems().add(lectures.getElement(i));
        }
        gridPane.add(lectureComboBox, 0, 1);

        /*
        ------------------- SELECT BUTTON ----------------------------
         */
        Button select = new Button(Main.getBundle().getString("Select"));
        select.setPrefSize(125, 65);
        select.setOnAction(e -> {
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
                    Stage stage = (Stage) gridPane.getScene().getWindow();
                    stage.close();
                } catch (IllegalArgumentException exc) {
                    MyLogger.LOGGER.log(Level.SEVERE, exc.getMessage());
                }
            }
        });
        gridPane.add(select, 1, 1);


        MyLogger.LOGGER.exiting(getClass().toString(), "makeForm");
    }

    public void setUnit(Lectures unit) {
        this.unit = unit;
    }

    public void setParentController(Updatable c) {
        this.parentController = c;
    }
}
