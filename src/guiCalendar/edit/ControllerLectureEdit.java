package guiCalendar.edit;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logging.MyLogger;
import timetable.Lecture;

import java.net.URL;
import java.util.ResourceBundle;

public class ControllerLectureEdit implements Initializable {

    @FXML
    private ScrollPane edit_scroll;

    @FXML
    private GridPane edit_grid;

    private Lecture lecture;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        AnchorPane.setTopAnchor(edit_scroll, 8.0);
        AnchorPane.setBottomAnchor(edit_scroll, 8.0);
        AnchorPane.setLeftAnchor(edit_scroll, 8.0);
        AnchorPane.setRightAnchor(edit_scroll, 8.0);

        adjustGridPane(edit_grid);

        populateGrid(edit_grid);

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

        MyLogger.LOGGER.exiting(getClass().toString(), "adjustGridPane");
    }

    private void populateGrid(GridPane gridPane) {
        Text sceneTitle = new Text("Edit: ...");
        sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        gridPane.add(sceneTitle, 0, 0, 2, 1);

        Label lectureTitle = new Label("Title:");
        gridPane.add(lectureTitle, 0, 1);
        TextField lectureTitleTextField = new TextField();
        gridPane.add(lectureTitleTextField, 1, 1);


    }
}
