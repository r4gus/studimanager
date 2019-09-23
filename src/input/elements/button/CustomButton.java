package input.elements.button;

import guiCalendar.create.timetable.TimetableController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Modality;
import javafx.stage.Stage;
import logging.MyLogger;
import sample.Main;

import java.io.IOException;
import java.util.logging.Level;

public class CustomButton {
    public static Button makeNewTimetableButton(Stage parentStage) {
        Button newTimetableButton = new Button(Main.getBundle().getString("New"));
        newTimetableButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                try {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("/guiCalendar/create/timetable/layoutTimetable.fxml"));
                    Parent root = loader.load();

                    /* assign this stage as parent (this stage should only be closed after the successful creation of a new timetable)*/
                    TimetableController timetableController = loader.getController();
                    timetableController.setParent(parentStage);

                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.setTitle(Main.getBundle().getString("New") + " " + Main.getBundle().getString("Timetable"));

                    // prevent interaction with the primary stage until the new window is closed
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(parentStage);
                    stage.setResizable(false);
                    // show window
                    stage.show();
                } catch (IOException e) {
                    MyLogger.LOGGER.log(Level.SEVERE, "Unable to open New Lecture dialog window.\n" + e.getMessage());
                }

            }
        });

        return newTimetableButton;
    }
}
