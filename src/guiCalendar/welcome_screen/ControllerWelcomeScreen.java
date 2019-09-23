package guiCalendar.welcome_screen;

import guiCalendar.calendar.ControllerCalendar;
import input.elements.button.CustomButton;
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
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.MyLogger;
import message.Notification;
import sample.Main;
import timetable.Timetable;
import serializer.TimetableObjectCollection;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.logging.Level;

/**
 * @author David Sugar
 */
public class ControllerWelcomeScreen implements Initializable {

    @FXML
    public GridPane welcome_grid;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

        welcome_grid.getStylesheets().add(getClass().getResource("../../main.css").toExternalForm());

        welcome_grid.setHgap(10);
        welcome_grid.setVgap(10);
        welcome_grid.setPadding(new Insets(25, 25, 25, 25));
        welcome_grid.setAlignment(Pos.CENTER);

        FileChooser fileChooser = new FileChooser();
        /*
        ########################## TITLE #####################################
         */
        Text sceneTitle = new Text(Main.getBundle().getString("Welcome"));
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 22));
        sceneTitle.setStyle("-fx-font-weight: bold");
        welcome_grid.add(sceneTitle, 0, 0, 2, 1);


        /*
        ##################### NEW TIMETABLE ###################################
         */
        Platform.runLater(() -> {
            Label newTimetableTitle = new Label(Main.getBundle().getString("ChoiceNew") + ":");
            welcome_grid.add(newTimetableTitle, 0,1);
            Button newTimetableButton = CustomButton.makeNewTimetableButton((Stage) welcome_grid.getScene().getWindow());
            welcome_grid.add(newTimetableButton, 1, 1);

        });

        /*
        ###################### IMPORT #########################################
         */
        Label importTimetableTitle = new Label(Main.getBundle().getString("ChoiceOpen") + ":");
        welcome_grid.add(importTimetableTitle, 0, 2);
        Button importTimetableButton = new Button(Main.getBundle().getString("Open"));
        welcome_grid.add(importTimetableButton, 1, 2);

        /*
        Let the user choose a Json file and open it.
         */
        importTimetableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                /*
                choose File
                 */

                File selectedFile = fileChooser.showOpenDialog(welcome_grid.getScene().getWindow());
                TimetableObjectCollection timetableObjectCollection;
                Stage primaryStage = Main.getPrimaryStage();

                if( selectedFile != null) {     // open the primary stage using the chosen File
                    try {
                        /*
                        -------------- Set Timetable object ----------------------------------
                         */
                        timetableObjectCollection = Timetable.load(selectedFile.getPath());
                        ControllerCalendar.setTimetable(timetableObjectCollection.getTimetable());

                        /*
                        ----------------- UPDATE timetablePath IN CONFIG_FILE ----------------
                         */
                        Main.getConfig().setTimetablePath(selectedFile.getPath());
                        try {
                            Main.getConfig().store();
                        } catch (IOException e) {
                            MyLogger.LOGGER.log(Level.SEVERE, "Couldn't update config data." +
                                    "\nClass: " + getClass().toString() + "\nMethod: handle()" + "\n" + e.getMessage());
                        }

                        /*
                        --------------- Show primary stage ------------------------------------
                         */
                        Parent root = FXMLLoader.load(getClass().getResource("../../sample/" + Main.fxml));
                        primaryStage.setTitle(Main.TITLE);
                        primaryStage.setScene(new Scene(root, Main.WIDTH , Main.HEIGHT));
                        primaryStage.show();

                        /*
                        ---------------- Close this window --------------------------------------
                         */
                        Stage stage = (Stage) welcome_grid.getScene().getWindow();
                        stage.close();

                        /*
                        ------------------ Set new path / or copy file to files (not yet decided) ----------
                         */

                    } catch (Exception exc) {
                        Notification.showInfo("Oops...",
                                Main.getBundle().getString("CantOpenFile"),
                                welcome_grid.getScene().getWindow());
                    }
                }
            }
        });
    }




}
