package sample;


import guiCalendar.calendar.ControllerCalendar;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import timetable.Timetable;

import java.io.FileNotFoundException;
import java.io.IOException;

public class Main extends Application {

    private static Stage primaryStage;

    private static String path = "files/timetable.json";

    public static final String fxml = "sample.fxml";

    public static final int WIDTH = 1000;

    public static final int HEIGHT = 700;

    public static final String TITLE = "Studimanager";

    @Override
    public void start(Stage primaryStage) throws Exception{
        Timetable timetable = null;
        setPrimaryStage(primaryStage);

        try {
            timetable = Timetable.load(path);
            ControllerCalendar.setTimetable(timetable);

            Parent root = FXMLLoader.load(getClass().getResource(Main.fxml));
            primaryStage.setTitle(Main.TITLE);
            primaryStage.setScene(new Scene(root, Main.WIDTH , Main.HEIGHT));
            primaryStage.show();
        } catch (FileNotFoundException exc) {
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
}


