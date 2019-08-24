package sample;

import com.fasterxml.jackson.core.JsonProcessingException;
import guiCalendar.calendar.ControllerCalendar;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;

import java.awt.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller implements Initializable {

    @FXML
    public MenuItem saveButton;

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
                    ControllerCalendar.getTimetable().store(ControllerCalendar.PATH);
                    /*
                    visual notification: FILE SAVED
                     */
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
    }


}
