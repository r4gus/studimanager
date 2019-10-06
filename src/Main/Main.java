package Main;


import config.Config;
import guiCalendar.calendar.ControllerCalendar;
import guiExam.ControllerExam;
import guiTodolist.ControllerTodolist;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import timetable.Timetable;
import serializer.TimetableObjectCollection;

import java.util.Locale;
import java.util.ResourceBundle;

import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    public static final String fxml = "Main.fxml";

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 960;
    public static final int MIN_WIDTH = 600;
    public static final int MIN_HEIGHT = 400;


    public static final String TITLE = "Studimanager";

    private static ResourceBundle bundle;

    private static Config config;

    @Override
    public void start(Stage primaryStage) throws Exception{
        TimetableObjectCollection timetableObjectCollection;
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
                bundle = ResourceBundle.getBundle("config.i18n.TimetableResourceBundle", new Locale("de", "DE"));
                break;
            case FR:
                bundle = ResourceBundle.getBundle("config.i18n.TimetableResourceBundle", new Locale("fr", "FR"));
                break;
            default:
                bundle = ResourceBundle.getBundle("config.i18n.TimetableResourceBundle", new Locale("en", "US"));
        }

        /* #################### OPEN APPLICATION ######################################################## */
        try {
            /* ------------------------- LOAD TIMETABLE ------------------------------------ */
            timetableObjectCollection = Timetable.load(config.getTimetablePath());
            // set all required objects
            ControllerCalendar.setTimetable(timetableObjectCollection.getTimetable());
            ControllerExam.setExamList(timetableObjectCollection.getExamList());
            ControllerTodolist.setTaskListCollection(timetableObjectCollection.getTaskListCollection());

            Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
            primaryStage.setTitle(Main.TITLE);
            primaryStage.setScene(new Scene(root, Main.WIDTH , Main.HEIGHT));
            primaryStage.setMinWidth(MIN_WIDTH);
            primaryStage.setMinHeight(MIN_HEIGHT);
            primaryStage.show();
        } catch (Exception exc) {
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

    public static ResourceBundle getBundle() {
        return bundle;
    }
}


