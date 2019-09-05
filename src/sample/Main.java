package sample;


import config.Config;
import guiCalendar.calendar.ControllerCalendar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import timetable.Timetable;
import java.util.Locale;
import java.util.ResourceBundle;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    public static final String fxml = "sample.fxml";

    private static final String RESOURCE_BUNDLE_PATH = "files/";

    public static final int WIDTH = 1000;

    public static final int HEIGHT = 700;

    public static final String TITLE = "Studimanager";

    private static ResourceBundle timetableBundle;

    private static Config config;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Timetable timetable;
        setPrimaryStage(primaryStage);

        /* ################## LOAD CONFIG DATA ########################################################## */
        try {
            config = Config.load();
        } catch (IOException exc) {
            config = new Config();
            try {
                config.store();
            } catch (IOException e) {
                /*
                something really bad happened. Contact the developers.
                 */
            }
        }

        /* ########################### LOAD LANGUAGE-RESOURCE-BUNDLE #################################### */
        switch (Main.getConfig().getLanguage()) {
            case DE:
                timetableBundle = ResourceBundle.getBundle("config.i18n.TimetableResourceBundle", new Locale("de", "DE"));
                break;
            default:
                timetableBundle = ResourceBundle.getBundle("config.i18n.TimetableResourceBundle");
        }

        /* #################### OPEN APPLICATION ######################################################## */
        try {
            /* ------------------------- LOAD TIMETABLE ------------------------------------ */
            timetable = Timetable.load(config.getTimetablePath());
            ControllerCalendar.setTimetable(timetable);

            Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
            primaryStage.setTitle(Main.TITLE);
            primaryStage.setScene(new Scene(root, Main.WIDTH , Main.HEIGHT));
            primaryStage.show();
        } catch (FileNotFoundException exc) {
            /* ------------------------- OPEN WELCOME SCREEN ON FAILURE -------------------- */
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/welcome_screen/layoutWelcomeScreen.fxml"));
            Parent root = loader.load();

            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.setTitle("Welcome");
            stage.setResizable(false);

            stage.initModality(Modality.APPLICATION_MODAL);
            stage.initOwner(Main.getPrimaryStage());
            stage.show();
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    private void setPrimaryStage(Stage primStage) {
        Main.primaryStage = primStage;
    }


    public static void main(String[] args) {
        launch(args);
    }

    public static Config getConfig() {
        return config;
    }

    public static void setConfig(Config config) {
        Main.config = config;
    }

    public static ResourceBundle getTimetableBundle() {
        return timetableBundle;
    }

    public static void setTimetableBundle(ResourceBundle timetableBundle) {
        Main.timetableBundle = timetableBundle;
    }
}


