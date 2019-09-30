package guiCalendar.welcome_screen;
import config.Language;
import guiCalendar.calendar.ControllerCalendar;
import input.elements.button.CustomButton;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import logging.MyLogger;
import message.Notification;
import Main.Main;
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

    @FXML
    private Text sceneTitle;

    @FXML
    private Label newTimetableTitle;

    @FXML
    private Label importTimetableTitle;

    @FXML
    private Button importTimetableButton;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        welcome_grid.getStylesheets().add(getClass().getResource("/main.css").toExternalForm());
        setLanguage();
        setNewTimetableButton();
    }

    /**
     * Set the text for all elements depending on the language {@link Language} settings specified
     * in {@link config.Config}.
     */
    private void setLanguage() {
        sceneTitle.setText(Main.getBundle().getString("Welcome"));
        newTimetableTitle.setText(Main.getBundle().getString("ChoiceNew"));
        importTimetableTitle.setText(Main.getBundle().getString("ChoiceOpen"));
        importTimetableButton.setText(Main.getBundle().getString("Open"));
    }

    private void setNewTimetableButton() {
        Platform.runLater(() -> {
            Button newTimetableButton = CustomButton.makeNewTimetableButton((Stage) welcome_grid.getScene().getWindow());
            welcome_grid.add(newTimetableButton, 1, 1);
        });
    }

    @FXML
    public void handleImportTimetableButtonAction() {
        FileChooser fileChooser = new FileChooser();
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
                Parent root = FXMLLoader.load(getClass().getResource("/Main/" + Main.fxml));
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
}
