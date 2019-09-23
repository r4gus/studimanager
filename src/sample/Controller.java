package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import guiCalendar.calendar.ControllerCalendar;
import guiCalendar.create.timetable.TimetableController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import message.Notification;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.sql.Time;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.logging.Level;

public class Controller implements Initializable {

    @FXML
    public MenuItem saveButton;

    @FXML
    public MenuItem settingsButton;

    @FXML
    public MenuItem newTimetable;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        registerButtonEvents();
    }

    private void registerButtonEvents() {
        /*
        ################### SAVE Timetable ###############################
         */
        saveButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    String path = Main.getConfig().getTimetablePath();
                    if(path.equals("")) {
                        FileChooser fileChooser = new FileChooser();
                        fileChooser.setInitialFileName("timetable_" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("dd.MM.yyyy")) + ".json");
                        File file = fileChooser.showSaveDialog(Main.getPrimaryStage());

                        if(file == null) return;

                        path = file.getPath();
                        Main.getConfig().setTimetablePath(path);
                        Main.getConfig().store();
                    }

                    ControllerCalendar.getTimetable().store(path);
                    /*
                    visual notification: FILE SAVED
                     */
                    Notification.showConfirm(Main.getBundle().getString("Success"),
                            Main.getBundle().getString("FileSaved"), Main.getPrimaryStage());
                } catch (FileNotFoundException exc) {
                    /*
                    something is wrong with the specified path
                     */
                } catch (JsonProcessingException exc) {

                    /*
                    worst case
                     */
                } catch (IOException exc) {
                    /*
                    general error message
                     */
                }
            }
        });

        settingsButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/config/gui/layoutConfig.fxml"));
                    Parent root = loader.load();

                    // show settings
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("Settings"));

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(Main.getPrimaryStage());
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException exc) {
                    exc.printStackTrace();
                }
            }
        });

        newTimetable.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/timetable/layoutTimetable.fxml"));
                    Parent root = loader.load();

                    /* assign this stage as parent (this stage should only be closed after the successful creation of a new timetable)*/
                    TimetableController timetableController = loader.getController();
                    timetableController.setParent(Main.getPrimaryStage());

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("New") + " " + Main.getBundle().getString("Timetable"));

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(Main.getPrimaryStage());
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException e) {
                    MyLogger.LOGGER.log(Level.SEVERE, "Unable to open New Lecture dialog window.\n" + e.getMessage());
                }
            }
        });
    }


}
