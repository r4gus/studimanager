package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import guiCalendar.calendar.ControllerCalendar;
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
import javafx.stage.Modality;
import javafx.stage.Stage;
import message.Notification;
import org.controlsfx.control.NotificationPane;
import org.controlsfx.control.Notifications;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public MenuItem saveButton;

    @FXML
    public MenuItem settingsButton;

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
                    ControllerCalendar.getTimetable().store(Main.getConfig().getTimetablePath());
                    /*
                    visual notification: FILE SAVED
                     */
                    Notification.showConfirm("SUCCESS", "FILE SAVED");
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
    }


}
